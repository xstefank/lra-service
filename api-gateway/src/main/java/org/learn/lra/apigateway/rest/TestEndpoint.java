package org.learn.lra.apigateway.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestEndpoint {

    @GET
    public String testMessage() {
        return "test message from TestEndpoint - ";
    }
}
