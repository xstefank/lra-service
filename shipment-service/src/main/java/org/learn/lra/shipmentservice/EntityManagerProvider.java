package org.learn.lra.shipmentservice;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProvider {

    @Produces
    @PersistenceContext(unitName = "shipmentPU")
    private EntityManager entityManager;
}
