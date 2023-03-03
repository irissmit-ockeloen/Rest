package fesma.nl.Export;

public class Export {
    public class CompentenceIds {
        private String competence1;
        private String competence2;
        private String competence3;
    }

    private String profile;
    private CompentenceIds competencies;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public CompentenceIds getCompetencies() {
        return competencies;
    }

    public void setCompetencies(CompentenceIds competencies) {
        this.competencies = competencies;
    }
}