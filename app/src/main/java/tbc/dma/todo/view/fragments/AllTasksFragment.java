package tbc.dma.todo.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;
import tbc.dma.todo.helperCLasses.FragmentHelper;
import tbc.dma.todo.helperCLasses.SearchViewHelper;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.view.activity.AddEditTaskActivity;
import tbc.dma.todo.viewModel.AllTasksFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllTasksFragment extends Fragment implements RecyclerViewTaskListAdapter.OnItemClickListener  {
    // Member variables for the adapter and RecyclerView
    private RecyclerViewTaskListAdapter mAdapter;
    private AllTasksFragmentViewModel allTasksFragmentViewModel;
    private final Paint p = new Paint();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);

        setHasOptionsMenu(true);
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewAllTasksList);
        allTasksFragmentViewModel = new ViewModelProvider(this).get(AllTasksFragmentViewModel.class);

        FragmentHelper fragmentHelper = new FragmentHelper(mRecyclerView, container);
        mAdapter = fragmentHelper.initRVHelper();

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
          An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * Called when a user swipes left or right on a ViewHolder
             * Row is swiped from recycler view
             * */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                final List<TodoEntity> todoList = mAdapter.getTasks();
                /*swipe to edit the task list*/
                if (swipeDir == ItemTouchHelper.RIGHT){
                    onItemClick(todoList.get(position));

                }else{
                    /*
                    * swipe to delete
                    * remove it from adapter
                    * */
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    allTasksFragmentViewModel.deleteTask(todoList.get(position));
                                    Toast toast = Toast.makeText(getActivity(), " \" "+ todoList.get(position).getTaskTitle() +" \""+" deleted successfully.  ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.WHITE);
                                    toast.show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast toastNO = Toast.makeText(getActivity(), " Delete operation canceled.", Toast.LENGTH_LONG);
                                    toastNO.getView().setBackgroundColor(Color.WHITE);
                                    toastNO.show();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                    ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    mAdapter.notifyDataSetChanged();

                }
            }

            /**
             * the foreground view is changed while user is swiping the view.
             *  to customize how RecyclerView's item respond to user interactions like edit/delete while swiping
             */
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX >= 0){
                        p.setColor(Color.parseColor("#FFFFFF"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background,p);

                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.edit_icon);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft() + 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#cdcdcd"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);

                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete_icon);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        // attaching the touch helper to recycler view
        }).attachToRecyclerView(mRecyclerView);

        // Observing the datachanges
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        new SearchViewHelper(menu, inflater, mAdapter, getContext());
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
