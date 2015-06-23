package preso.rest.fun;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;

/**
 * ICNDB Service.
 */
public interface ICNDBService {

    @GET
    @Path("/{type}/{operation}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNode get(@PathParam("type") String type, @PathParam("operation") String operation, @QueryParam("firstName") String first, @QueryParam("lastName") String last);

    @GET
    @Path("/jokes")
    @Consumes(MediaType.APPLICATION_JSON)
    JsonNode all(@QueryParam("exclude") String exclude);

    @GET
    @Path("/jokes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    JsonNode byId(@PathParam("id") String path);

}
