package fesma.nl.Profile;

import java.util.Objects;

class Profile {

    private String id;
    private String title;
    private String function;
    private String description;

    Profile(String title, String function, String description) {
        this.title = title;
        this.function = function;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (this == o) return true;
        if (!(o instanceof Profile profile)) return false;
        return Objects.equals(this.id, profile.id) && Objects.equals(this.title, profile.title)
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

