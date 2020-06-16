package tbc.dma.todo.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@SuppressWarnings("unchecked")
public class AddEditTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;
    private final int taskId;

    public AddEditTaskViewModelFactory(Application application, int taskId){
        Log.d("DBUGX", AddEditTaskViewModelFactory.class.getSimpleName()+" Constructor()");
        this.application = application;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return  (T) new AddEditTaskViewModel(application, taskId);
    }
}
