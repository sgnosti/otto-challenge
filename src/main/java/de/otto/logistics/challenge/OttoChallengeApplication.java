package de.otto.logistics.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Otto Challenge", version = "1.0", description = "Simple IP range filter"))
public class OttoChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OttoChallengeApplication.class, args);
    }

}
