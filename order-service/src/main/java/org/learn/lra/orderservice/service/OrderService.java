package org.learn.lra.orderservice.service;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.orderservice.model.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class OrderService {

    private static final Logger log = Logger.getLogger(OrderService.class);

    @Inject
    private EntityManager entityManager;

    public Order createOrder(ProductInfo productInfo) {
        log.info("creating order");
        Order order = new Order(productInfo);
        entityManager.persist(order);
        return order;

    }
}
