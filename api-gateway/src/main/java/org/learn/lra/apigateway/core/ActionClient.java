package org.learn.lra.apigateway.core;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActionClient {

    @EJB
    private ServicesLocator servicesLocator;

    //TODO Client calls

}
