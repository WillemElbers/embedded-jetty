package nl.we.embedded.jetty.test;

import io.swagger.jaxrs.config.BeanConfig;
import nl.we.embedded.jetty.ServerConfig;
import nl.we.jersey.filters.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;
import nl.we.embedded.jetty.rest.DefaultApplication;

/**
 *
 * @author wilelb
 */
public class TestApplication extends DefaultApplication {
    
    private final static String PACKAGES = "nl.we.embedded.jetty.test.resources";
    
    public TestApplication() {
        super(PACKAGES);
        /*
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:"+ServerConfig.getInstance().getPort());
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("nl.we.embedded.jetty.test.resources");
        beanConfig.setScan(true);
        */
        //packages("io.swagger.jaxrs.listing");
        packages(PACKAGES);
        //register(CORSFilter.class);
    }
}
