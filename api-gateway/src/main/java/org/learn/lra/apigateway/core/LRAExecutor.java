package org.learn.lra.apigateway.core;

import io.narayana.lra.client.NarayanaLRAClient;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.LRADefinition;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Stateless
public class LRAExecutor {

    private static final String API_PREFIX = "api";
    private static final Logger log = Logger.getLogger(LRAExecutor.class);

    @Inject
    @CurrentLRAClient
    private NarayanaLRAClient lraClient;

    @Inject
    private ServicesLocator servicesLocator;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void processLRA(LRADefinition lraDefinition) {
        executor.submit(() -> process(lraDefinition));
    }

    private LRAResult process(LRADefinition lraDefinition) {
        log.infof("Processing LRA %s", lraDefinition);

        URL lraUrlId = startLRA();
        Object info = lraDefinition.getInfo();

        boolean needCompensation = lraDefinition.getActions().stream()
                .parallel()
                .map(a -> executeAction(a, info, lraUrlId.toString()))
                .anyMatch(x -> x.equals(Result.NEED_COMPENSATION));

        if (needCompensation) {
            lraClient.cancelLRA(lraUrlId);
        } else {
            lraClient.closeLRA(lraUrlId);
        }

        LRAResult lraResult = new LRAResult(lraDefinition, needCompensation ? Result.NEED_COMPENSATION : Result.COMPLETED);
        log.infof("Processed LRA %s with result %s", lraDefinition, lraResult.getResult());

        return lraResult;
    }

    private URL startLRA() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        URL lraUrlId = lraClient.startLRA(null, LRAExecutor.class.getName() + "#"
                + methodName, 0L, TimeUnit.SECONDS);

        return lraUrlId;
    }

    private Result executeAction(Action action, Object lraInfo, String lraUri) {
        log.infof("executing action - %s", action);

        Client client = ClientBuilder.newClient();
        URI build = UriBuilder
                .fromUri(servicesLocator.getServiceUri(action.getService()))
                .path(API_PREFIX)
                .path(action.getType().getPath())
                .build();
        log.info("action request url - " + build);
        WebTarget target = client.target(build);

        Response response = target.request().header(NarayanaLRAClient.LRA_HTTP_HEADER, lraUri).post(Entity.json(lraInfo));
        log.info("Result of action - " + response.readEntity(String.class));

        Result result = response.getStatus() == Response.Status.OK.getStatusCode() ? Result.COMPLETED : Result.NEED_COMPENSATION;

        response.close();

        return result;
    }

}
