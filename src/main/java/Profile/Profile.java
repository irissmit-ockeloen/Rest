package Profile;

import jakarta.persistence.*;
import java.util.Objects;
import jakarta.persistence.Entity;

@Entity
class Profile {

    @Id
    @GeneratedValue()
    private Long id;
    private String title;
    private String function;
    private String description;

    Profile() {
    }

    Profile(String title, String function, String description) {
        this.title = title;
        this.function = function;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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
        if (!(o instanceof Profile))
            return false;
        Profile profile = (Profile) o;
        return
                Objects.equals(this.id, profile.id) && Objects.equals(this.title, profile.title)
                && Objects.equals(this.function, profile.function) && Objects.equals(this.description, profile.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id, this.title, this.function, this.description);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + this.id + ", title=" + this.title + '\'' + ", function=" + this.function + '\'' + "description=" + this.description + '\'' + '}';
    }
}

