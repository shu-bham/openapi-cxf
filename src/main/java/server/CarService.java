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

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("/car")
public class CarService {
    private Map<String, Car> cars;

    public CarService() {
        cars = Collections.synchronizedMap(new TreeMap<String, Car>(String.CASE_INSENSITIVE_ORDER));
        cars.put("Car 1", new Car("Car 1", "Value 1"));
        cars.put("Car 2", new Car("Car 2", "Value 2"));
    }

    @Produces({ MediaType.APPLICATION_JSON })
    @GET
    @Operation(
        summary = "Get all Cars",
        description = "Get operation with Response and @Default value",
        responses = {
            @ApiResponse(
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Car.class))),
                responseCode = "200"
            )
        }
    )
    public Response getCars(@Parameter(required = true) @QueryParam("page") @DefaultValue("1") int page) {
        return Response.ok(cars.values()).build();
    }

    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/{name}")
    @GET
    @Operation(
        summary = "Get Car by name",
        description = "Get operation with type and headers",
        responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = Car.class)), responseCode = "200"),
            @ApiResponse(responseCode = "404")
        }
    )
    public Response getCar(
            @Parameter(required = true) @HeaderParam("Accept-Language") final String language,
            @Parameter(required = true) @PathParam("name") String name) {
        return cars.containsKey(name)
            ? Response.ok().entity(cars.get(name)).build()
                : Response.status(Status.NOT_FOUND).build();
    }

    @Consumes({ MediaType.APPLICATION_JSON })
    @POST
    @Operation(
        summary = "Create new Car",
        description = "Post operation with entity in a body",
        responses = {
            @ApiResponse(
                content = @Content(
                    schema = @Schema(implementation = Car.class),
                    mediaType = MediaType.APPLICATION_JSON
                ),
                headers = @Header(name = "Location"),
                responseCode = "201"
            )
        }
    )
    public Response createCar(
        @Context final UriInfo uriInfo,
        @Parameter(required = true) final Car Car) {
        cars.put(Car.getName(), Car);
        return Response
            .created(uriInfo.getBaseUriBuilder().path(Car.getName()).build())
            .entity(Car).build();
    }

    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/{name}")
    @PUT
    @Operation(
        summary = "Update an existing new Car",
        description = "Put operation with form parameter",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Car.class)),
                responseCode = "200"
            )
        }
    )
    public Car updateCar(
            @Parameter(required = true) @PathParam("name") String name,
            @Parameter(required = true) @FormParam("value") String value) {
        Car Car = new Car(name, value);
        cars.put(name,  Car);
        return Car;
    }

    @Path("/{name}")
    @DELETE
    @Operation(
        summary = "Delete an existing new Car",
        description = "Delete operation with implicit header",
        responses = @ApiResponse(responseCode = "204")
    )
    @Parameter(
       name = "Accept-Language",
       description = "language",
       required = true,
       schema = @Schema(implementation = String.class),
       in = ParameterIn.HEADER
    )
    public void delete(@Parameter(required = true) @PathParam("name") String name) {
        cars.remove(name);
    }
}
