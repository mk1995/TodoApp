package tbc.dma.todo.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;


@Entity (tableName = "tbl_todoapp")
public class TodoEntity {

    @PrimaryKey(autoGenerate = true)
    private int taskID;

    private final String taskTitle;
    private final String taskDescription;
    private final int taskPriority;
    private final Date taskDate;

    public TodoEntity(String taskTitle, String taskDescription, int taskPriority, Date taskDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.taskDate = taskDate;
    }
    @Ignore
    public TodoEntity(int taskID, String taskTitle, String taskDescription, int taskPriority, Date taskDate) {
        this.taskID = taskID;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.taskDate = taskDate;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public Date getTaskDate() {
        return taskDate;
    }
}
