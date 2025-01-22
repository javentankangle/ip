public class Task {

    private String description;
    private Boolean isCompleted;

    public Task(String description) {
        this.description = description;
    }

    public String TaskIcon() {
        if (this.isCompleted) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    public void complete(){
        this.isCompleted = true;
    }
}
