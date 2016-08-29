package nl.we.embedded.jetty.rest;

import io.swagger.jaxrs.config.BeanConfig;
import nl.we.embedded.jetty.ServerConfig;
import nl.we.jersey.filters.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author wilelb
 */
public class DefaultApplication extends ResourceConfig {
    
    protected final BeanConfig beanConfig = new BeanConfig();
    
    public DefaultApplication(String resourcePackage) {        
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:"+ServerConfig.getInstance().getPort());
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage(resourcePackage);//"nl.we.embedded.jetty.test.resources");
        beanConfig.setScan(true);
        
        packages("io.swagger.jaxrs.listing");
        //packages("nl.we.embedded.jetty.test.resources");
        register(CORSFilter.class);
    }
}
