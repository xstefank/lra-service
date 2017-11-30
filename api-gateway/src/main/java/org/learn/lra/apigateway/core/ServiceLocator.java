package org.learn.lra.apigateway.core;

import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.Service;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Stateless
public class ServiceLocator {

    private static final String SERVICE_PREFIX = "lra.service";
    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String PROTOCOL = "http";

    private static final Logger log = Logger.getLogger(ServiceLocator.class);

    @Inject
    private Config config;

    private Map<Service, URL> serviceLocations = new HashMap<>();

    @PostConstruct
    public void initConfig() {
        Arrays.stream(Service.values())
                .forEach(service -> {
                    Optional<String> host = config.getOptionalValue(getPropertyName(service, HOST), String.class);
                    Optional<String> port = config.getOptionalValue(getPropertyName(service, PORT), String.class);

                    if (host.isPresent() && port.isPresent()) {
                        URL url = generateUrl(host.get(), port.get());
                        log.info("adding url " + url);
                        if (url != null) {
                            serviceLocations.put(service, url);
                        }
                    } else {
                        log.warnf("Incomplete config information for service %s, missing host or port", service);
                    }
                });

        log.infof("Known service locations: %s", serviceLocations);
    }

    private URL generateUrl(String host, String port) {
        try {
            String url = String.format("%s://%s:%s", PROTOCOL, host, port);
            return new URL(url);
        } catch (MalformedURLException e) {
            log.warn("Generated invalid URL", e);
        }

        return null;
    }

    private String getPropertyName(Service service, String suffix) {
        return String.format("%s.%s.%s", SERVICE_PREFIX, service.name().toLowerCase(), suffix);
    }

}
