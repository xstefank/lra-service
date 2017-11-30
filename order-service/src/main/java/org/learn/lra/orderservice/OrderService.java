package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClientAPI;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.ActionType;
import org.learn.lra.coreapi.LRA;
import org.learn.lra.coreapi.LRABuilder;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.Service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        URL lraUrlId = lraClient.startLRA(null, OrderService.class.getName() + "#"
                + methodName, 0L, TimeUnit.SECONDS);

        String recoveryPath = lraClient.joinLRA(lraUrlId, 0L, baseUri, null);
        log.infof("Starting LRA: %s when joining with baseUri: %s on enlistment gets recovery path: %s",
                lraUrlId, baseUri, recoveryPath);

        log.info("testing order call for API-gateway");
        LRA lra = new LRABuilder()
                .id(lraUrlId.toString())
                .name(ORDER_LRA)
                .withAction(new Action("testAction1", ActionType.REQUEST, Service.SHIPMENT))
                .withAction(new Action("testAction2", ActionType.REQUEST, Service.INVOICE))
                .build();

        log.info(apiClient.processLRA(lra));

        Order order = new Order(productInfo);
//        entityManager.persist(order);
        return order;

    }


}
