package org.learn.lra.orderservice;

import io.narayana.lra.client.LRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.ActionType;
import org.learn.lra.coreapi.LRABuilder;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.coreapi.Service;
import org.learn.lra.orderservice.model.OrderDAO;

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

    private LRABuilder lraDefinitionBuilder = new LRABuilder()
            .name(ORDER_LRA)
            .withAction(new Action("order persist", ActionType.REQUEST, Service.ORDER))
            .withAction(new Action("shipment request", ActionType.REQUEST, Service.SHIPMENT))
            .withAction(new Action("invoice request", ActionType.REQUEST, Service.INVOICE));


    public Response createOrderSaga(ProductInfo productInfo) {
        log.info("creating order saga...");

        String lraResult = apiClient.processLRA(lraDefinitionBuilder.lraInfo(productInfo).build());

        return Response.ok(lraResult).build();

    }


}
