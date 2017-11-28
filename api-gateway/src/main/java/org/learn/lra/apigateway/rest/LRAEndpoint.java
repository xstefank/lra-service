package org.learn.lra.apigateway.rest;


import org.jboss.logging.Logger;
import org.learn.lra.apigateway.LRAExecutor;
import org.learn.lra.coreapi.LRA;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/lra")
public class LRAEndpoint {

    private static final Logger log = Logger.getLogger(LRAEndpoint.class);

    @EJB
    private LRAExecutor lraExecutor;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String lraRequest(LRA lra) {
        log.info("received lra - " + lra);
        return lraExecutor.processLRA(lra);
    }



}
