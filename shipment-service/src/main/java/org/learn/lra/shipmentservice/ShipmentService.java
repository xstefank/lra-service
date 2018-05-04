package org.learn.lra.shipmentservice;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class ShipmentService {

    private static final Logger log = Logger.getLogger(ShipmentService.class);

    @Inject
    private EntityManager entityManager;

    public void computeShipment(String lraId, ProductInfo productInfo) {
        int shipmentPrice = calculateShipmentForOrder(productInfo);

        Shipment shipment = new Shipment(lraId, shipmentPrice);
        entityManager.persist(shipment);
        log.infof("Shipment for order saga %s persisted at %s", lraId, shipment.getId());
    }

    private int calculateShipmentForOrder(ProductInfo productInfo) {
        //return stub for now
        return 42;
    }

    public void completeShipment(String lraId) {
        Shipment shipment = findShipmentForLRA(lraId);

        shipment.setComleted(true);
        entityManager.merge(shipment);

        log.infof("Shipment %s fully completed", shipment.getId());

    }

    public void compensateShipment(String lraId) {
        Shipment shipment = findShipmentForLRA(lraId);

        entityManager.remove(shipment);
        log.infof("Shipment %s fully compensated", shipment.getId());
    }

    @SuppressWarnings(value = "unchecked")
    public List<Shipment> findShipments() {
        return (List<Shipment>) entityManager.createQuery("FROM Shipment").getResultList();
    }

    public Shipment findShipment(String shipmentId) {
        return entityManager.createQuery("FROM Shipment WHERE id=:id", Shipment.class)
                .setParameter("id", shipmentId)
                .getSingleResult();
    }

    private Shipment findShipmentForLRA(String lraId) {
        return entityManager.createQuery("FROM Shipment WHERE lraId=:lraId", Shipment.class)
                .setParameter("lraId", lraId)
                .getSingleResult();
    }

}
