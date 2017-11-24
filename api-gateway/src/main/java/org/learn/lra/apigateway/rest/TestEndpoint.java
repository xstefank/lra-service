package org.learn.lra.apigateway.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/test")
public class TestEndpoint {

    @POST
    public String testMessage(String msg) {
        return "test message from TestEndpoint - " + msg;
    }

}
