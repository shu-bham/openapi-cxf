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

import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Api
public class BikeServiceImpl implements IBikeService {
  private Map<String, Bike> items;

  public BikeServiceImpl() {
    items = Collections.synchronizedMap(new TreeMap<String, Bike>(String.CASE_INSENSITIVE_ORDER));
    items.put("Bike 1", new Bike("Bike 1", "Value 1"));
    items.put("Bike 2", new Bike("Bike 2", "Value 2"));
  }

  @Override
  public Response getBikes(int page) {

    return Response.ok(items.values()).build();
  }

  @Override
  public Bike getBike(final String language, String name) {

    return items.get(name);
  }

  @Override
  public Response createBike(@Context final UriInfo uriInfo, final Bike item) {
    items.put(item.getName(), item);
    return Response.created(uriInfo.getBaseUriBuilder().path(item.getName()).build())
        .entity(item)
        .build();
  }

  @Override
  public Bike updateBike(String name, String value) {
    Bike item = new Bike(name, value);
    items.put(name, item);
    return item;
  }

  @Override
  public Response delete(String name) {
    items.remove(name);
    return Response.ok().build();
  }
}
