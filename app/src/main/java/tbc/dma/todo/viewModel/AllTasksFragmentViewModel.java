package tbc.dma.todo.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.model.repository.TodoRepository;
import tbc.dma.todo.model.room.AppDatabase;

public class AllTasksFragmentViewModel extends AndroidViewModel {
    //repo
    private TodoRepository repository;
    //data
    private LiveData<List<TodoEntity>> tasks;
    private List<TodoEntity> taskList;


    public AllTasksFragmentViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        repository = new TodoRepository(db);
        tasks = repository.getAllTasks();
        taskList = repository.getAllTasksList();
    }
    public LiveData<List<TodoEntity>> getTasks(){
        return tasks;
    }

    public List<TodoEntity> getAllTasksList(){
        return taskList;
    }

    public void deleteTask(TodoEntity task){
        repository.deleteTask(task);
    }

}
