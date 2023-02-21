package fesma.nl.Profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public ProfileFirestoreRepository profileFirestoreRepository() throws IOException {
        return new ProfileFirestoreRepository();
    }
}
