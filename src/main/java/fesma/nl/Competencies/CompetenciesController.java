package fesma.nl.Competencies;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CompetenciesController {
    private CompetenciesRepository repository;

    CompetenciesController(CompetenciesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/competencies")
    List<Competencies> getAllCompetencies() {
        return repository.findAll();
    }
    @GetMapping("/profiles/{id}")
    Competencies getCompetencies(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CompetenciesNotFoundException(id));
    }

    @PostMapping("/competencies")
    Competencies postCompetencies(@RequestBody Competencies newCompetencies) {
        return repository.save(newCompetencies);
    }

    @PutMapping("/competencies/{id}")
    Competencies putCompetencies(@RequestBody Competencies newCompetencies, @PathVariable Long id) {
        Optional<Competencies> result = repository.findById(id);
        if (result.isPresent()) {
            return result.map(competencies -> {
                        competencies.setCompetence(newCompetencies.getCompetence());
                        competencies.setDescription(newCompetencies.getDescription());

                        return repository.save(competencies);
                    })
                    .orElseGet(() -> {
                        newCompetencies.setId(id);
                        return repository.save(newCompetencies);
                    });
        }
        throw new CompetenciesNotFoundException(id);
    }
    @DeleteMapping("/competencies/{id}")
    void deleteCompetencies(@PathVariable Long id){
        repository.deleteById(id);
    }
}

