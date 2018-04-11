package org.learn.lra.orderservice;

import io.swagger.annotations.ApiOperation;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class OrderEndpoint {

    @EJB
    private OrderService orderService;

    @EJB
    private OrderDAO orderDAO;


    @POST
    @Path("/order")
    @Consumes("application/json")
    public void createOrder(ProductInfo productInfo) {
        orderService.createOrder(productInfo);
    }

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getCompletedOrders() {
        return orderDAO.getCompletedOrders();
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    @ApiOperation("Used to verify the health of the service")
    public String health() {
        return "I'm ok";
    }
}
