package org.learn.lra.orderservice;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRAResult;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;

@Stateless
public class OrderDAO {

    private static final Logger log = Logger.getLogger(OrderDAO.class);

    @Inject
    private EntityManager em;

    public Order create(ProductInfo productInfo) {
        Order order = new Order(productInfo);
        em.persist(order);
        return order;
    }

    public void processLRAResult(LRAResult lraResult) {
        log.info("Received LRA result - " + lraResult);

        OrderInfo orderInfo = (OrderInfo) lraResult.getLraDefinition().getInfo();
        Order order = em.find(Order.class, orderInfo.getOrderId());
        order.setCompleted(true);
        em.merge(order);
        log.info("Order " + order.getId() + "processed");
    }

    @SuppressWarnings(value = "unchecked")
    public List<Order> getCompletedOrders() {
        Query query = em.createQuery("SELECT o FROM Order o WHERE o.completed = true");
        List resultList = query.getResultList();
        return (List<Order>) resultList;
    }
}
