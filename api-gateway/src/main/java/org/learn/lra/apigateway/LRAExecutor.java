package org.learn.lra.apigateway;

import org.learn.lra.coreapi.LRA;

import javax.ejb.Stateless;

@Stateless
public class LRAExecutor {

    public String processLRA(LRA lra) {
        return "Received lra - " + lra;
    }
}
