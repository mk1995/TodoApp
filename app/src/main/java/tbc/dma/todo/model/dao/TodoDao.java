package tbc.dma.todo.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tbc.dma.todo.model.entity.TodoEntity;

@Dao
public interface TodoDao {
    @Query("select * from tbl_todoapp")
    LiveData<List<TodoEntity>> loadAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TodoEntity task);

    @Update
    void update(TodoEntity task);

    @Delete
    void deleteTask(TodoEntity task);

    @Query("Select * from tbl_todoapp where taskID =:taskId")
    LiveData<TodoEntity> loadTaskById(int taskId);

    @Query("Select * from tbl_todoapp where taskPriority =:priority")
    LiveData<TodoEntity> loadTaskByPriority(int priority);

}
