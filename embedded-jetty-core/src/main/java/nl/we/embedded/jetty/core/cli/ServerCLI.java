package nl.we.embedded.jetty.core.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import nl.we.embedded.jetty.core.ServerCommand;
import nl.we.embedded.jetty.core.ServerConfig;
import nl.we.embedded.jetty.core.ServerDecorator;
import nl.we.embedded.jetty.core.ServerMain;
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
    public final static String OPT_CONFIG = "cfg";
    
    private final static Logger logger = LoggerFactory.getLogger(ServerCLI.class);
    
    private final ServerMain server;
    
    private static enum ServerState {
        STOPPED,
        STOPPING,
        RUNNING,
        UNKOWN
    } 
    
    public ServerCLI(ServerMain server) {
        this.server = server;
    }
    
    public void handleCLI(String[] args, ServerDecorator decorator) throws Exception {
        handleCLI(args, decorator, new ArrayList<PreStartAction>());
    }
    
    public void handleCLI(String[] args, ServerDecorator decorator, List<PreStartAction> preStartActions) throws Exception {
        Options options = 
            new Options()
                .addOption(OPT_HELP, false, "print this message" )
                .addOption(OPT_START, false, "start server")
                .addOption(OPT_STOP, false, "stop server")
                .addOption(OPT_STATUS, false, "status of server")
                .addOption(OPT_CONFIG, true, "path to config file");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse( options, args );

        if(line.hasOption(OPT_START)) {
            ServerConfig cfg = ServerConfig.getInstance();
            if(line.hasOption(OPT_CONFIG)) {
                String cfgFile = line.getOptionValue(OPT_CONFIG);
                cfg.loadFromFile(new File(cfgFile));
            }
            cfg.print();    
            
            if(cfg.getAutoStop()) {
                logger.info("Auto stop enabled; Stopping running server");
                stopServer(line);
                //Wait for the server to stop
                //TODO: wait for server to actually stop
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    logger.debug("", ex);
                }
            }
            
            decorator.decorate(server);
            for(PreStartAction action : preStartActions) {
                action.execute();
            }
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
        logger.info("Staring server");
        server.start();
    }
    
    private void stopServer(CommandLine line) {
        logger.debug("Stopping server");
        String response = sendCommandToServer(ServerCommand.STOP);
        if(response == null) {
            logger.debug("Stop server response is null");
        } else {
            logger.debug("Stop server response is {}", response);
        }
    }
    
    private ServerState serverStatus(CommandLine line) {
        logger.debug("Geting server status");
        ServerState result;
        String response = sendCommandToServer(ServerCommand.STATUS);
        if(response == null) {
            result = ServerState.STOPPED;
        } else {
            if(response.equalsIgnoreCase("running")) {
                result = ServerState.RUNNING;
            } else {
                result = ServerState.UNKOWN;
            }
        }
        logger.debug("Server status: {}", result);
        return result;
    }
    
    private String sendCommandToServer(ServerCommand command) {
        String response = null;
        Socket s = null;
        try {
            s = new Socket(
                InetAddress.getByName(ServerConfig.getInstance().getControlHost()), 
                ServerConfig.getInstance().getControlPort());
            PrintWriter writer = 
                new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader br = 
                new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            logger.trace("Sending {} command to server", command);
            writer.println(command.toString());
            writer.flush();
            
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null) {
                builder.append(line);
            }
            response = builder.toString();
            logger.trace("Response: {}", response);
        } catch(ConnectException ex) {
            if(!ex.getMessage().equalsIgnoreCase("Connection refused")) {
                logger.info("Cannot connect to server. Not running?");
                logger.debug("", ex);
            }
        }  catch(IOException ex) {
            logger.error("Failed to stop server", ex);
        } finally {
            if(s != null) {
                try {
                    s.close();
                } catch(IOException ex) {
                    logger.error("Failed to close socket", ex);
                }
            }
        }
        return response;
    }
}
