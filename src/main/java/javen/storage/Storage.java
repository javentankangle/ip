package javen.storage;

import javen.task.Task;
import javen.tasklist.TaskList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves taskList to local hard drive
     * If folder ".data" is not created, creates folder.
     *
     * @param taskList User's list of task
     */
    public void saveTask(TaskList taskList) {
        Path folderPath = Paths.get(".data");

        try {
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
                System.out.println("Folder created: " + folderPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".data/duke.txt"))) {
            oos.writeObject(taskList.getTasks());
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }


    /**
     * Returns user's list of task loaded from hard drive
     * If file is empty, file is not available create an empty arraylist of task
     *
     * @return User's list of task
     */
    public ArrayList<Task> loadTask() throws IOException, ClassNotFoundException{

        File file = new File(filePath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Task>) ois.readObject();
        }
    }
}
