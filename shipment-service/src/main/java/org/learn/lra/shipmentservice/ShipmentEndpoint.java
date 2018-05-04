package org.learn.lra.shipmentservice;

import io.narayana.lra.annotation.Compensate;
import io.narayana.lra.annotation.Complete;
import io.narayana.lra.annotation.LRA;
import io.narayana.lra.client.NarayanaLRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRAOperationAPI;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public Response requestShipment(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri, ProductInfo productInfo) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("processing request for LRA " + lraId);

        shipmentService.computeShipment(lraId, productInfo);

        //stub for compensation scenario
        if ("failShipment".equals(productInfo.getProductId())) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Shipment for order saga " + lraId + " failure")
                    .build();
        }

        return Response
                .ok()
                .entity(String.format("Shipment for order saga %s processed", lraId))
                .build();
    }

    @PUT
    @Path(LRAOperationAPI.COMPLETE)
    @Produces(MediaType.APPLICATION_JSON)
    @Complete
    public Response completeWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("completing shipment for LRA" + lraId);

        shipmentService.completeShipment(lraId);
        return Response.ok().build();
    }

    @PUT
    @Path(LRAOperationAPI.COMPENSATE)
    @Produces(MediaType.APPLICATION_JSON)
    @Compensate
    public Response compensateWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
        String lraId = NarayanaLRAClient.getLRAId(lraUri);
        log.info("compensating shipment for LRA " + lraId);


        shipmentService.compensateShipment(lraId);
        return Response.ok().build();
    }

    @GET
    @Path("/shipments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Shipment> getShipments() {
        return shipmentService.findShipments();
    }

    @GET
    @Path("/shipment/{shipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Shipment getShipment(@PathParam("shipmentId") String shipmentId) {
        return shipmentService.findShipment(shipmentId);
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    public String health() {
        return "I'm ok";
    }

}
