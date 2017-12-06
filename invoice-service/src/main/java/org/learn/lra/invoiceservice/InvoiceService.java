package org.learn.lra.invoiceservice;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.OrderInfo;

import javax.ejb.Stateless;

@Stateless
public class InvoiceService {

    private static final Logger log = Logger.getLogger(InvoiceService.class);

    public void computeInvoice(String lraId, OrderInfo orderInfo) {
        //TODO
    }


    public void completeInvoice(String lraId) {
        //TODO
    }

    public void compensateInvoice(String lraId) {
        //TODO
    }
}
