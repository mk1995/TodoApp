package tbc.dma.todo.viewModel;

import android.app.Application;
import android.util.Log;

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
        Log.d("DBUGX", AddEditTaskViewModel.class.getSimpleName()+" Constructor()");
        AppDatabase db = AppDatabase.getInstance(application);
        todoRepository = new TodoRepository(db);
        this.TASKID = taskID;
    }

    public LiveData<TodoEntity> getTask(){
        Log.d("DBUGX", AddEditTaskViewModel.class.getSimpleName()+" getTask()");
        if(this.TASKID > 0){
            tasks = todoRepository.getTaskById(this.TASKID);
            Log.d("DBUGX", AddEditTaskViewModel.class.getSimpleName()+" gotTask()");
        }
        return tasks;
    }

    public void insertTask(TodoEntity task){
        Log.d("DBUGX", AddEditTaskViewModel.class.getSimpleName()+" insertTask()");
        todoRepository.insertTask(task);
    }

    public void updateTask(TodoEntity task)
    {
        Log.d("DBUGX", AddEditTaskViewModel.class.getSimpleName()+" update()");
        todoRepository.updateTask(task);
    }
}
