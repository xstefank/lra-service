package org.learn.lra.orderservice.model;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class OrderDAO {

    private static final Logger log = Logger.getLogger(OrderDAO.class);

    @Inject
    private EntityManager em;

    public Order create(ProductInfo productInfo, String lraId) {
        Order order = new Order(productInfo, lraId);
        em.persist(order);
        return order;
    }

    public void completeOrder(String lraId) {
        log.info("completing order lra - " + lraId);

        Order order = (Order) em.createQuery("SELECT o FROM Order o WHERE o.lraId = :value1")
                .setParameter("value1", lraId).getSingleResult();

        order.setCompleted(true);
        em.merge(order);
        log.info("Order " + order.getId() + " completed");
    }

    public void compensateOrder(String lraId) {
        log.info("compensating order lra - " + lraId);

        Order order = (Order) em.createQuery("SELECT o FROM Order o WHERE o.lraId = :value1")
                .setParameter("value1", lraId).getSingleResult();

        em.detach(order);
        log.info("Order " + order.getId() + " compensated");
    }

    @SuppressWarnings(value = "unchecked")
    public List<Order> getCompletedOrders() {
        Query query = em.createQuery("SELECT o FROM Order o WHERE o.completed = true");
        List resultList = query.getResultList();
        return (List<Order>) resultList;
    }

    public Order getOrder(String orderId) {
        return em.createQuery("FROM Order WHERE id=:id", Order.class)
                .setParameter("id", orderId)
                .getSingleResult();
    }
}
