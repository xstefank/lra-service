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

    public void computeShipment(String lraId, OrderInfo orderInfo) {
        int shipmentPrice = calculateShipmentForOrder(orderInfo);

        Shipment shipment = new Shipment(orderInfo.getOrderId(), lraId, shipmentPrice);
        entityManager.persist(shipment);
        log.infof("Shipment for order %s persisted at %s", orderInfo.getOrderId(), shipment.getId());
    }

    private int calculateShipmentForOrder(OrderInfo orderInfo) {
        //return stub for now
        return 42;
    }

    public void completeShipment(String lraId) {
        Shipment shipment = findShipment(lraId);

        shipment.setComleted(true);
        entityManager.merge(shipment);

        log.infof("Shipment %s fully completed", shipment.getId());

    }

    public void compensateShipment(String lraId) {
        Shipment shipment = findShipment(lraId);

        entityManager.remove(shipment);
        log.infof("Shipment %s fully compensated", shipment.getId());
    }

    private Shipment findShipment(String lraId) {
        return entityManager.createQuery("FROM Shipment WHERE lraId=:lraId", Shipment.class)
                .setParameter("lraId", lraId)
                .getSingleResult();
    }

}
