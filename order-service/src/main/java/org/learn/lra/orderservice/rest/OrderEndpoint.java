package org.learn.lra.orderservice.rest;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/order")
public class OrderEndpoint {


    @POST
    @Consumes("application/json")
    public void createOrder(ProductInfo productInfo) {
        Logger.getLogger(OrderEndpoint.class).info(String.format("%s %s %d", productInfo.getProductId(),
                productInfo.getComment(), productInfo.getPrice()));
    }
}
