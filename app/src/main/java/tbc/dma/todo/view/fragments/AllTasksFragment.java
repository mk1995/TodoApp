package tbc.dma.todo.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.view.activity.AddEditTaskActivity;
import tbc.dma.todo.viewModel.AllTasksFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllTasksFragment extends Fragment implements RecyclerViewTaskListAdapter.OnItemClickListener {
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerViewTaskListAdapter mAdapter;
    private AllTasksFragmentViewModel allTasksFragmentViewModel;
    private Paint p = new Paint();

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
        mAdapter = new RecyclerViewTaskListAdapter(getActivity());

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
                int position = viewHolder.getAdapterPosition();
                List<TodoEntity> todoList = mAdapter.getTasks();
                // Here is where you'll implement swipe to delete
                if (swipeDir == ItemTouchHelper.RIGHT){
                    onItemClick(todoList.get(position));
                    Toast.makeText(getActivity(), "Swiped From Left to Right", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Swiped from Right to Left", Toast.LENGTH_LONG).show();
                    //allTasksFragmentViewModel.deleteTask(todoList.get(position));
                }
            }

            //to customize how RecyclerView's item respond to user interactions like edit/delete while swiping
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

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

        allTasksFragmentViewModel.getTasks().observe(requireActivity(), new Observer<List<TodoEntity>>() {
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
