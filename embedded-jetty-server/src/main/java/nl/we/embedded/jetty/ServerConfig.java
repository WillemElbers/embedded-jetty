package nl.we.embedded.jetty;

import java.util.Properties;

/**
 *
 * @author wilelb
 */
public class ServerConfig {

    private static final int DEFAULT_PORT = 9090;
    private static final int DEFAULT_CONTROL_PORT = 9091;
    private static final String DEFAULT_CONTROL_HOST = "127.0.0.1";
    private static final String DEFAULT_DOC_VERSION = "1.0.0";
    private static final String DEFAULT_DOC_SCHEMES = "http";
    private static final String DEFAULT_DOC_PATH = "/";
    
    private static final String KEY_SERVER_PORT = "server.port";
    private static final String KEY_SERVER_CONTROL_PORT = "server.control.port";
    private static final String KEY_SERVER_CONTROL_HOST = "server.control.host";
    private static final String KEY_DOC_VERSION = "doc.version";
    private static final String KEY_DOC_SCHEMES = "doc.schemes";
    private static final String KEY_DOC_PATH = "doc.path";

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
    private String docPath;
    
    private ServerConfig() {
        this.port = DEFAULT_PORT;
        this.controlPort = DEFAULT_CONTROL_PORT;
        this.controlHost = DEFAULT_CONTROL_HOST;
    }
    
    public void loadFromProperties(Properties props) {
        this.port = 
            Integer.parseInt(
                props.getProperty(KEY_SERVER_PORT, String.valueOf(DEFAULT_PORT)));
        this.controlPort = 
            Integer.parseInt(
                props.getProperty(KEY_SERVER_CONTROL_PORT, String.valueOf(DEFAULT_CONTROL_PORT)));
        this.controlHost = props.getProperty(KEY_SERVER_CONTROL_HOST, DEFAULT_CONTROL_HOST);
        this.docPath = props.getProperty(DEFAULT_DOC_PATH, DEFAULT_DOC_PATH);
        this.docSchemes = props.getProperty(DEFAULT_DOC_SCHEMES, DEFAULT_DOC_SCHEMES).split(",");
        this.docVersion = props.getProperty(DEFAULT_DOC_VERSION, DEFAULT_DOC_VERSION);
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

    public String getDocPath() {
        return docPath;
    }
}
