package nl.we.embedded.jetty.test.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    
    @Path("/test2")
    @GET
    @ApiOperation(
        value = "Second test endpoint",
        notes = "Just return a simple message to show this is working as well",
        response = TestModel.class)
    public TestModel get2() {
        TestModel model = new TestModel(true, "this works too");
        return model;
    }
    
    /*
    @Path("/test3")
    @GET
    @ApiOperation(
        value = "Third test endpoint with parameter",
        notes = "Just return a simple message to show this is working as well",
        response = TestModel.class)
    public TestModel get3(@ApiParam(value = "x", required = true) @PathParam("x") String x) {
        TestModel model = new TestModel(true, "this works too, argument x = ["+x+"]");
        return model;
    }
    */
}
