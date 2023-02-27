package fesma.nl.Competence;

public class CompetenceNotFoundException extends RuntimeException {
    CompetenceNotFoundException(Long id) {
        super("Could not find competency " + id);
    }
}

