package org.learn.lra.apigateway;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.learn.lra.coreapi.LRA;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

//@Stateless
@Singleton
@Startup
public class LRAExecutor {

    @Inject
    @ConfigProperty(name = "lra.order.url", defaultValue = "order-service")
    private String orderUrl;

    @PostConstruct
    public void init() {
        System.out.println("XXXXXXXXXXXXX test");
        System.out.println("AAAAAAAAAAAAAAAa " + orderUrl);

    }

    public String processLRA(LRA lra) {
        return "Received lra - " + lra;
    }
}
