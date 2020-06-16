package tbc.dma.todo.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import tbc.dma.todo.helperCLasses.FragmentHelper;
import tbc.dma.todo.helperCLasses.SearchViewHelper;
import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.view.activity.AddEditTaskActivity;
import tbc.dma.todo.viewModel.HighPriorityFragmentViewModel;

public class HighPriorityFragment extends Fragment implements RecyclerViewTaskListAdapter.OnItemClickListener {
    private RecyclerViewTaskListAdapter mAdapter;
    HighPriorityFragmentViewModel highPriorityFragmentViewModel;
    private Paint p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        setHasOptionsMenu(true);

        highPriorityFragmentViewModel = new ViewModelProvider(this).get(HighPriorityFragmentViewModel.class);

        // 1. get a reference to recyclerView
        // Member variables for the adapter and RecyclerView
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewAllTasksList);

        FragmentHelper fragmentHelper = new FragmentHelper(mRecyclerView, container);
        mAdapter = fragmentHelper.initRVHelper();


        /**
         *Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         *An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder, and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                List<TodoEntity> todoList = mAdapter.getTasks();
                // Here is where you'll implement swipe to delete
                if (swipeDir == ItemTouchHelper.RIGHT){
                    onItemClick(todoList.get(position));
                }else{
                    highPriorityFragmentViewModel.deleteTask(todoList.get(position));
                    Toast toast = Toast.makeText(getActivity(), " \" "+ todoList.get(position).getTaskTitle() +" \""+" deleted successfully.  ", Toast.LENGTH_LONG);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.show();
                }
            }

            //to customize how RecyclerView's item respond to user interactions like edit/delete while swiping
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    p = new Paint();
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX >= 0){
                        p.setColor(Color.parseColor("#FFFFFF"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.edit_icon);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#FFFFFF"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete_icon);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        }).attachToRecyclerView(mRecyclerView);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();

        highPriorityFragmentViewModel.getTasksList().observe(Objects.requireNonNull(requireActivity(), "LifecycleOwner is null"), new Observer<List<TodoEntity>>() {
            @Override
            public void onChanged(List<TodoEntity> taskEntries) {
                mAdapter.setTasks(taskEntries);
            }
        });

        mAdapter.setOnTaskClickListener(this);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        new SearchViewHelper(menu, inflater, mAdapter);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onItemClick(TodoEntity todoEntity) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.UPDATE_MODE, todoEntity.getTaskID());
        startActivity(intent);
    }
}
