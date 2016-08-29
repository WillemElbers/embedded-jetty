package nl.we.embedded.jetty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wilelb
 */
public class ServerControl extends Thread {

    private final static Logger logger = LoggerFactory.getLogger(ServerControl.class);
    
    private ServerSocket socket;

    private final Server server;
    
    public ServerControl(Server server) {
        this.server = server;
        setDaemon(true);
        setName("StopMonitor");
        try {
            socket = 
                new ServerSocket(
                    ServerConfig.getInstance().getControlPort(), 
                    1, 
                    InetAddress.getByName(ServerConfig.getInstance().getControlHost()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        logger.info("Running jetty 'stop' thread");
        Socket accept;
        try {
            accept = socket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            reader.readLine();
            logger.info("Stopping jetty embedded server");
            server.stop();
            accept.close();
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
