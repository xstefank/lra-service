package org.learn.lra.orderservice;

import com.uber.jaeger.metrics.Metrics;
import com.uber.jaeger.metrics.NullStatsReporter;
import com.uber.jaeger.metrics.StatsFactoryImpl;
import com.uber.jaeger.reporters.RemoteReporter;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import com.uber.jaeger.senders.Sender;
import com.uber.jaeger.senders.UdpSender;
import feign.httpclient.ApacheHttpClient;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.opentracing.TracingClient;
import io.narayana.lra.client.LRAClient;
import io.narayana.lra.client.LRAClientAPI;
import io.opentracing.NoopTracerFactory;
import io.opentracing.Tracer;
import io.opentracing.contrib.web.servlet.filter.TracingFilter;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;
import org.learn.lra.coreapi.LRADefinition;

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

    public static final String APIGATEWAY_URL = "order.apigateway.url";
    public static final String APIGATEWAY_PORT = "order.apigateway.port";

    private static final Logger log = Logger.getLogger(BeanConfiguration.class);
    private static final String SERVICE_NAME = "order";

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
    @Singleton
    private ApiClient apiClient(Tracer tracer) {

        String host = config.getValue(APIGATEWAY_URL, String.class);
        String port = config.getValue(APIGATEWAY_PORT, String.class);

        log.infof("API gateway expected at %s:%s", host, port);

        return HystrixFeign.builder()
                .client(new TracingClient(new ApacheHttpClient(HttpClientBuilder.create().build()), tracer))
                .logger(new feign.Logger.ErrorLogger()).logLevel(feign.Logger.Level.BASIC)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ApiClient.class, String.format("http://%s:%s", host, port),
                        (LRADefinition lraDefinition) -> rx.Observable.empty());

    }

    @Produces
    @CurrentLRAClient
    public LRAClientAPI lraClient() {
        try {

            Optional<String> host = config.getOptionalValue("lra.coordinator.host", String.class);
            Optional<Integer> port = config.getOptionalValue("lra.coordinator.port", Integer.class);

            LRAClient lraClient = new LRAClient(host.orElse("lra-coordinator"), port.orElse(8080));
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
            filterRegistration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/order");
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {}
    }

}
