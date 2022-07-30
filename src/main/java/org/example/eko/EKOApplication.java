package org.example.eko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entrypoint of the Spring Boot org.example.eko.Application starting the server
 */

//@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class EKOApplication {

    public static void main(String[] args) {
        SpringApplication.run(EKOApplication.class, args);
    }
}
