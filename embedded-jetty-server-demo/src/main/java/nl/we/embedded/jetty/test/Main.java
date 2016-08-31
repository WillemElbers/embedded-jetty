package nl.we.embedded.jetty.test;

import java.util.Properties;
import nl.we.embedded.client.cli.ServerCLI;
import nl.we.embedded.jetty.ServerMain;

/**
 *
 * @author wilelb
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        //props.load(inStream);
        
        final ServerMain server = new ServerMain(TestApplication.class.getCanonicalName()) {
            @Override
            protected void load() {
                //Preload some data
            }
        };
        new ServerCLI(server)
            .configFromProperties(props)
            .handleCLI(args);
    }
}
