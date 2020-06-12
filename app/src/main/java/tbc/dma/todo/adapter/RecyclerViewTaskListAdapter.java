package tbc.dma.todo.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tbc.dma.todo.R;
import tbc.dma.todo.model.entity.TodoEntity;

public class RecyclerViewTaskListAdapter extends RecyclerView.Adapter<RecyclerViewTaskListAdapter.ViewHolder> implements Filterable {

    //cached copy of data
    private List<TodoEntity> tasks;
    private List<TodoEntity> filteredTasks;

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
        filteredTasks = taskEntries;
        notifyDataSetChanged();
    }

    /**
   *Helper method for selecting the correct priority circle color. P1 = red, P2 = blue, P3 = green
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

     public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskTitleView;
        private TextView dateView;
        private TextView priorityView;
        private EditText searchEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleView = itemView.findViewById(R.id.taskRVTitle);
            dateView = itemView.findViewById(R.id.taskRVDate);
            priorityView = itemView.findViewById(R.id.taskRVPriority);
            searchEditText = itemView.findViewById(R.id.search_input);

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

            dateView.setText(taskEntry.getTaskDate().toString());

        }
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

    public interface OnItemClickListener
    {
        void onItemClick(TodoEntity todoEntity);
    }

    public void setOnTaskClickListener(OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<TodoEntity> filteredTaskList = new ArrayList<>();
                String searchString = constraint.toString().toLowerCase().trim();
                if(searchString.isEmpty() || searchString.length() == 0){
                    filteredTasks = tasks;
                }else{
                    //debug::tasks null
                    for (TodoEntity t : tasks){
                        Log.d("Search", "Task Title: "+t.getTaskTitle());
                        if (t.getTaskTitle().toLowerCase().contains(searchString)

                                || t.getTaskDescription().toLowerCase().contains(searchString)) {
                            filteredTaskList.add(t);
                        }
                    }
                    filteredTasks = filteredTaskList;
                    Log.d("Search", "FilteredTaskListCOunt: "+String.valueOf(filteredTaskList.size()));
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredTasks;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //
                filteredTasks = (List<TodoEntity>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
