/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package server;



import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Path("/")
@Api
public class CarService {
    private Map<String, Car> items;

    public CarService() {
        items = Collections.synchronizedMap(new TreeMap<String, Car>(String.CASE_INSENSITIVE_ORDER));
        items.put("Car 1", new Car("Car 1", "Value 1"));
        items.put("Car 2", new Car("Car 2", "Value 2"));
    }


    @Produces({ MediaType.APPLICATION_JSON })
    @GET
    public Response getCars(
         @QueryParam("page")  int page) {

        return Response.ok(items.values()).build();
    }

    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/{name}")
    @GET
    public Car getCar(
         @HeaderParam("Accept-Language") final String language,
        @PathParam("name") String name) {
        return items.get(name);
    }

    @Consumes({ MediaType.APPLICATION_JSON })
    @POST
    public Response createCar(
        @Context final UriInfo uriInfo,
         final Car item) {
        items.put(item.getName(), item);
        return Response
            .created(uriInfo.getBaseUriBuilder().path(item.getName()).build())
            .entity(item).build();
    }

    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/{name}")
    @PUT
    public Car updateCar(
         @PathParam("name") String name,
         @FormParam("value") String value) {
        Car item = new Car(name, value);
        items.put(name,  item);
        return item;
    }

    @Path("/{name}")
    @DELETE

    public Response delete( @PathParam("name") String name) {
        items.remove(name);
        return Response.ok().build();
    }
}
