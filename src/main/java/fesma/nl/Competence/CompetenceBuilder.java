package fesma.nl.Competence;

public class CompetenceBuilder {
    private String competence;
    private String description;

    public CompetenceBuilder setCompetence(String competence) {
        this.competence = competence;
        return this;
    }

    public CompetenceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Competence createCompetencies() {
        return new Competence(competence, description);
    }
}

