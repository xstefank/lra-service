package org.learn.lra.orderservice.rest;

import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.orderservice.service.OrderService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/order")
public class OrderEndpoint {

    @EJB
    private OrderService orderService;

    @POST
    @Consumes("application/json")
    public void createOrder(ProductInfo productInfo) {
        orderService.createOrder(productInfo);
    }
}
