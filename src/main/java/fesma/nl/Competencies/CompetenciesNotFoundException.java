package fesma.nl.Competencies;

public class CompetenciesNotFoundException extends RuntimeException {
    CompetenciesNotFoundException(Long id) {
        super("Could not find competency " + id);
    }
}

