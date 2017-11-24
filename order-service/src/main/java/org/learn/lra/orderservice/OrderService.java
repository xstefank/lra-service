package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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

        log.info("testing call for API-gateway");
        log.info(apiClient.test());

        Order order = new Order(productInfo);
//        entityManager.persist(order);
        return order;

    }


}
