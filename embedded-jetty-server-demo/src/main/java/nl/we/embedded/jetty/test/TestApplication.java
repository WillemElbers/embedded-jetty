package nl.we.embedded.jetty.test;

import nl.we.embedded.jetty.rest.DefaultApplication;

/**
 *
 * @author wilelb
 */
public class TestApplication extends DefaultApplication {
    
    public TestApplication() {
        super(new String[] {"nl.we.embedded.jetty.test.resources"});
    }
    
}
