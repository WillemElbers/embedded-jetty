package nl.we.embedded.jetty.rest;

import io.swagger.jaxrs.config.BeanConfig;
import nl.we.embedded.jetty.core.ServerConfig;
import nl.we.embedded.jetty.rest.providers.JsonObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author wilelb
 */
public class DefaultSwaggerApplication extends DefaultApplication {
    
    protected final BeanConfig beanConfig = new BeanConfig();
    
    public DefaultSwaggerApplication(String[] resourcePackages) {  
        super(resourcePackages);
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
        
        //enable json serialization
        register(JsonObjectMapperProvider.class);
        register(JacksonFeature.class);
    }
}
