package nl.we.embedded.jetty.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
        setName("ServerControl");
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
        logger.info("Running server control thread");
        Socket accept = null;
        try {
            accept = socket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));            
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(accept.getOutputStream())));
            String line = reader.readLine();
            try {
                processCommand(line, writer);
            } catch(ServerControlException ex) {
                logger.error("Server control error", ex);
            } 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(accept != null) {
                try {
                    accept.close();
                } catch(IOException ex) {
                    logger.error("Failed to close socket", ex);
                }
            }
        }
    }
    
    private void processCommand(String line, PrintWriter writer) throws ServerControlException {
        try {
            switch(ServerCommand.valueOf(line)) {
                case STATUS: serverStatus(writer); break;
                case STOP: stopServer(writer); break;
                default: throw new ServerControlException("Unsupported server command: "+line);
            }
        } catch(IllegalArgumentException ex) {
            throw new ServerControlException("Unkown command", ex);
        }
    }
    
    private void stopServer(PrintWriter writer) throws ServerControlException {
        writer.println("Shutting down");
        writer.flush();
        try {
            server.stop();
        } catch(Exception ex) {
            throw new ServerControlException("Failed to stop server", ex);
        }
        try {
            socket.close();
        } catch(IOException ex) {
            throw new ServerControlException("Failed to close socket", ex);
        }
    }
    
    private void serverStatus(PrintWriter writer) throws ServerControlException {
        writer.println("Running");
        writer.flush();
    }
    
    public static class ServerControlException extends Exception {
        public ServerControlException(String msg) {
            super(msg);
        }
        
        public ServerControlException(String msg, Throwable t) {
            super(msg, t);
        }
    }

}
