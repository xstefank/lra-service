package org.learn.lra.apigateway.rest;


import org.jboss.logging.Logger;
import org.learn.lra.apigateway.core.LRAExecutor;
import org.learn.lra.coreapi.LRADefinition;
import org.learn.lra.coreapi.LRAResult;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/lra")
public class LRAEndpoint {

    private static final Logger log = Logger.getLogger(LRAEndpoint.class);

    @EJB
    private LRAExecutor lraExecutor;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LRAResult lraRequest(LRADefinition lraDefinition) {
        log.info("received lra - " + lraDefinition);
        return lraExecutor.processLRA(lraDefinition, uriInfo.getBaseUri().toString());
    }



}
