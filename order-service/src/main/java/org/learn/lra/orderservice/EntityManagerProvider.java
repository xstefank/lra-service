package org.learn.lra.orderservice;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProvider {

    @Produces
    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;
}
