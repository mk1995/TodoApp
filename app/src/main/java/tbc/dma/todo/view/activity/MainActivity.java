package tbc.dma.todo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;
import tbc.dma.todo.adapter.ViewPagerAdapter;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.view.fragments.AllTasksFragment;
import tbc.dma.todo.view.fragments.HighPriorityFragment;
import tbc.dma.todo.viewModel.AddEditTaskViewModel;
import tbc.dma.todo.viewModel.AddEditTaskViewModelFactory;
import tbc.dma.todo.viewModel.AllTasksFragmentViewModel;
import tbc.dma.todo.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabButton;
    private SearchView searchView;
    private RecyclerViewTaskListAdapter mAdapter;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_main);
        viewPager = findViewById(R.id.viewPager);
        fabButton = findViewById(R.id.fab);



        mAdapter = new RecyclerViewTaskListAdapter(getApplication());
        mainActivityViewModel = new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllTaskList().observe(this, new Observer<List<TodoEntity>>() {
            @Override
            public void onChanged(List<TodoEntity> todoEntities) {
                mAdapter.setTasks(todoEntities);
                mAdapter.notifyDataSetChanged();
                Log.d("TASKLIST", "TotalListCount: "+todoEntities.size());
            }
        });



        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        adapter.AddFragment(new AllTasksFragment(), "");
        adapter.AddFragment(new HighPriorityFragment(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.all_list);
        tabLayout.getTabAt(1).setIcon(R.drawable.important_tasks);

        /**
         * Set the Floating Action Button (FAB) to its corresponding View.
         * Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create explicit intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                if(addTaskIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(addTaskIntent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search", "Inside onQueryTextSubmit");
                mAdapter.getFilter().filter(query);
                Log.d("Search", "Filter onQueryTextSubmit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                Log.d("Search", "Filter onQueryTextChange");
                return false;
            }
        });
        return true;

    }
}
