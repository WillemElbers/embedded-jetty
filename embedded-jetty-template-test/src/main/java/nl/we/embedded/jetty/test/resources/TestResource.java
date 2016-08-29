package nl.we.embedded.jetty.test.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "test")
@Produces({MediaType.APPLICATION_JSON})
public class TestResource {
    
    @GET
    @ApiOperation(
        value = "Test endpoint",
        notes = "Just return a simple message to show this is working",
        response = String.class)
    public Response get() {
        return Response.ok("it works").build();
    }
    
}
