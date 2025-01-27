import java.io.Serial;
import java.io.Serializable;

public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String description;
    private Boolean isMark;

    public Task(String description) {
        this.description = description;
        this.isMark = false;
    }

    public void markTask(){
        this.isMark = true;
    }

    public void unmarkTask(){
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
