package tbc.dma.todo.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RVAllTaskAdapter;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.view.activity.AddEditTaskActivity;
import tbc.dma.todo.viewModel.AllTasksFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllTasksFragment extends Fragment implements RVAllTaskAdapter.OnItemClickListener {
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private RVAllTaskAdapter mAdapter;
    AllTasksFragmentViewModel allTasksFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        allTasksFragmentViewModel = new ViewModelProvider(this).get(AllTasksFragmentViewModel.class);

        // 1. get a reference to recyclerView
        mRecyclerView = view.findViewById(R.id.recyclerViewAllTasksList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // 2. set layoutManger
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        mAdapter = new RVAllTaskAdapter();

        // 4. set adapter
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

               /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                int position = viewHolder.getAdapterPosition();
                List<TodoEntity> todoList = mAdapter.getTasks();
                allTasksFragmentViewModel.deleteTask(todoList.get(position));
            }
        }).attachToRecyclerView(mRecyclerView);

        allTasksFragmentViewModel.getTasks().observe(getActivity(), new Observer<List<TodoEntity>>() {
            @Override
            public void onChanged(List<TodoEntity> taskEntries) {
                mAdapter.setTasks(taskEntries);
            }
        });

        mAdapter.setOnTaskClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(TodoEntity todoEntity) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, todoEntity.getTaskID());
        startActivity(intent);
    }
}
