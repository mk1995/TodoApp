package tbc.dma.todo.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "tbl_todoapp")
public class TodoEntity {

    @PrimaryKey(autoGenerate = true)
    private int taskID;

    private String taskTitle;
    private String taskDescription;
    private int taskPriority;
    private Date taskDate;

    @Ignore
    public TodoEntity(String taskTitle, String taskDescription, int taskPriority, Date taskDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.taskDate = taskDate;
    }

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

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }
}
