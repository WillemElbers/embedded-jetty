package nl.we.embedded.jetty.rest;

import io.swagger.jaxrs.config.BeanConfig;
import nl.we.embedded.jetty.ServerConfig;
import nl.we.jaxrs.filters.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author wilelb
 */
public class DefaultApplication extends ResourceConfig {
    
    protected final BeanConfig beanConfig = new BeanConfig();
    
    public DefaultApplication(String[] resourcePackages) {    
        //configure swagger
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:"+ServerConfig.getInstance().getPort());
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage(resourcePackages[0]);
        beanConfig.setScan(true);        
        packages("io.swagger.jaxrs.listing");
        //add all packages to scan for resources
        for(String resourcePackage :  resourcePackages) {
            packages(resourcePackage);
        }
        //enable corst filter
        register(CORSFilter.class);
    }
}
