package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.LRA;
import org.learn.lra.coreapi.LRABuilder;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.Service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class OrderService {

    private static final Logger log = Logger.getLogger(OrderService.class);

    @Inject
    private EntityManager entityManager;

    @Inject
    private LRAClient lraClient;

    @Inject
    private ApiClient apiClient;

    public Order createOrder(ProductInfo productInfo, String baseUri) {
        log.info("creating order...");

//        String methodName = Object.class.getEnclosingMethod().getName();
//        URL lraUrlId = lraClient.startLRA(null, OrderService.class.getName() + "#"
//                + methodName, 0L, TimeUnit.SECONDS);
//
//        String recoveryPath = lraClient.joinLRA(lraUrlId, 0L, baseUri, null);
//        log.infof("Starting LRA: %s when joining with baseUri: %s on enlistment gets recovery path: %s",
//                lraUrlId, baseUri, recoveryPath);

        log.info("testing order call for API-gateway");
        LRA lra = new LRABuilder()
                .id("testing id")
                .name("OrderLRA")
                .withAction(new Action("testAction1", Service.SHIPMENT))
                .withAction(new Action("testAction2", Service.INVOICE))
                .build();

        log.info(apiClient.processLRA(lra));

        Order order = new Order(productInfo);
//        entityManager.persist(order);
        return order;

    }


}
