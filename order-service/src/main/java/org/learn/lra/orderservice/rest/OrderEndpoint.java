package org.learn.lra.orderservice.rest;

import io.swagger.annotations.ApiOperation;
import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.orderservice.model.Order;
import org.learn.lra.orderservice.model.OrderDAO;
import org.learn.lra.orderservice.OrderService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class OrderEndpoint {

    @EJB
    private OrderService orderService;

    @EJB
    private OrderDAO orderDAO;


    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createOrder(ProductInfo productInfo) {
        return orderService.createOrderSaga(productInfo);
    }

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getCompletedOrders() {
        return orderDAO.getCompletedOrders();
    }

    @GET
    @Path("/order/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("orderId") String orderId) {
        return orderDAO.getOrder(orderId);
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    @ApiOperation("Used to verify the health of the service")
    public String health() {
        return "I'm ok";
    }
}
