package tbc.dma.todo.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tbc.dma.todo.R;
import tbc.dma.todo.model.entity.TodoEntity;

public class RecyclerViewTaskListAdapter extends RecyclerView.Adapter<RecyclerViewTaskListAdapter.ViewHolder> {

    //cached copy of data
    private List<TodoEntity> tasks;
    // Member variable to handle item clicks
    private OnItemClickListener mItemClickListener;
    private Context mContext;

    public RecyclerViewTaskListAdapter(Context cntx) {
        mContext = cntx;
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

    /*
   Helper method for selecting the correct priority circle color.
   P1 = red, P2 = blue, P3 = green
   */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.colorHighPriority);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.colorMediumPriority);
                break;
            case 3:
                priorityColor =ContextCompat.getColor(mContext, R.color.colorLowPriority);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @NonNull
    @Override
    public RecyclerViewTaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewTaskListAdapter.ViewHolder holder, int position) {
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

            taskTitleView.setText(taskEntry.getTaskTitle());

            int priority = taskEntry.getTaskPriority();
            int priorityColor = getPriorityColor(priority);

            GradientDrawable priorityCircle = (GradientDrawable) priorityView.getBackground();
            priorityCircle.setColor(priorityColor);

        /*    Log.d("TAG", String.valueOf(priorityColor));
            Log.d("TAG", String.valueOf(priority));*/

            dateView.setText(taskEntry.getTaskDate().toString());

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
