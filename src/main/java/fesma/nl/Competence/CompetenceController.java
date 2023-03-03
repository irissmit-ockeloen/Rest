package fesma.nl.Competence;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CompetenceController {
    private CompetenceRepository repository;

    CompetenceController(CompetenceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/competencies")
    List<Competence> getCompetencies() {
        return repository.findAll();
    }
    @PostMapping("/competencies")
    Competence postCompetence(@RequestBody Competence newCompetence) {
        return repository.save(newCompetence);
    }
    @GetMapping("/competencies/{id}")
    Competence getCompetence(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new CompetenceNotFoundException(id));
    }
    @PutMapping("/competencies/{id}")
    Competence putCompetence(@RequestBody Competence newCompetence, @PathVariable String id) {
        Optional<Competence> result = repository.findById(id);
        if (result.isPresent()) {
            return result.map(competence -> {
                        competence.setCompetence(newCompetence.getCompetence());
                        competence.setDescription(newCompetence.getDescription());

                        return repository.save(competence);
                    })
                    .orElseGet(() -> {
                        newCompetence.setId(id);
                        return repository.save(newCompetence);
                    });
        }
        throw new CompetenceNotFoundException(id);
    }
    @DeleteMapping("/competencies/{id}")
    void deleteCompetence(@PathVariable String id){
        repository.deleteById(id);
    }
}


