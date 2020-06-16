package tbc.dma.todo.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.model.repository.TodoRepository;
import tbc.dma.todo.model.room.AppDatabase;

public class AllTasksFragmentViewModel extends AndroidViewModel {
    //repo
    private final TodoRepository repository;

    public AllTasksFragmentViewModel(@NonNull Application application) {
        super(application);
        Log.d("DBUGX", AllTasksFragmentViewModel.class.getSimpleName()+" Constructor()");
        AppDatabase db = AppDatabase.getInstance(application);
        repository = new TodoRepository(db);
    }
    public LiveData<List<TodoEntity>> getTasks(){
        Log.d("DBUGX", AllTasksFragmentViewModel.class.getSimpleName()+" getTask()");
        return repository.getAllTasks();
    }

    public void deleteTask(TodoEntity task){
        Log.d("DBUGX", AllTasksFragmentViewModel.class.getSimpleName()+" delete()");
        repository.deleteTask(task);
    }

}
