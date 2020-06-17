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
    private final TodoRepository repository;

    public HighPriorityFragmentViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        repository = new TodoRepository(db);
    }
    public LiveData<List<TodoEntity>> getTasksList(){
        return repository.getTaskByPriority();
    }

    public void deleteTask(TodoEntity task){
        repository.deleteTask(task);
    }
}
