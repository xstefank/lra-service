package org.learn.lra.orderservice;

import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonEncoder;
import io.narayana.lra.client.NarayanaLRAClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URISyntaxException;
import java.util.Optional;

public class BeanConfiguration {

    public static final String APIGATEWAY_URL = "order.apigateway.url";
    public static final String APIGATEWAY_PORT = "order.apigateway.port";

    private static final Logger log = Logger.getLogger(BeanConfiguration.class);

    @Inject
    private Config config;


    @Produces
    @Singleton
    private ApiClient apiClient() {

        String host = config.getValue(APIGATEWAY_URL, String.class);
        String port = config.getValue(APIGATEWAY_PORT, String.class);

        log.infof("API gateway expected at %s:%s", host, port);

        return Feign.builder()
                .client(new ApacheHttpClient(HttpClientBuilder.create().build()))
                .logger(new feign.Logger.ErrorLogger()).logLevel(feign.Logger.Level.BASIC)
                .encoder(new JacksonEncoder())
                .target(ApiClient.class, String.format("http://%s:%s", host, port));

    }

    @Produces
    @CurrentLRAClient
    public NarayanaLRAClient lraClient() {
        try {

            Optional<String> host = config.getOptionalValue("lra.coordinator.host", String.class);
            Optional<Integer> port = config.getOptionalValue("lra.coordinator.port", Integer.class);

            NarayanaLRAClient lraClient = new NarayanaLRAClient(host.orElse("lra-coordinator"), port.orElse(8080));
            log.info(">>> LRA coordinator to connect is at " + lraClient.getUrl());
            return lraClient;
        } catch (URISyntaxException urise) {
            throw new IllegalStateException("Can't initalize a new LRA client", urise);
        }
    }


}
