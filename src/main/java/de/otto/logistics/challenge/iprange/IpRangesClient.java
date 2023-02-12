package de.otto.logistics.challenge.iprange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class IpRangesClient {

    private final WebClient webClient;

    public IpRangesClient(@Value("${ipranges.sourceUrl}") String url) {
        // random numbers to avoid 256K limit, consider reading somehow sequentially
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)).build();

        this.webClient = WebClient.builder()
                .baseUrl(url)
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    public Mono<IpRanges> getIpRanges() {
        return webClient.get()
                .retrieve()
                .bodyToMono(IpRanges.class);
    }
}




