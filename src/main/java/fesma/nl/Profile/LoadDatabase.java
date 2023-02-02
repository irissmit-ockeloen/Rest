package fesma.nl.Profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProfileRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Profile("Java developer English", "Java developer","As a java developer Deniz was responsible for creating the Java backend for the CV app.")));
            log.info("Preloading " + repository.save(new Profile("Java developer Dutch", "Java developer","As a java developer James was responsible for creating the Java backend for the CV app.")));
            log.info("Preloading " + repository.save(new Profile("Java developer Dutch", "Java developer","As a java developer Iris was responsible for creating the Java backend for the CV app.")));
        };
    }
}
