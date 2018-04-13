package org.learn.lra.apigateway.core;

import io.narayana.lra.client.NarayanaLRAClient;
import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Optional;

public class BeanConfiguration {

    private static final Logger log = Logger.getLogger(BeanConfiguration.class);
    private static final String SERVICE_NAME = "api-gateway";


    @Inject
    private Config config;


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
