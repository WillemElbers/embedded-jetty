package nl.we.embedded.jetty;

/**
 *
 * @author wilelb
 */
public class ServerConfig {

    private static final int DEFAULT_PORT = 9090;
    private static final int DEFAULT_CONTROL_PORT = 9091;
    private static final String DEFAULT_CONTROL_HOST = "127.0.0.1";
    
    private static ServerConfig _instance;
    
    public static ServerConfig getInstance() {
        if(_instance == null) {
            _instance = new ServerConfig();
        }
        return _instance;
    }
    
    private final int port;
    private final int controlPort;
    private final String controlHost;
    
    private ServerConfig() {
        this.port = DEFAULT_PORT;
        this.controlPort = DEFAULT_CONTROL_PORT;
        this.controlHost = DEFAULT_CONTROL_HOST;
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
}
