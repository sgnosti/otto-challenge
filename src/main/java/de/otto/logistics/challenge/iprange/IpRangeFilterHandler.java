package de.otto.logistics.challenge.iprange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IpRangeFilterHandler {

    @Autowired
    private IpRangesClient client;

    @Value("${ipranges.validRegions}")
    private String regions;


    public Mono<ServerResponse> filter(ServerRequest request) {

        String regionPrefix = request.queryParam("region").orElse("");
        if (!List.of(regions.split(",")).contains(regionPrefix)) {
            return ServerResponse.badRequest().contentType(MediaType.TEXT_PLAIN)
                    .bodyValue("Region not found: " + regionPrefix);
        } else {
            IpRangeFilter filter = new IpRangeFilter(client.getIpRanges());
            return ServerResponse.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(BodyInserters.fromPublisher(
                            filter.get(regionPrefix).collect(Collectors.joining(System.lineSeparator())
                            ), String.class));
        }
    }

}
