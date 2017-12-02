package org.learn.lra.shipmentservice;

import org.jboss.logging.Logger;
import org.learn.lra.Util;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ShipmentInfo;

import javax.ejb.Stateless;

@Stateless
public class ShipmentService {

    private static final Logger log = Logger.getLogger(ShipmentService.class);

    public ShipmentInfo computeShipment(OrderInfo orderInfo) {
        //return testing stub
        String id = Util.generateId();

        return new ShipmentInfo(id , 42);
    }

    public void persistShipment(String lraId) {
        log.info("persisting shipment for LRA " + lraId);
        //TODO actual persist
    }

    public void compensateShipment(String lraId) {
        log.info("ocmpensating shipment for LRA " + lraId);
        //TODO
    }
}
