package org.learn.lra.apigateway.core;

import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Service;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ServicesLocator {

    private static final String SERVICE_PREFIX = "lra.service";
    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String PROTOCOL = "http";

    private static final Logger log = Logger.getLogger(ServicesLocator.class);

    @Inject
    private Config config;

    private Map<Service, URI> serviceLocations = new HashMap<>();

    @PostConstruct
    public void initConfig() {
        Arrays.stream(Service.values())
                .forEach(service -> {
                    Optional<String> host = config.getOptionalValue(getPropertyName(service, HOST), String.class);
                    Optional<String> port = config.getOptionalValue(getPropertyName(service, PORT), String.class);

                    if (host.isPresent() && port.isPresent()) {
                        URI uri = generateUrl(host.get(), port.get());
                        if (uri!= null) {
                            serviceLocations.put(service, uri);
                        }
                    } else {
                        log.warnf("Incomplete config information for service %s, missing host or port", service);
                    }
                });

        log.infof("Known service locations: %s", serviceLocations);
    }

    private URI generateUrl(String host, String port) {
        try {
            String url = String.format("%s://%s:%s", PROTOCOL, host, port);
            return new URI(url);
        } catch (URISyntaxException e) {
            log.warn("Generated invalid URI", e);
        }

        return null;
    }

    private String getPropertyName(Service service, String suffix) {
        return String.format("%s.%s.%s", SERVICE_PREFIX, service.name().toLowerCase(), suffix);
    }

    public URI getServiceUri(Service service) {
        return serviceLocations.get(service);
    }

}
