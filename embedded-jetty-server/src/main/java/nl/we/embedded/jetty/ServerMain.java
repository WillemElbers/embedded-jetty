package nl.we.embedded.jetty;

import nl.we.embedded.client.cli.StopServer;
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
    private final String applicationClassName;
    
    
    public ServerMain(String applicationClassName) {
        this.port = ServerConfig.getInstance().getPort();
        this.server = new Server(port);
        this.applicationClassName = applicationClassName;
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
        new StopServer().stop();
        loadBeforeStartup();
        logger.info("Starting server on port={}", port);
        Thread monitor = new ServerControl(server);
        monitor.start();
        server.setHandler(createRootHandler());
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

    protected Handler createRootHandler() {
        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        ServletHolder sh = new ServletHolder(ServletContainer.class);
        Map<String, String> map = new HashMap<>();
        map.put("javax.ws.rs.Application", applicationClassName);
        sh.setInitParameters(map);
        root.addServlet(sh, "/*");
        return root;
    }
}