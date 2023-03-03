package fesma.nl.Export;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
@Configuration
public class AppConfig {
    @Bean
    public ExportRepository ExportRepository() throws IOException {
        return new ExportRepository();
    }
}
