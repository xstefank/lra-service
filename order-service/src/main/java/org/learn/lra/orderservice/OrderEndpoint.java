package org.learn.lra.orderservice;

import io.swagger.annotations.ApiOperation;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class OrderEndpoint {

    @EJB
    private OrderService orderService;

    @Context
    private UriInfo context;

    @POST
    @Path("/order")
    @Consumes("application/json")
    public void createOrder(ProductInfo productInfo) {
        orderService.createOrder(productInfo, context.getBaseUri().toString());
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    @ApiOperation("Used to verify the health of the service")
    public String health() {
        return "I'm ok";
    }
}
