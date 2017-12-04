package org.learn.lra.shipmentservice;

import io.narayana.lra.annotation.Compensate;
import io.narayana.lra.annotation.Complete;
import io.narayana.lra.annotation.LRA;
import io.narayana.lra.client.LRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRAOperationAPI;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.ShipmentInfo;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/")
public class ShipmentEndpoint {

    private static final Logger log = Logger.getLogger(ShipmentEndpoint.class);

    @EJB
    private ShipmentService shipmentService;

    @POST
    @Path(LRAOperationAPI.REQUEST)
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @LRA(value = LRA.Type.REQUIRED)
    public String requestShipment(@HeaderParam(LRAClient.LRA_HTTP_HEADER) String lraUri, OrderInfo orderInfo) {
        String lraId = LRAClient.getLRAId(lraUri);
        log.info("processing request for LRA " + lraId);

        shipmentService.computeShipment(lraId, orderInfo);
        return String.format("Shipment for order %s processed", orderInfo.getOrderId());
    }

    @PUT
    @Path(LRAOperationAPI.COMPLETE)
    @Produces(MediaType.APPLICATION_JSON)
    @Complete
    public Response completeWork(@HeaderParam(LRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = LRAClient.getLRAId(lraUri);
        log.info("completing shipment for LRA " + lraId);

        shipmentService.completeShipment(lraId);
        return Response.ok().build();
    }

    @PUT
    @Path(LRAOperationAPI.COMPENSATE)
    @Produces(MediaType.APPLICATION_JSON)
    @Compensate
    public Response compensateWork(@HeaderParam(LRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = LRAClient.getLRAId(lraUri);
        log.info("compensating shipment for LRA " + lraId);


        shipmentService.compensateShipment(lraId);
        return Response.ok().build();
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    public String health() {
        return "I'm ok";
    }

}
