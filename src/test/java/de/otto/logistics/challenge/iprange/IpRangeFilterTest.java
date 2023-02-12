package de.otto.logistics.challenge.iprange;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

class IpRangeFilterTest {

    @Test
    void filterEmptyIpRanges() {
        IpRangeFilter filter = new IpRangeFilter(Mono.empty());
        StepVerifier.create(filter.get(""))
                .expectComplete()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    void filterIpRanges() {
        IpRanges.Prefix prefix1 = IpRanges.Prefix.builder().ipPrefix("34.223.24.0/22").region("us-west-2").build();
        IpRanges.Prefix prefix2 = IpRanges.Prefix.builder().ipPrefix("2a05:d035:5000::/40").region("il-central-1").build();
        IpRanges.Prefix prefix3 = IpRanges.Prefix.builder().ipPrefix("2620:108:7000::/44").region("us-west-2").build();

        IpRanges ipRanges = IpRanges.builder()
                .prefixes(List.of(prefix1))
                .ipv6Prefixes(List.of(prefix2, prefix3))
                .build();

        IpRangeFilter filter = new IpRangeFilter(Mono.just(ipRanges));
        StepVerifier.create(filter.get("US"))
                .expectNext(prefix1.getIpPrefix())
                .expectNext(prefix3.getIpPrefix())
                .expectComplete()
                .verify(Duration.ofSeconds(10));
    }

}
