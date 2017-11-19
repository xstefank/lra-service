package org.learn.lra.orderservice.service;

import io.narayana.lra.client.LRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.orderservice.model.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.UriInfo;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Stateless
public class OrderService {

    private static final Logger log = Logger.getLogger(OrderService.class);

    @Inject
    private EntityManager entityManager;

    @Inject
    private LRAClient lraClient;

    @Inject
    private UriInfo context;

    public Order createOrder(ProductInfo productInfo) {
        log.info("creating order...");

        String methodName = new Object().getClass().getEnclosingMethod().getName();
        URL lraUrlId = lraClient.startLRA(null, OrderService.class.getName() + "#"
                + methodName, 0L, TimeUnit.SECONDS);

        String recoveryPath = lraClient.joinLRA(lraUrlId, 0L, context.getBaseUri().toString(), null);
        log.infof("Starting LRA: %s when joining with baseUri: %s on enlistment gets recovery path: %s",
                lraUrlId, context.getBaseUri(), recoveryPath);

        Order order = new Order(productInfo);
        entityManager.persist(order);
        return order;

    }


}
