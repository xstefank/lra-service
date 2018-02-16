package org.learn.lra.apigateway.core;

import com.uber.jaeger.metrics.Metrics;
import com.uber.jaeger.metrics.NullStatsReporter;
import com.uber.jaeger.metrics.StatsFactoryImpl;
import com.uber.jaeger.reporters.RemoteReporter;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import com.uber.jaeger.senders.Sender;
import com.uber.jaeger.senders.UdpSender;
import io.narayana.lra.client.NarayanaLRAClient;
import io.opentracing.NoopTracerFactory;
import io.opentracing.Tracer;
import io.opentracing.contrib.web.servlet.filter.TracingFilter;
import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Optional;

public class BeanConfiguration {

    private static final Logger log = Logger.getLogger(BeanConfiguration.class);
    private static final String SERVICE_NAME = "api-gateway";


    @Inject
    private Config config;

    @Produces
    @Singleton
    public Tracer tracer() {
        String jaegerURL = System.getenv("JAEGER_SERVER_HOSTNAME");
        if (jaegerURL != null) {
            log.info("Using Jaeger tracer");
            return jaegerTracer(jaegerURL);
        }

        log.info("Using Noop tracer");
        return NoopTracerFactory.create();

    }

    private Tracer jaegerTracer(String url) {
        Sender sender = new UdpSender(url, 0, 0);
        return new com.uber.jaeger.Tracer.Builder(SERVICE_NAME,
                new RemoteReporter(sender, 100, 50,
                        new Metrics(new StatsFactoryImpl(new NullStatsReporter()))),
                new ProbabilisticSampler(1.0))
                .build();
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

    @WebListener
    public static class TracingFilterRegistration implements ServletContextListener {
        @Inject
        private Tracer tracer;

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            FilterRegistration.Dynamic filterRegistration = sce.getServletContext()
                    .addFilter("BraveServletFilter", new TracingFilter(tracer));
            // Explicit mapping to avoid trace on readiness probe
            filterRegistration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/api-gateway");
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {}
    }

}
