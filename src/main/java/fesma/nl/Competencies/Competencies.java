package fesma.nl.Competencies;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Competencies {
    private String competence;
    private String description;
    private Long id;

    public Competencies(String competence, String description) {
    }

    public Competencies(Long id, String competence, String description) {
        this.id = id;
        this.competence = competence;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
        if (!(o instanceof Competencies competencies))
            return false;
        return
                Objects.equals(this.id, competencies.id) && Objects.equals(this.competence, competencies.competence)
                        && Objects.equals(this.description, competencies.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id, this.competence, this.description);
    }

    @Override
    public String toString() {
        return "competencies.Competencies{" +
                "id=" + this.id + ", competence=" + this.competence + "description=" + this.description + '\'' + '}';
    }

    public List<Competencies> findAll() {
        return null;
    }

    public Competencies save(Competencies newCompetencies) {
        return newCompetencies;
    }

    public Optional<Competencies> findById(Long id) {
        return Optional.empty();
    }

    public void deleteById(Long id) {
    }
}