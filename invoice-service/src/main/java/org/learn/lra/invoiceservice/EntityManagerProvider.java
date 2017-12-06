package org.learn.lra.invoiceservice;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProvider {

    @Produces
    @PersistenceContext(unitName = "invoicePU")
    private EntityManager entityManager;
}
