package org.learn.lra.invoiceservice;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.ProductInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class InvoiceService {

    private static final Logger log = Logger.getLogger(InvoiceService.class);

    @Inject
    private EntityManager entityManager;

    public void computeInvoice(String lraId, ProductInfo productInfo) {
        String invoiceString = createInvoice(productInfo);

        Invoice invoice = new Invoice(lraId, invoiceString);
        entityManager.persist(invoice);
        log.infof("Invoice for order saga %s persisted at %s", lraId, invoice.getId());
    }

    private String createInvoice(ProductInfo productInfo) {
        //return testing stub
        return "this is not the invoice you're looking for";
    }


    public void completeInvoice(String lraId) {
        Invoice invoice = findInvoiceForLRA(lraId);

        invoice.setComleted(true);
        entityManager.merge(invoice);

        log.infof("Invoice %s fully completed", invoice.getId());
    }

    public void compensateInvoice(String lraId) {
        Invoice invoice = findInvoiceForLRA(lraId);

        entityManager.remove(invoice);
        log.infof("Invoice %s fully compensated", invoice.getId());
    }

    @SuppressWarnings(value = "unchecked")
    public List<Invoice> findInvoices() {
        return (List<Invoice>) entityManager.createQuery("FROM Invoice").getResultList();
    }

    public Invoice findInvoice(String invoiceId) {
        return entityManager.createQuery("FROM Invoice WHERE id=:id", Invoice.class)
                .setParameter("id", invoiceId)
                .getSingleResult();
    }

    private Invoice findInvoiceForLRA(String lraId) {
        return entityManager.createQuery("FROM Invoice WHERE lraId=:lraId", Invoice.class)
                .setParameter("lraId", lraId)
                .getSingleResult();
    }
}
