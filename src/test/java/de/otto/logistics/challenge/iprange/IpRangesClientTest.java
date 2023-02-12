package de.otto.logistics.challenge.iprange;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.Duration;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class IpRangesClientTest {
    @Value("${ipranges.sourceUrl}")
    private String url;

    @Test
    void testReadIpRangesFromSource() {
        IpRangesClient source = new IpRangesClient(url);
        StepVerifier.create(source.getIpRanges())
                .expectNextMatches(this::validate)
                .expectComplete()
                .verify(Duration.ofSeconds(10));
    }

    private boolean validate(IpRanges ipRanges) {
        return ipRanges != null && !ipRanges.getAllPrefixes().isEmpty();
    }

}