package nl.we.embedded.client.cli;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import nl.we.embedded.jetty.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wilelb
 */
public class StopServer {
    
    private static final Logger logger = LoggerFactory.getLogger(StopServer.class);
    
    public static void main(String[] args) throws Exception {
        new StopServer().stop();        
    }
    
    public void stop() {
        try {
            Socket s = new Socket(
                InetAddress.getByName(ServerConfig.getInstance().getControlHost()), 
                ServerConfig.getInstance().getControlPort());
            OutputStream out = s.getOutputStream();
            logger.info("Sending jetty stop request");
            out.write(("\r\n").getBytes());
            out.flush();
            s.close();
        } catch(ConnectException ex) {
            logger.info("Cannot connect to server. Not running?");
            logger.debug("", ex);
        }  catch(IOException ex) {
            logger.error("Failed to stop server", ex);
        }
    }
}
