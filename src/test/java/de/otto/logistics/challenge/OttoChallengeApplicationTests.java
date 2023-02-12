package de.otto.logistics.challenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OttoChallengeApplicationTests {

    @Value("${route.health}")
    private String healthRoute;

    @Value("${route.ipRangeFilter}")
    private String ipRangeFilter;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
    }

    @Test
    void testHealthCheck() {

        webTestClient.get()
                .uri(healthRoute)
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testIpRangeFilterWithoutQueryParameter() {

        webTestClient.get()
                .uri(ipRangeFilter)
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testIpRangeFilterWithValidQueryParameter() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(ipRangeFilter).queryParam("region", "US").build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

}
