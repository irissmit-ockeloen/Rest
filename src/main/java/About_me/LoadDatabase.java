package About_me;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(About_meRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new About_me("Java developer English", "Java developer","As a java developer Deniz was responsible for creating the Java backend for the CV app.")));


        };
    }
}
