package fesma.nl.Competence;

import java.util.Objects;

public class Competence {
    private String competence;
    private String description;
    private String id;

    public Competence(String competence, String description) {
        this.competence = competence;
        this.description = description;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String title) {
        this.competence = competence;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Competence competence))
            return false;
        return
                Objects.equals(this.id, competence.id) && Objects.equals(this.competence, competence.competence)
                        && Objects.equals(this.description, competence.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id, this.competence, this.description);
    }

    @Override
    public String toString() {
        return "competencies.Competence{" +
                "id=" + this.id + ", competence=" + this.competence + "description=" + this.description + '\'' + '}';
    }
}