package org.learn.lra.orderservice.rest;

import org.learn.lra.coreapi.ProductInfo;
import org.learn.lra.orderservice.service.OrderService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/order")
public class OrderEndpoint {

    @EJB
    private OrderService orderService;

    @Context
    private UriInfo context;

    @POST
    @Consumes("application/json")
    public void createOrder(ProductInfo productInfo) {
        orderService.createOrder(productInfo, context.getBaseUri().toString());
    }
}
