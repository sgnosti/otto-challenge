package de.otto.logistics.challenge.iprange;

import reactor.core.publisher.Mono;

interface IpRangesClient {
    Mono<IpRanges> getIpRanges();
}
