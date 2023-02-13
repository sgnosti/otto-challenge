package de.otto.logistics.challenge.iprange;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

class IpRangeFilterHandlerTest {

    @Test
    void filterEmptyIpRanges() {
        IpRangesClient client = new IpRangesClient() {
            @Override
            public Mono<IpRanges> getIpRanges() {
                return Mono.empty();
            }
        };
        IpRangeFilterHandler handler = new IpRangeFilterHandler(client, new String[]{"US"});
        ServerRequest serverRequest = MockServerRequest.builder().queryParam("region", "US").body(Mono.empty());

        StepVerifier.create(handler.handle(serverRequest))
                .expectNext("")
                .expectComplete()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    void filterIpRanges() {
        IpRanges.Prefix prefix1 = IpRanges.Prefix.builder().ipPrefix("34.223.24.0/22").region("us-west-2").build();
        IpRanges.Prefix prefix2 = IpRanges.Prefix.builder().ipPrefix("2a05:d035:5000::/40").region("il-central-1").build();
        IpRanges.Prefix prefix3 = IpRanges.Prefix.builder().ipPrefix("2620:108:7000::/44").region("us-west-2").build();

        IpRangesClient client = new IpRangesClient() {
            @Override
            public Mono<IpRanges> getIpRanges() {

                IpRanges ipRanges = IpRanges.builder()
                        .prefixes(List.of(prefix1))
                        .ipv6Prefixes(List.of(prefix2, prefix3))
                        .build();
                return Mono.just(ipRanges);
            }
        };


        IpRangeFilterHandler handler = new IpRangeFilterHandler(client, new String[]{"US"});
        ServerRequest serverRequest = MockServerRequest.builder().queryParam("region", "US").body(Mono.empty());

        StepVerifier.create(handler.handle(serverRequest))
                .expectNext(prefix1.getIpPrefix().concat(System.lineSeparator()).concat(prefix3.getIpPrefix()))
                .expectComplete()
                .verify(Duration.ofSeconds(10));
    }
}
