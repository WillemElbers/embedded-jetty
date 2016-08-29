package nl.we.embedded.jetty.test;

import nl.we.embedded.jetty.rest.DefaultApplication;

/**
 *
 * @author wilelb
 */
public class TestApplication extends DefaultApplication {
    
    private final static String PACKAGES = "nl.we.embedded.jetty.test.resources";
    
    public TestApplication() {
        super(PACKAGES);
        packages(PACKAGES);
    }
}
