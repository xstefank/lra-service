package org.learn.lra.invoiceservice;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/")
public class InvoiceEndpoint {

	@GET
	@Path("/health")
	@Produces("text/plain")
	public String health() {
		return "I'm ok";
	}

	@GET
	@Produces("text/plain")
	public Response doGet() {
		return Response.ok("Hello from WildFly Swarm!").build();
	}
}
