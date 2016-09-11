package nl.we.embedded.jetty.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wilelb
 */
public class ServerConfig {

    private final static Logger logger = LoggerFactory.getLogger(ServerConfig.class);
    
    private static final int DEFAULT_PORT = 9090;
    private static final int DEFAULT_CONTROL_PORT = 9091;
    private static final String DEFAULT_CONTROL_HOST = "127.0.0.1";
    private static final String DEFAULT_DOC_VERSION = "1.0.0";
    private static final String DEFAULT_DOC_SCHEMES = "http";
    private static final String DEFAULT_SERVER_BASE_PATH = "/";
    
    public static final String KEY_SERVER_PORT = "server.port";
    public static final String KEY_SERVER_BASE_PATH = "server.base.path";
    public static final String KEY_SERVER_CONTROL_PORT = "server.control.port";
    public static final String KEY_SERVER_CONTROL_HOST = "server.control.host";
    public static final String KEY_DOC_VERSION = "doc.version";
    public static final String KEY_DOC_SCHEMES = "doc.schemes";
    

    private static ServerConfig _instance;
    
    public static ServerConfig getInstance() {
        if(_instance == null) {
            _instance = new ServerConfig();
        }
        return _instance;
    }
    
    private int port;
    private int controlPort;
    private String controlHost;
    private String docVersion;
    private String[] docSchemes;
    private String serverBasePath;
    
    private ServerConfig() {
        loadFromProperties(new Properties());
    }
    
    public void loadFromFile(File file) {
        logger.info("Loading properties from file: {}", file.getAbsolutePath());
        Properties props;
        try (FileInputStream input = new FileInputStream(file)) {
            props = new Properties();
            props.load(input);
            loadFromProperties(props);
        } catch (IOException ex) {
            logger.error("Failed to load properties from file", ex);
        }
    }
    
    private void loadFromProperties(Properties props) {
        this.port = 
            Integer.parseInt(
                props.getProperty(KEY_SERVER_PORT, String.valueOf(DEFAULT_PORT)));
        this.controlPort = 
            Integer.parseInt(
                props.getProperty(KEY_SERVER_CONTROL_PORT, String.valueOf(DEFAULT_CONTROL_PORT)));
        this.controlHost = props.getProperty(KEY_SERVER_CONTROL_HOST, DEFAULT_CONTROL_HOST);
        this.serverBasePath = props.getProperty(KEY_SERVER_BASE_PATH, DEFAULT_SERVER_BASE_PATH);
        this.docSchemes = props.getProperty(KEY_DOC_SCHEMES, DEFAULT_DOC_SCHEMES).split(",");
        this.docVersion = props.getProperty(KEY_DOC_VERSION, DEFAULT_DOC_VERSION);
    }
    
    public void print() {
        logger.info("{}={}",KEY_SERVER_PORT, port);
        logger.info("{}={}",KEY_SERVER_CONTROL_HOST, controlHost);
        logger.info("{}={}",KEY_SERVER_CONTROL_PORT, controlPort);
        logger.info("{}={}",KEY_SERVER_BASE_PATH, serverBasePath);
        logger.info("{}={}",KEY_DOC_SCHEMES, docSchemes);
        logger.info("{}={}",KEY_DOC_VERSION, docVersion);
    }
   

    public int getPort() {
        return port;
    }

    public int getControlPort() {
        return controlPort;
    }

    public String getControlHost() {
        return controlHost;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public String[] getDocSchemes() {
        return docSchemes;
    }

    public String getServerBasePath() {
        return serverBasePath;
    }
}
