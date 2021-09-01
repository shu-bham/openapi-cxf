package server;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/bike")
public interface IBikeService {
  @Produces({MediaType.APPLICATION_JSON})
  @GET
  Response getBikes(@QueryParam("page")  int page);

  @Produces({MediaType.APPLICATION_JSON})
  @Path("/{name}")
  @GET
  Response getBike(@HeaderParam("Accept-Language") String language, @PathParam("name") String name);

  @Consumes({MediaType.APPLICATION_JSON})
  @POST
  Response createBike(@Context UriInfo uriInfo, Bike item);

  @Produces({MediaType.APPLICATION_JSON})
  @Path("/{name}")
  @PUT
  Bike updateBike(@PathParam("name") String name, @FormParam("value") String value);

  @Path("/{name}")
  @DELETE
  Response delete(@PathParam("name") String name);
}
