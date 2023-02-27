package de.otto.logistics.challenge.health;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration(proxyBeanMethods = false)
public class HealthCheckRouter {

    @Value("${route.health}")
    private String route;

    @Bean
    @RouterOperation(path = "/health", produces = {
        MediaType.APPLICATION_JSON_VALUE},
        beanClass = HealthCheckHandler.class, method = RequestMethod.GET, beanMethod = "health",
        operation = @Operation(operationId = "health", responses = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                content = @Content(schema = @Schema(implementation = String.class)))}
        ))
    public RouterFunction<ServerResponse> healthCheckRoute(HealthCheckHandler healthCheckHandler) {
        return RouterFunctions.route(GET(route).and(accept(MediaType.TEXT_PLAIN)), healthCheckHandler::health);
    }
}
