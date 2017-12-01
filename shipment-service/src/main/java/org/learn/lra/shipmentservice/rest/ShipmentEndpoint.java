package org.learn.lra.shipmentservice.rest;

import io.narayana.lra.annotation.LRA;
import org.learn.lra.Util;
import org.learn.lra.coreapi.LRAOperationAPI;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ShipmentInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/")
public class ShipmentEndpoint {

    @POST
    @Path(LRAOperationAPI.REQUEST)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @LRA(value = LRA.Type.REQUIRED)
    public ShipmentInfo requestShipment(OrderInfo orderInfo) {
        String id = Util.generateId();

        int price = computeShipment(orderInfo);

        return new ShipmentInfo(id, price);
    }

    private int computeShipment(OrderInfo orderInfo) {
        //return testing stub
        return 42;
    }
}
