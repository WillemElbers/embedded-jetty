package nl.we.embedded.jetty.test;

import nl.we.embedded.jetty.rest.DefaultSwaggerApplication;

/**
 *
 * @author wilelb
 */
public class TestApplication extends DefaultSwaggerApplication {
    
    public TestApplication() {
        super(new String[] {"nl.we.embedded.jetty.test.resources"});
    }
    
}
