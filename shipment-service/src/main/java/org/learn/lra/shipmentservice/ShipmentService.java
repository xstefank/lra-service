package org.learn.lra.shipmentservice;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.OrderInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class ShipmentService {

    private static final Logger log = Logger.getLogger(ShipmentService.class);

    @Inject
    private EntityManager entityManager;

    public void computeShipment(OrderInfo orderInfo) {
        int shipmentPrice = calculateShipmentForOrder(orderInfo);

        Shipment shipment = new Shipment(orderInfo.getOrderId(), shipmentPrice);
        entityManager.persist(shipment);
        log.infof("Shipment for order %s persisted at %s", orderInfo.getOrderId(), shipment.getId());
    }

    private int calculateShipmentForOrder(OrderInfo orderInfo) {
        //return stub for now
        return 42;
    }

    public void completeShipment(String lraId) {
        //TODO
    }

    public void compensateShipment(String lraId) {
        //TODO
    }


    public void testPersist() {
        log.info("persisting...");
        entityManager.persist(new Shipment("testLRAId", 42));
        log.info("persisted....");
    }
}
