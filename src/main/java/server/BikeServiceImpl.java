/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class BikeServiceImpl implements IBikeService {
  private Map<String, Bike> bikes;

  public BikeServiceImpl() {
    bikes = Collections.synchronizedMap(new TreeMap<String, Bike>(String.CASE_INSENSITIVE_ORDER));
    bikes.put("Bike 1", new Bike("Bike 1", "Value 1"));
    bikes.put("Bike 2", new Bike("Bike 2", "Value 2"));
  }

  @Override
  @Operation(
          summary = "Get all Bikes",
          description = "Get operation with Response and @Default value",
          responses = {
                  @ApiResponse(
                          content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bike.class))),
                          responseCode = "200"
                  )
          }
  )
  public Response getBikes(@Parameter(required = true) @QueryParam("page") @DefaultValue("1") int page) {
    return Response.ok(bikes.values()).build();
  }

  @Override
  @Operation(
          summary = "Get Bike by name",
          description = "Get operation with type and headers",
          responses = {
                  @ApiResponse(content = @Content(schema = @Schema(implementation = Bike.class)), responseCode = "200"),
                  @ApiResponse(responseCode = "404")
          }
  )
  public Response getBike(
          @Parameter(required = true) @HeaderParam("Accept-Language") final String language,
          @Parameter(required = true)  String name) {
    return bikes.containsKey(name)
            ? Response.ok().entity(bikes.get(name)).build()
            : Response.status(Response.Status.NOT_FOUND).build();
  }

  @Override
  @Operation(
          summary = "Create new Bike",
          description = "Post operation with entity in a body",
          responses = {
                  @ApiResponse(
                          content = @Content(
                                  schema = @Schema(implementation = Bike.class),
                                  mediaType = MediaType.APPLICATION_JSON
                          ),
                          headers = @Header(name = "Location"),
                          responseCode = "201"
                  )
          }
  )
  public Response createBike(
          @Context final UriInfo uriInfo,
          @Parameter(required = true) final Bike bike) {
    bikes.put(bike.getName(), bike);
    return Response
            .created(uriInfo.getBaseUriBuilder().path(bike.getName()).build())
            .entity(bike).build();
  }

  @Override
  @Operation(
          summary = "Update an existing new Bike",
          description = "Put operation with form parameter",
          responses = {
                  @ApiResponse(
                          content = @Content(schema = @Schema(implementation = Bike.class)),
                          responseCode = "200"
                  )
          }
  )
  public Bike updateBike(
          @Parameter(required = true) String name,
          @Parameter(required = true) @FormParam("value") String value) {
    Bike bike = new Bike(name, value);
    bikes.put(name,  bike);
    return bike;
  }


  @Override
  @Operation(
          summary = "Delete an existing new Bike",
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
  public Response delete(@Parameter(required = true) String name) {
    bikes.remove(name);
    return Response.ok().build();
  }
}
