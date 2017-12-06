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
        String invoiceString = createInvoice(orderInfo);

        Invoice invoice = new Invoice(orderInfo.getOrderId(), lraId, invoiceString);
        entityManager.persist(invoice);
        log.infof("Invoice for order %s persisted at %s", orderInfo.getOrderId(), invoice.getId());
    }

    private String createInvoice(OrderInfo orderInfo) {
        //return testing stub
        return "this is not the invoice you're looking for";
    }


    public void completeInvoice(String lraId) {
        Invoice invoice = findInvoice(lraId);

        invoice.setComleted(true);
        entityManager.merge(invoice);

        log.infof("Invoice %s fully completed", invoice.getId());
    }

    public void compensateInvoice(String lraId) {
        Invoice invoice = findInvoice(lraId);

        entityManager.remove(invoice);
        log.infof("Invoice %s fully compensated", invoice.getId());
    }

    private Invoice findInvoice(String lraId) {
        return entityManager.createQuery("FROM Invoice WHERE lraId=:lraId", Invoice.class)
                .setParameter("lraId", lraId)
                .getSingleResult();
    }
}
