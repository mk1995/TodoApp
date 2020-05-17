package tbc.dma.todo.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public interface OnItemClickListener
    {
        void onItemClick(TodoEntity todoEntity);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }
    @NonNull
    @Override
    public RVAllTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAllTaskAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }
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

    @Override
    public int getItemCount() {
        if(tasks != null){
            return tasks.size();
        }else{
            return 0;
        }
    }
    /*
       Helper method for selecting the correct priority circle color.
       P1 = red, P2 = orange, P3 = yellow
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitleView;
        TextView dateView;
        TextView priorityView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleView = itemView.findViewById(R.id.textViewTaskTitle);
            dateView = itemView.findViewById(R.id.textViewDate);
            priorityView = itemView.findViewById(R.id.textViewPriority);

            //when task list is clicked
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
        public void bind(int pos)
        {
            TodoEntity taskEntry = tasks.get(pos);
            int priority = taskEntry.getTaskPriority();
            // Programmatically set the text and color for the priority TextView
            priorityView.setText(String.valueOf(tasks.get(pos).getTaskPriority()));

            GradientDrawable priorityCircle = (GradientDrawable) priorityView.getBackground();
            // Get the appropriate background color based on the priority
            int priorityColor = getPriorityColor(priority);
            priorityCircle.setColor(priorityColor);

            taskTitleView.setText(tasks.get(pos).getTaskDescription());
            dateView.setText((CharSequence) tasks.get(pos).getTaskDate());
        }
    }
}
