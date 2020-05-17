package tbc.dma.todo.viewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.model.repository.TodoRepository;
import tbc.dma.todo.model.room.AppDatabase;

public class AddEditTaskViewModel extends AndroidViewModel {

    private TodoRepository  todoRepository;
    private LiveData<TodoEntity> tasks;

    public AddEditTaskViewModel(@NonNull Application application, int taskID) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        todoRepository = new TodoRepository(db);
        if(taskID != -1)
            tasks = todoRepository.getTaskById(taskID);
    }

    public LiveData<TodoEntity> getTask(){
        return tasks;
    }

    public void insertTask(TodoEntity task){
        todoRepository.insertTask(task);
        Toast.makeText(getApplication(), "Data Inserted Succesfully", Toast.LENGTH_LONG).show();
    }

    public void updateTask(TodoEntity task)
    {
        todoRepository.updateTask(task);
    }
}
