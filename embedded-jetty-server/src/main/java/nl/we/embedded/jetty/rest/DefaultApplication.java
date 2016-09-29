package nl.we.embedded.jetty.rest;

import nl.we.jaxrs.filters.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author wilelb
 */
public class DefaultApplication extends ResourceConfig {
    
    public DefaultApplication(String[] resourcePackages) {            
        //add all packages to scan for resources
        for(String resourcePackage :  resourcePackages) {
            packages(resourcePackage);
        }
        //enable corst filter
        register(CORSFilter.class);
        //enable json serialization
        //register(JsonObjectMapperProvider.class);
        //register(JacksonFeature.class);
    }
}
