package de.otto.logistics.challenge.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class HealthCheckRouter {

    @Value("${route.health}")
    private String route;

    @Bean
    public RouterFunction<ServerResponse> healthCheckRoute(HealthCheckHandler healthCheckHandler) {
        return RouterFunctions.route(GET(route).and(accept(MediaType.TEXT_PLAIN)), healthCheckHandler::health);
    }
}
