package org.learn.lra.apigateway.rest;

import io.narayana.lra.annotation.Compensate;
import io.narayana.lra.annotation.Complete;
import io.narayana.lra.client.NarayanaLRAClient;
import io.swagger.annotations.ApiOperation;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRAOperationAPI;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ApiGatewayEndpoint {

    private static final Logger log = Logger.getLogger(ApiGatewayEndpoint.class);

    @PUT
    @Path(LRAOperationAPI.COMPLETE)
    @Produces(MediaType.APPLICATION_JSON)
    @Complete
    public Response completeWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("completing LRA " + lraId);

        return Response.ok().build();
    }

    @PUT
    @Path(LRAOperationAPI.COMPENSATE)
    @Produces(MediaType.APPLICATION_JSON)
    @Compensate
    public Response compensateWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("compensating LRADefinition " + lraId);

        return Response.ok().build();
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    @ApiOperation("Used to verify the health of the service")
    public String health() {
        return "I'm ok";
    }
}
