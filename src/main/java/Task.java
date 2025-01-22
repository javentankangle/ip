public class Task {

    private final String description;
    private Boolean isMark;

    public Task(String description) {
        this.description = description;
        this.isMark = false;
    }

    public void mark(){
        this.isMark = true;
    }

    public void unmark(){
        this.isMark = false;
    }

    public String toString() {
        if (this.isMark) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}
