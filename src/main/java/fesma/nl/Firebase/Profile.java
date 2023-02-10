package fesma.nl.Firebase;

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

    public String toString() {
        return "Profile{" +
                "title=" + this.title + '\'' + ", function=" + this.function + '\'' + "description=" + this.description + '\'' + '}';
    }
}

