package org.learn.lra.invoiceservice;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.OrderInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class InvoiceService {

    private static final Logger log = Logger.getLogger(InvoiceService.class);

    @Inject
    private EntityManager entityManager;

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
