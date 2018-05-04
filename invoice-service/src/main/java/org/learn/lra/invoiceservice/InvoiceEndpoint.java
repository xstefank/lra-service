package org.learn.lra.invoiceservice;


import io.narayana.lra.annotation.Compensate;
import io.narayana.lra.annotation.Complete;
import io.narayana.lra.annotation.LRA;
import io.narayana.lra.client.NarayanaLRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRAOperationAPI;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/")
public class InvoiceEndpoint {

	private static final Logger log = Logger.getLogger(InvoiceEndpoint.class);

	@EJB
	private InvoiceService invoiceService;

	@POST
	@Path(LRAOperationAPI.REQUEST)
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@LRA(value = LRA.Type.REQUIRED)
	public Response requestInvoice(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri, ProductInfo productInfo) {
		String lraId = NarayanaLRAClient.getLRAId(lraUri);
		log.info("processing request for LRA " + lraId);

		invoiceService.computeInvoice(lraId, productInfo);

		//stub for compensation scenario
		if ("failInvoice".equals(productInfo.getProductId())) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Invoice for order saga " + lraId + " failure")
					.build();
		}

		return Response
				.ok()
				.entity(String.format("Invoice for order saga %s processed", lraId))
				.build();
	}

	@PUT
	@Path(LRAOperationAPI.COMPLETE)
	@Produces(MediaType.APPLICATION_JSON)
	@Complete
	public Response completeWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
		String lraId = NarayanaLRAClient.getLRAId(lraUri);
		log.info("completing invoice for LRA " + lraId);

		invoiceService.completeInvoice(lraId);
		return Response.ok().build();
	}

	@PUT
	@Path(LRAOperationAPI.COMPENSATE)
	@Produces(MediaType.APPLICATION_JSON)
	@Compensate
	public Response compensateWork(@HeaderParam(NarayanaLRAClient.LRA_HTTP_HEADER) String lraUri) {
		String lraId = NarayanaLRAClient.getLRAId(lraUri);
		log.info("compensating invoice for LRA " + lraId);


		invoiceService.compensateInvoice(lraId);
		return Response.ok().build();
	}

	@GET
	@Path("/invoices")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Invoice> getInvoices() {
		return invoiceService.findInvoices();
	}

	@GET
	@Path("/invoice/{invoiceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Invoice getInvoice(@PathParam("invoiceId") String invoiceId) {
        return invoiceService.findInvoice(invoiceId);
	}

	@GET
	@Path("/health")
	@Produces("text/plain")
	public String health() {
		return "I'm ok";
	}

}
