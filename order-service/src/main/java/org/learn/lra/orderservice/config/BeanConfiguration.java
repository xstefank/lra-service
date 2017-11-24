package org.learn.lra.orderservice.config;

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
import feign.opentracing.TracingClient;
import io.opentracing.NoopTracerFactory;
import io.opentracing.Tracer;
import io.opentracing.contrib.web.servlet.filter.TracingFilter;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.logging.Logger;
import org.learn.lra.orderservice.lra.ApiClient;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

public class BeanConfiguration {

    private static final Logger log = Logger.getLogger(BeanConfiguration.class);
    private static final String SERVICE_NAME = "order";

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

        //TODO get host and port from properties
        String host = "api-gateway";
        String port = "8080";

        log.infof("API gateway expected at %s:%s", host, port);

        return HystrixFeign.builder()
                .client(new TracingClient(new ApacheHttpClient(HttpClientBuilder.create().build()), tracer))
                .logger(new feign.Logger.ErrorLogger()).logLevel(feign.Logger.Level.BASIC)
                .decoder(new JacksonDecoder())
                .target(ApiClient.class, String.format("http://%s:%s", host, port),
                        (String lraUri) -> "Order response (fallback)");

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
