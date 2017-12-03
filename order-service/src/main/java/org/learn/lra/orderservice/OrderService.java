package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClientAPI;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.ActionType;
import org.learn.lra.coreapi.LRA;
import org.learn.lra.coreapi.LRABuilder;
import org.learn.lra.coreapi.LRAResult;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.Service;
import org.learn.lra.orderservice.model.ProductLRAInfo;

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

    public Order createOrder(ProductInfo productInfo, String baseUri) {
        log.info("creating order...");


        LRA lra = new LRABuilder()
                .name(ORDER_LRA)
                .lraInfo(new ProductLRAInfo(productInfo))
                .withAction(new Action("testAction1", ActionType.REQUEST, Service.SHIPMENT))
                .withAction(new Action("testAction2", ActionType.REQUEST, Service.INVOICE))
                .build();

        LRAResult lraResult = apiClient.processLRA(lra);
        log.info("Received LRA result - " + lraResult);

        Order order = new Order(productInfo);
//        entityManager.persist(order);
        return order;

    }


}
