package tbc.dma.todo.model.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import tbc.dma.todo.model.dao.TodoDao;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.model.room.AppDatabase;

public class TodoRepository {
    TodoDao dao;

    public TodoRepository(AppDatabase appDatabase){
        dao = appDatabase.todoDao();
    }

    public LiveData<List<TodoEntity>> getTasks(){
        return dao.loadAllTasks();
    }

    public LiveData<TodoEntity> getTaskById(int taskId){
        return dao.loadTaskById(taskId);
    }

    public LiveData<TodoEntity> getTaskByPriority(int Priority){
        return dao.loadTaskByPriority(Priority);
    }

    public void updateTask(final TodoEntity task){
        AppDatabase.getDatabaseWriteExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.update(task);
            }
        });
    }

    public void deleteTask(final TodoEntity task){
        AppDatabase.getDatabaseWriteExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteTask(task);
            }
        });
    }

    public  void  insertTask(final TodoEntity task){
        AppDatabase.getDatabaseWriteExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.insertTask(task);
            }
        });
    }
}
