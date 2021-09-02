JAX-RS OpenApiFeature Demo using WebApp with Spring
=================

The demo shows a basic usage of Swagger API documentation with multiple REST based Web Services using 
JAX-RS 2.0 (JSR-339). In this demo, the OpenApiFeature is configured using Spring.

Building and running the demo using Maven
---------------------------------------

From the base directory of this sample (i.e., where this README file is
located), the Maven pom.xml file can be used to build and run the demo. 


Using either UNIX or Windows:

  mvn install
  mvn jetty:run-war


Two JAX-RS endpoints are available after the service has started. 
Swagger API documents in JSON and YAML are available at

  http://localhost:9090/app/carSample/openapi.json
  http://localhost:9090/app/carSample/openapi.yaml

and

  http://localhost:9090/app/bikeSample/openapi.json
  http://localhost:9090/app/bikeSample/openapi.yaml

To view the Swagger document using Swagger-UI, use your Browser to 
open the Swagger-UI page at

  http://localhost:9090/app/bikeSample/api-docs?url=/app/bikeSample/openapi.json
  or
  http://localhost:9090/app/carSample/api-docs?url=/app/carSample/openapi.json

or go to the CXF services page:

  http://localhost:9090/app

and follow Swagger links.

To remove the target dir, run mvn clean".



