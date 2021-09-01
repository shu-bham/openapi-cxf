package server;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public interface IBikeService {
  @Produces({MediaType.APPLICATION_JSON})
  @GET
  Response getBikes(@QueryParam("page")  int page);

  @Produces({MediaType.APPLICATION_JSON})
  @Path("/{name}")
  @GET
  Bike getBike(@HeaderParam("Accept-Language") String language, String name);

  @Consumes({MediaType.APPLICATION_JSON})
  @POST
  Response createBike(@Context UriInfo uriInfo, Bike item);

  @Produces({MediaType.APPLICATION_JSON})
  @Path("/{name}")
  @PUT
  Bike updateBike(String name, @FormParam("value") String value);

  @Path("/{name}")
  @DELETE
  Response delete(@PathParam("name") String name);
}
