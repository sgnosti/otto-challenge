package de.otto.logistics.challenge.iprange;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class IpRangeFilter {

    private final Mono<IpRanges> ipRanges;

    Flux<String> get(String regionPrefix) {
        return ipRanges
                .flatMapIterable(IpRanges::getAllPrefixes)
                .filter(prefix -> prefix.getRegion().startsWith(regionPrefix.toLowerCase()))
                .map(IpRanges.Prefix::getIpPrefix);
    }

}
