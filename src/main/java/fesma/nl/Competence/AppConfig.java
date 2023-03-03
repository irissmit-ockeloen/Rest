package fesma.nl.Competence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public CompetenceRepository competenceRepository() throws IOException {
        return new CompetenceRepository();
    }
}
