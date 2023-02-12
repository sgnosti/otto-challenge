package de.otto.logistics.challenge.iprange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class IpRangeFilterHandler {

    @Autowired
    private IpRangesClient client;

    @Value("${ipranges.validRegions}")
    private String[] regions;

    public Mono<ServerResponse> filter(ServerRequest request) {
        return handle(request)
                .flatMap(s -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(s))
                .onErrorResume(
                        e -> ServerResponse.badRequest().contentType(MediaType.TEXT_PLAIN).bodyValue(e.getMessage()));
    }

    Mono<String> handle(ServerRequest request) {
        try {
            String regionParam = request.queryParam("region").orElseThrow();
            String regionPrefix = Stream.of(regions)
                    .filter(region -> region.equalsIgnoreCase(regionParam))
                    .map(region -> (region.equals("ALL")) ? "" : region)
                    .findFirst().orElseThrow();
            IpRangeFilter filter = new IpRangeFilter(client.getIpRanges());
            return filter.get(regionPrefix).collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
