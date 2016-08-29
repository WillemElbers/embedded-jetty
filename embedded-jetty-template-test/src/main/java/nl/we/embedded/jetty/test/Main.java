package nl.we.embedded.jetty.test;

import nl.we.embedded.jetty.ServerMain;

/**
 *
 * @author wilelb
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final ServerMain server = new ServerMain(TestApplication.class.getCanonicalName()) {
            @Override
            protected void load() {
                
            }
        };
        server.start();
    }
}
