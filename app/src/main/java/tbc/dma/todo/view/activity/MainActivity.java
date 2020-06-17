package tbc.dma.todo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.ViewPagerAdapter;
import tbc.dma.todo.view.fragments.AllTasksFragment;
import tbc.dma.todo.view.fragments.HighPriorityFragment;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(this);

        FloatingActionButton fabButton = findViewById(R.id.fab);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
                adapter.AddFragment(new AllTasksFragment(), "");
                adapter.AddFragment(new HighPriorityFragment(), "");
                viewPager.setAdapter(adapter);
                TabLayout tabLayout = findViewById(R.id.tab_main);
                tabLayout.setupWithViewPager(viewPager);
                Objects.requireNonNull(tabLayout.getTabAt(0), "Tab_Layout indexing error").setIcon(R.drawable.all_list);
                Objects.requireNonNull(tabLayout.getTabAt(1), "Tab_Layout indexing error").setIcon(R.drawable.important_tasks);
            }
        }).start();

       /* ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        adapter.AddFragment(new AllTasksFragment(), "");
        adapter.AddFragment(new HighPriorityFragment(), "");
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_main);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0), "Tab_Layout indexing error").setIcon(R.drawable.all_list);
        Objects.requireNonNull(tabLayout.getTabAt(1), "Tab_Layout indexing error").setIcon(R.drawable.important_tasks);*/




        /*
          Set the Floating Action Button (FAB) to its corresponding View.
          Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create explicit intent to start an AddTaskActivity
                /*Intent addTaskIntent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                        if(addTaskIntent.resolveActivity(getPackageManager()) != null){
                            startActivity(addTaskIntent);
                        }
*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent addTaskIntent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                        if(addTaskIntent.resolveActivity(getPackageManager()) != null){
                            startActivity(addTaskIntent);
                        }
                    }
                }).start();

            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void doSomethingOnLifeCycleState() {
        Toast.makeText(this, "Welcome to TODO. CurrentState: "+getLifecycle().getCurrentState(), Toast.LENGTH_LONG).show();
    }

}
