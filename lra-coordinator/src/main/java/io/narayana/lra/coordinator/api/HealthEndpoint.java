package io.narayana.lra.coordinator.api;

import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/api")
public class HealthEndpoint {

    @GET
    @Path("/health")
    @Produces("text/plain")
    @ApiOperation("Used to verify the health of the service")
    public String health() {
        return "I'm ok";
    }
}
