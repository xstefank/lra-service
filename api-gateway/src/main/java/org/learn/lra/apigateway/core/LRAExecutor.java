package org.learn.lra.apigateway.core;

import io.narayana.lra.client.LRAClientAPI;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.LRA;
import org.learn.lra.coreapi.LRAInfo;
import org.learn.lra.coreapi.LRAResult;
import org.learn.lra.coreapi.Result;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Stateless
public class LRAExecutor {

    private static final Logger log = Logger.getLogger(LRAExecutor.class);

//    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Inject
    @CurrentLRAClient
    private LRAClientAPI lraClient;

    @Inject
    private ServicesLocator servicesLocator;

    public LRAResult processLRA(LRA lra, String baseUri) {

        log.infof("Processing LRA %s", lra);

        URL lraUrlId = startLRA(baseUri);

        boolean needCompensation = lra.getActions().stream()
                .map(a -> executeAction(a, lra.getInfo()))
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

    private Result executeAction(Action action, LRAInfo<?> lraInfo) {
        log.infof("executing action - %s", action);

        Client client = ClientBuilder.newClient();
        URI build = UriBuilder
                .fromUri(servicesLocator.getServiceUri(action.getService()))
                .path(action.getType().getPath())
                .build();
        WebTarget target = client.target(build);

        //TODO make it post some info about LRA?
        Response response = target.request().post(Entity.json(lraInfo));
        //TODO get value
        response.close();

        return Result.COMPLETED;
    }

//    public Future<String> processLRA(LRA lra) {
//
//        log.infof("Processing LRA %s with id - %s", lra.getName(), lra.getId());
//        return executorService.submit(new LRAWorker(lra));
//    }
}
