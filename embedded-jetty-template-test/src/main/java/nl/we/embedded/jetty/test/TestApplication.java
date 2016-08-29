package nl.we.embedded.jetty.test;

import nl.we.jersey.filters.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author wilelb
 */
public class TestApplication extends ResourceConfig {
    
    public TestApplication() {
        packages("nl.we.embedded.jetty.test.resources");
        register(CORSFilter.class);
    }
}
