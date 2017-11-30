package org.learn.lra.apigateway.core;

import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.LRA;

import java.util.concurrent.Callable;

public class LRAWorker implements Callable<String> {

    private static final Logger log = Logger.getLogger(LRAWorker.class);

    private final LRA lra;

    public LRAWorker(LRA lra) {
        this.lra = lra;
    }

    @Override
    public String call() throws Exception {

        lra.getActions().forEach(this::executeAction);
        return "";
    }

    private void executeAction(Action action) {
        //TODO
    }
}
