package org.learn.lra.shipmentservice;

import org.jboss.logging.Logger;
import org.learn.lra.Util;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ShipmentInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class ShipmentService {

    private static final Logger log = Logger.getLogger(ShipmentService.class);

    public ShipmentInfo computeShipment(OrderInfo orderInfo) {
        //return testing stub
        String id = Util.generateId();

        return new ShipmentInfo(id , 42);
    }

    public void completeShipment(String lraId) {
        //TODO
    }

    public void compensateShipment(String lraId) {
        //TODO
    }

    @Inject
    private EntityManager entityManager;


    public void testPersist() {
        log.info("persisting...");
        entityManager.persist(new Shipment("testLRAId", 42));
        log.info("persisted....");
    }
}
