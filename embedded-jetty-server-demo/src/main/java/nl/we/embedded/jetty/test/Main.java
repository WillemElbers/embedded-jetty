package nl.we.embedded.jetty.test;

import nl.we.embedded.jetty.core.ServerMain;
import nl.we.embedded.jetty.core.ServerDecorator;
import nl.we.embedded.jetty.core.cli.ServerCLI;
/**
 *
 * @author wilelb
 */
public class Main {
    public static void main(String[] args) throws Exception {      
        final ServerMain server = new ServerMain() {
            @Override
            protected void load() {
                //Preload some data
            }
        };
        final ServerDecorator decorator = new ServerDecorator() {
            @Override
            public void decorate(ServerMain server) {
                server.setServletContextHandler("/", TestApplication.class);
            }   
        };
        new ServerCLI(server).handleCLI(args, decorator);
    }
}
