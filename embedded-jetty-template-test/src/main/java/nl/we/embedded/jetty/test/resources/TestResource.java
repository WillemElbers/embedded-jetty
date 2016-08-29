package nl.we.embedded.jetty.test.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author wilelb
 */
@Path("/test")
@Produces({MediaType.APPLICATION_JSON})
public class TestResource {
    
    @GET
    public Response get() {
        return Response.ok("it works").build();
    }
    
}
