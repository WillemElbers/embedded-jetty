package nl.we.embedded.jetty.core;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wilelb
 */
public abstract class ServerMain {    
    private final static Logger logger = LoggerFactory.getLogger(ServerMain.class);
    
    private final Server server; 
    private final int port;
    //private final String applicationClassName;
    private Handler handler;
    
    public ServerMain(/*String applicationClassName, Handler handler*/) {
        //this.handler = handler;
        this.port = ServerConfig.getInstance().getPort();
        this.server = new Server(port);
        //this.applicationClassName = applicationClassName;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                    server.join();
                } catch(Exception ex) {
                    logger.error("Failed to shutdown.", ex);
                }
            }
        });
    }

    private double nsToMs(long ns) {
        return ns / 1000000.0;
    }
    
    private void loadBeforeStartup() {
        logger.info("Loading data before startup");
        long t1 = System.nanoTime();
        load();
        long t2 = System.nanoTime();
        logger.info("Loading data before startup completed in {}ms", String.format("%.4f", nsToMs(t2-t1)));
    }
    
    protected abstract void load();
    
    public void start() throws Exception {
        if(handler == null) {
            throw new IllegalStateException("No handler configured");
        }
        loadBeforeStartup();
        logger.info("Starting server on port={}", port);
        Thread monitor = new ServerControl(server);
        monitor.start();
        server.setHandler(handler);
        server.start();
        server.join();
    }
    
    public void stop() throws Exception {
        logger.info("Stopping server");
        server.stop();
    }
    
    private void join() throws InterruptedException {
        server.join();
    }
    
    public ServerMain setServletContextHandler(String urlPath, Class applicationClass) {
        return setServletContextHandler(urlPath, applicationClass.getCanonicalName());
    }
    
    public ServerMain setServletContextHandler(String urlPath, String applicationClassName) {
        String path = ServerConfig.getInstance().getServerBasePath();
        if(!path.endsWith("/")) {
            path += "/";
        }
        path += "*";
        
        logger.info("Path={}, application classname={}", path, applicationClassName);
        
        ServletContextHandler root = new ServletContextHandler(server, urlPath, ServletContextHandler.SESSIONS);
        ServletHolder sh = new ServletHolder(ServletContainer.class);
        Map<String, String> map = new HashMap<>();
        map.put("javax.ws.rs.Application", applicationClassName);
        sh.setInitParameters(map);
        root.addServlet(sh, path);
        handler = root;
        return this;
    }
}
