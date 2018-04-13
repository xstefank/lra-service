package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.ActionType;
import org.learn.lra.coreapi.LRABuilder;
import org.learn.lra.coreapi.LRADefinition;
import org.learn.lra.coreapi.LRAResult;
import org.learn.lra.coreapi.OrderInfo;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.Service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Stateless
public class OrderService {

    private static final String ORDER_LRA = "OrderLRA";
    private static final Logger log = Logger.getLogger(OrderService.class);

    @Inject
    private OrderDAO orderDAO;

    @Inject
    @CurrentLRAClient
    private LRAClient lraClient;

    @Inject
    private ApiClient apiClient;

    public Response createOrder(ProductInfo productInfo) {
        log.info("creating order...");

        Order order = orderDAO.create(productInfo);

        LRADefinition lraDefinition = new LRABuilder()
                .name(ORDER_LRA)
                .lraInfo(new OrderInfo(order.getId(), productInfo))
                .withAction(new Action("shipment request", ActionType.REQUEST, Service.SHIPMENT))
                .withAction(new Action("invoice request", ActionType.REQUEST, Service.INVOICE))
                .build();

        LRAResult lraResult = apiClient.processLRA(lraDefinition);
        orderDAO.processLRAResult(lraResult);


        return Response.ok(order.getId()).build();

    }


}
