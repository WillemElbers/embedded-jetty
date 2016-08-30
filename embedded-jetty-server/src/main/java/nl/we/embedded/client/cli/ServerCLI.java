package nl.we.embedded.client.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import nl.we.embedded.jetty.ServerCommand;
import nl.we.embedded.jetty.ServerConfig;
import nl.we.embedded.jetty.ServerMain;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wilelb
 */
public class ServerCLI {
    public final static String OPT_HELP = "help";
    public final static String OPT_START = "start";
    public final static String OPT_STOP = "stop";
    public final static String OPT_STATUS = "status";
    
    private final static Logger logger = LoggerFactory.getLogger(ServerCLI.class);
    
    private final ServerMain server;
    
    public ServerCLI(ServerMain server) {
        this.server = server;
    }
    
    public void handleCLI(String[] args) throws Exception {        
        Options options = 
            new Options()
                .addOption(OPT_HELP, false, "print this message" )
                .addOption(OPT_START, false, "start server")
                .addOption(OPT_STOP, false, "stop server")
                .addOption(OPT_STATUS, false, "status of server");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse( options, args );

        if(line.hasOption(OPT_START)) {
            startServer(line);
        } else if(line.hasOption(OPT_STOP)) {
            stopServer(line);
        } else if(line.hasOption(OPT_STATUS)) {
            serverStatus(line);
        } else {            
            showHelp(options, !line.hasOption(OPT_HELP));
        }   
    }
    
    private void showHelp(Options options, boolean invalid) {
        if(invalid) {
            logger.info("Invalid options");
        }
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "java -jar <jarfile>", options );
    }
    
    private void startServer(CommandLine line) throws Exception {
        logger.info("startServer");
        server.start();
    }
    
    private void stopServer(CommandLine line) {
        logger.info("stopServer");
        sendCommandToServer(ServerCommand.STOP);
    }
    
    private void serverStatus(CommandLine line) {
        sendCommandToServer(ServerCommand.STATUS);
    }
    
    private void sendCommandToServer(ServerCommand command) {
        try {
            Socket s = new Socket(
                InetAddress.getByName(ServerConfig.getInstance().getControlHost()), 
                ServerConfig.getInstance().getControlPort());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

            BufferedReader br = 
                new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            logger.info("Sending jetty stop request");
            writer.println(command.toString());
            writer.flush();
            
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null) {
                builder.append(line);
            }
            logger.info("Response: {}", builder.toString());
            
            s.close();
        } catch(ConnectException ex) {
            logger.info("Cannot connect to server. Not running?");
            logger.debug("", ex);
        }  catch(IOException ex) {
            logger.error("Failed to stop server", ex);
        }
    }
}
