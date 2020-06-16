package tbc.dma.todo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
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
        Log.d("DBUGX", MainActivity.class.getSimpleName()+"Oncreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_main);
        ViewPager viewPager = findViewById(R.id.viewPager);
        FloatingActionButton fabButton = findViewById(R.id.fab);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        adapter.AddFragment(new AllTasksFragment(), "");
        adapter.AddFragment(new HighPriorityFragment(), "");

        viewPager.setAdapter(adapter);
        Log.d("DBUGX", MainActivity.class.getSimpleName()+"ViewPager added");
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0), "Tab_Layout indexing error").setIcon(R.drawable.all_list);
        Objects.requireNonNull(tabLayout.getTabAt(1), "Tab_Layout indexing error").setIcon(R.drawable.important_tasks);
        Log.d("DBUGX", MainActivity.class.getSimpleName()+"TabLayout added");

        /*
          Set the Floating Action Button (FAB) to its corresponding View.
          Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        Log.d("DBUGX", MainActivity.class.getSimpleName()+"fabButton()");
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create explicit intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                if(addTaskIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(addTaskIntent);
                }
                Log.d("DBUGX", MainActivity.class.getSimpleName()+"intent to AddEditTaskActivit");
            }
        });
        Log.d("DBUGX", MainActivity.class.getSimpleName()+"fabButton added()");
    }
}
