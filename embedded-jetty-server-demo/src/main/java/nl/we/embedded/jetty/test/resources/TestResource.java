package nl.we.embedded.jetty.test.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        response = TestModel.class)
    public TestModel get() {
        TestModel model = new TestModel(true, "it works");
        return model;
    }
    
}
