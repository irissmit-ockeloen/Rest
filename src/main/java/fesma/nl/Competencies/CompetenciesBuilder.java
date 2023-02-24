package fesma.nl.Competencies;

public class CompetenciesBuilder {
    private String competence;
    private String description;

    public CompetenciesBuilder setCompetence(String competence) {
        this.competence = competence;
        return this;
    }

    public CompetenciesBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Competencies createCompetencies() {
        return new Competencies(competence, description);
    }
}

