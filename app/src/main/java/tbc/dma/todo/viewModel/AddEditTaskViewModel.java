package tbc.dma.todo.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.model.repository.TodoRepository;
import tbc.dma.todo.model.room.AppDatabase;

public class AddEditTaskViewModel extends AndroidViewModel {

    private final TodoRepository  todoRepository;
    private LiveData<TodoEntity> tasks;
    private final int TASKID;

    public AddEditTaskViewModel(@NonNull Application application, int taskID) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        todoRepository = new TodoRepository(db);
        this.TASKID = taskID;
    }

    public LiveData<TodoEntity> getTask(){
        if(this.TASKID > 0){
            tasks = todoRepository.getTaskById(this.TASKID);
        }
        return tasks;
    }

    public void insertTask(TodoEntity task){
        todoRepository.insertTask(task);
    }

    public void updateTask(TodoEntity task)
    {
        todoRepository.updateTask(task);
    }
}
