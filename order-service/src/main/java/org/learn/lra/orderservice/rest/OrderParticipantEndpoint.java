package org.learn.lra.orderservice.rest;

import io.narayana.lra.annotation.Compensate;
import io.narayana.lra.annotation.Complete;
import io.narayana.lra.annotation.LRA;
import io.narayana.lra.client.NarayanaLRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRAOperationAPI;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.orderservice.model.OrderDAO;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class OrderParticipantEndpoint {

    private static final Logger log = Logger.getLogger(OrderParticipantEndpoint.class);

    @EJB
    private OrderDAO orderDAO;

    @POST
    @Path(LRAOperationAPI.REQUEST)
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @LRA(value = LRA.Type.REQUIRED)
    public Response persistOrder(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri, ProductInfo productInfo) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("processing request for LRA " + lraId);

        orderDAO.create(productInfo, lraId);

        return Response
                .ok()
                .entity(String.format("Order for saga %s processed", lraId))
                .build();
    }

    @PUT
    @Path(LRAOperationAPI.COMPLETE)
    @Produces(MediaType.APPLICATION_JSON)
    @Complete
    public Response completeWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("completing order for LRA" + lraId);

        orderDAO.completeOrder(lraId);
        return Response.ok().build();
    }

    @PUT
    @Path(LRAOperationAPI.COMPENSATE)
    @Produces(MediaType.APPLICATION_JSON)
    @Compensate
    public Response compensateWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("compensating order for LRA " + lraId);

        orderDAO.compensateOrder(lraId);
        return Response.ok().build();
    }
}
