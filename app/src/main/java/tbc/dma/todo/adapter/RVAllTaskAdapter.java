package tbc.dma.todo.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tbc.dma.todo.R;
import tbc.dma.todo.model.entity.TodoEntity;

public class RVAllTaskAdapter extends RecyclerView.Adapter<RVAllTaskAdapter.ViewHolder> {

    //cached copy of data
    private List<TodoEntity> tasks;
    // Member variable to handle item clicks
    private OnItemClickListener mItemClickListener;

    public List<TodoEntity> getTasks(){
        return tasks;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<TodoEntity> taskEntries) {
        tasks = taskEntries;
        notifyDataSetChanged();
    }

    /*
   Helper method for selecting the correct priority circle color.
   P1 = red, P2 = blue, P3 = green
   */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = R.color.colorHighPriority;
                break;
            case 2:
                priorityColor = R.color.colorMediumPriority;
                break;
            case 3:
                priorityColor = R.color.colorLowPriority;
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @NonNull
    @Override
    public RVAllTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_tasks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAllTaskAdapter.ViewHolder holder, int position) {
        holder.bindViews(position);
    }

    @Override
    public int getItemCount() {
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskTitleView;
        private TextView dateView;
        private TextView priorityView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleView = (TextView) itemView.findViewById(R.id.taskRVTitle);
            dateView = itemView.findViewById(R.id.taskRVDate);
            priorityView = itemView.findViewById(R.id.taskRVPriority);

            //when task list item is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (mItemClickListener != null && pos != RecyclerView.NO_POSITION) {
                        mItemClickListener.onItemClick(tasks.get(pos));
                    }
                }
            });
        }

        public void bindViews(int pos)
        {
            TodoEntity taskEntry = tasks.get(pos);

            //taskTitleView.setText(taskEntry.getTaskTitle());

            /*int priority = taskEntry.getTaskPriority();
            priorityView.setText(taskEntry.getTaskPriority());
            GradientDrawable priorityCircle = (GradientDrawable) priorityView.getBackground();
            int priorityColor = getPriorityColor(priority);
            priorityCircle.setColor(priorityColor);*/

            //dateView.setText((CharSequence) taskEntry.getTaskDate());

            Log.d("RV", taskEntry.getTaskTitle());
            Log.d("RV", taskEntry.getTaskDescription());
            Log.d("RV", String.valueOf(taskEntry.getTaskPriority()));
            Log.d("RV", taskEntry.getTaskDate().toString());



        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(TodoEntity todoEntity);
    }

    public void setOnTaskClickListener(OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }
}
