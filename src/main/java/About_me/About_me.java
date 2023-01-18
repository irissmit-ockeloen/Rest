package About_me;

import jakarta.persistence.*;
import java.util.Objects;
import jakarta.persistence.Entity;

@Entity
public class About_me {
    @Id
    @GeneratedValue()
    private Long id;
    private String title;
    private String function;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    About_me() {
    }
    About_me (String title, String function, String description) {

        this.title = title;
        this.function = function;
        this.description = description;
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
        if (!(o instanceof About_me))
            return false;
        About_me employee = (About_me) o;
        return
                Objects.equals(this.id, employee.id) && Objects.equals(this.title, employee.title)
                && Objects.equals(this.function, employee.function) && Objects.equals(this.description, employee.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                //this.id,
                this.title, this.function, this.description);
    }

    @Override
    public String toString() {
        return "Employee{" +
                //"id=" + this.id +
                ", title='" + this.title + '\'' + ", function='" + this.function + '\'' + "description=" + this.description + '\'' + '}';
    }
}

