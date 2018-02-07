package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClientAPI;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.ActionType;
import org.learn.lra.coreapi.LRADefinition;
import org.learn.lra.coreapi.LRABuilder;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.Service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class OrderService {

    private static final String ORDER_LRA = "OrderLRA";
    private static final Logger log = Logger.getLogger(OrderService.class);

    @Inject
    private EntityManager entityManager;

    @Inject
    @CurrentLRAClient
    private LRAClientAPI lraClient;

    @Inject
    private ApiClient apiClient;

    public Order createOrder(ProductInfo productInfo) {
        log.info("creating order...");

        Order order = new Order(productInfo);
        entityManager.persist(order);

        LRADefinition lraDefinition = new LRABuilder()
                .name(ORDER_LRA)
                .lraInfo(new OrderInfo(order.getId(), productInfo))
                .withAction(new Action("shipment request", ActionType.REQUEST, Service.SHIPMENT))
                .withAction(new Action("invoice request", ActionType.REQUEST, Service.INVOICE))
                .build();

        apiClient.processLRA(lraDefinition)
                .defaultIfEmpty(null)
                .subscribe(lraResult -> log.info("Received LRA result - " + lraResult));


        return order;

    }


}
