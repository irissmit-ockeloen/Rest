package fesma.nl.Payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar","bilbobaggins@gmail.com",20)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief", "frodobaggins@gmail.com", 20)));
            log.info("Preloading" + repository.save(new Employee("Deniz van Iersel","consultant", "denizvaniersel@gmail.com", 25)));
            log.info("Preloading" + repository.save(new Employee("James Baggins","housekeeper", "jamesbaggins@gmail.com", 35)));
            log.info("Preloading" + repository.save(new Employee("Eva Baggins","engineer", "evabaggins@gmail.com", 55)));


        };
    }
}
