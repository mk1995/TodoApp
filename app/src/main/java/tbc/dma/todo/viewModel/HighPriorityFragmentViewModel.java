package tbc.dma.todo.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.model.repository.TodoRepository;
import tbc.dma.todo.model.room.AppDatabase;

public class HighPriorityFragmentViewModel extends AndroidViewModel {
    //repo
    TodoRepository repository;
    //data
    private LiveData<List<TodoEntity>> tasks;
    private final int HIGH_PRIORITY_TASK = 1;

    public HighPriorityFragmentViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        repository = new TodoRepository(db);
        tasks = repository.getTasks();
    }
    public void getTaskByPriority(){
        repository.getTaskByPriority(HIGH_PRIORITY_TASK);
    }
}
