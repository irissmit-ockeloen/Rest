package fesma.nl.Competence;

public class CompetenceNotFoundException extends RuntimeException {
    CompetenceNotFoundException(String id) {
        super("Could not find competency " + id);
    }
}

