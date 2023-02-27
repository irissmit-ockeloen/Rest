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

    @GetMapping("/competence")
    List<Competence> getAllCompetencies() {
        return repository.findAll();
    }
    @GetMapping("/competence/{id}")
    Competence getCompetencies(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new CompetenceNotFoundException(id));
    }

    @PostMapping("/competence")
    Competence postCompetencies(@RequestBody Competence newCompetence) {
        return repository.save(newCompetence);
    }

    @PutMapping("/competence/{id}")
    Competence putCompetencies(@RequestBody Competence newCompetence, @PathVariable String id) {
        Optional<Competence> result = repository.findById(id);
        if (result.isPresent()) {
            return result.map(competencies -> {
                        competencies.setCompetence(newCompetence.getCompetence());
                        competencies.setDescription(newCompetence.getDescription());

                        return repository.save(competencies);
                    })
                    .orElseGet(() -> {
                        newCompetence.setId(id);
                        return repository.save(newCompetence);
                    });
        }
        throw new CompetenceNotFoundException(id);
    }
    @DeleteMapping("/competence/{id}")
    void deleteCompetencies(@PathVariable String id){
        repository.deleteById(id);
    }
}

