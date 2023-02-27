package fesma.nl.Competence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CompetenceRepository repository() {
        return new CompetenceRepository();
    }
}
