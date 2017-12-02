package org.learn.lra.apigateway.core;

import io.narayana.lra.client.LRAClientAPI;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.LRA;
import org.learn.lra.coreapi.LRAResult;
import org.learn.lra.coreapi.Result;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Stateless
public class LRAExecutor {

    private static final Logger log = Logger.getLogger(LRAExecutor.class);

//    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Inject
    @CurrentLRAClient
    private LRAClientAPI lraClient;

    public LRAResult processLRA(LRA lra, String baseUri) {

        log.infof("Processing LRA %s", lra);

        URL lraUrlId = startLRA(baseUri);

        boolean needCompensation = lra.getActions().stream()
                .map(this::executeAction)
                .anyMatch(x -> x.equals(Result.NEED_COMPENSATION));

        if (needCompensation) {
            lraClient.cancelLRA(lraUrlId);
        } else {
            lraClient.closeLRA(lraUrlId);
        }

        return new LRAResult(lra, needCompensation ? Result.NEED_COMPENSATION : Result.COMPLETED);
    }

    private URL startLRA(String baseUri) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        URL lraUrlId = lraClient.startLRA(null, LRAExecutor.class.getName() + "#"
                + methodName, 0L, TimeUnit.SECONDS);

        String recoveryPath = lraClient.joinLRA(lraUrlId, 0L, baseUri, null);
        log.infof("Starting LRA: %s when joining with baseUri: %s on enlistment gets recovery path: %s",
                lraUrlId, baseUri, recoveryPath);
        return lraUrlId;
    }

    private Result executeAction(Action action) {
        log.infof("executing action - %s", action);

        return Result.COMPLETED;
    }

//    public Future<String> processLRA(LRA lra) {
//
//        log.infof("Processing LRA %s with id - %s", lra.getName(), lra.getId());
//        return executorService.submit(new LRAWorker(lra));
//    }
}
