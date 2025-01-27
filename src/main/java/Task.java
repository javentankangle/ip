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


    /**
     * Put mark task status to be true
     */
    public void markTask(){
        this.isMark = true;
    }


    /**
     * Put mark task status to be false
     */
    public void unmarkTask(){
        this.isMark = false;
    }

    @Override
    public String toString() {
        if (this.isMark) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}
