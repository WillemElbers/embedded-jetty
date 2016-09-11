package nl.we.embedded.jetty.rest;

import io.swagger.jaxrs.config.BeanConfig;
import nl.we.embedded.jetty.core.ServerConfig;
import nl.we.embedded.jetty.rest.providers.JsonObjectMapperProvider;
import nl.we.jaxrs.filters.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author wilelb
 */
public class DefaultApplication extends ResourceConfig {
    
    protected final BeanConfig beanConfig = new BeanConfig();
    
    public DefaultApplication(String[] resourcePackages) {    
        //configure swagger
        beanConfig.setVersion(ServerConfig.getInstance().getDocVersion());
        beanConfig.setSchemes(ServerConfig.getInstance().getDocSchemes());
        beanConfig.setHost("localhost:"+ServerConfig.getInstance().getPort());
        beanConfig.setBasePath(ServerConfig.getInstance().getServerBasePath());
        beanConfig.setResourcePackage(resourcePackages[0]);
        beanConfig.setScan(true);        
        //packages("io.swagger.jaxrs.listing");
        //packages("io.swagger.jaxrs.json");
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        
        
        //add all packages to scan for resources
        for(String resourcePackage :  resourcePackages) {
            packages(resourcePackage);
        }
        //enable corst filter
        register(CORSFilter.class);
        //enable json serialization
        register(JsonObjectMapperProvider.class);
        register(JacksonFeature.class);
    }
}
