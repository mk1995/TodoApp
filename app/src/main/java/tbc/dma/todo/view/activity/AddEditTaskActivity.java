package tbc.dma.todo.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import tbc.dma.todo.R;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.viewModel.AddEditTaskViewModel;
import tbc.dma.todo.viewModel.AddEditTaskViewModelFactory;


/**
 *
 */
public class AddEditTaskActivity extends AppCompatActivity {

    //TAG NAME
    public static  final String UPDATE_MODE = null;

    // default task id to be used when not in update mode
    private static int DEFAULT_TASK_ID = 0;

    //ViewModel
    AddEditTaskViewModel addEditTaskViewModel;

    /*Member variable views*/
    EditText taskTitle;
    EditText taskDescription;
    Spinner taskPriority;
    TextView taskDate;
    Button banCancel;
    Button btnOK;
    TextView textViewSetColor;

    /**
     * initViews is called from onCreate to initialise the member variable views
     */
    private void initViews() {
        taskTitle = findViewById(R.id.editTextAddEditTaskTitle);
        taskDescription = findViewById(R.id.editTextAddEditTaskDescription);
        taskPriority = findViewById(R.id.spinnerAddEditTaskPriority);
        taskDate = findViewById(R.id.textViewAddEditTaskDate);
        banCancel = findViewById(R.id.buttonAddEditTaskCancle);
        btnOK = findViewById(R.id.buttonAddEditTaskOK);
        textViewSetColor = findViewById(R.id.txtViewSetPriorityColor);
    }

    /*textview validation*/
    final private TextWatcher taskInputValidation = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
                 btnOK.setEnabled(!taskTitle.getText().toString().trim().isEmpty() && !taskDescription.getText().toString().trim().isEmpty() && !taskDate.getText().toString().trim().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int[] getPriorityFromViews(int priorityValue) {
        int[] returnValues = new int[2];
        //GradientDrawable drawable = (GradientDrawable) imgViewSetColor.getBackground();
        int bgColor = -1, indexPosition = 0;
        switch (priorityValue) {
            case 1:
                indexPosition = 0;
                bgColor = ContextCompat.getColor(this, R.color.colorHighPriority);
                break;
            case 2:
                indexPosition = 1;
                bgColor = ContextCompat.getColor(this, R.color.colorMediumPriority);
                break;
            case 3:
                indexPosition = 2;
                bgColor = ContextCompat.getColor(this, R.color.colorLowPriority);
        }
        returnValues[0] = indexPosition;
        returnValues[1] = bgColor;
        return returnValues;
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private void populateUI(TodoEntity task) {
        if(task == null){
            return;
        }
        int[] array =  getPriorityFromViews(task.getTaskPriority());
        int indexPosition = array[0];
        int color = array[1];

        taskTitle.setText(task.getTaskTitle());
        taskDescription.setText(task.getTaskDescription());
        taskPriority.setSelection(indexPosition);

        setBackgroundPriorityColor(color);

        taskDate.setBackground(null);
        taskDate.setText(task.getTaskDate().toString());
    }

    /**
     * Changing background color based on priority
     * @param color
     * int value
     */
    private void setBackgroundPriorityColor(int color){
        GradientDrawable priorityCircle = (GradientDrawable) textViewSetColor.getBackground();
        priorityCircle.setColor(color);
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        try {

            String title = taskTitle.getText().toString();
            String description = taskDescription.getText().toString();
            int index = taskPriority.getSelectedItemPosition();
            String date = taskDate.getText().toString();
            Date sqlDate = new Date(Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)).getTime());
            TodoEntity todo = new TodoEntity(title, description, (index+1), sqlDate);

            if(DEFAULT_TASK_ID < 1){
                AddEditTaskViewModelFactory factory = new AddEditTaskViewModelFactory(getApplication(), 0);
                addEditTaskViewModel = new ViewModelProvider(this, factory).get(AddEditTaskViewModel.class);
                addEditTaskViewModel.insertTask(todo);
                Toast toast = Toast.makeText(getApplicationContext(), " \" "+ title +" \""+" added successfully.  "+todo.getTaskID(), Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.WHITE);
                toast.show();
            }
            else{

                todo.setTaskID(DEFAULT_TASK_ID);
                addEditTaskViewModel.updateTask(todo);
                Toast toast = Toast.makeText(getApplicationContext(), " \" "+ title +" \""+" updated successfully.  ", Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.WHITE);
                toast.show();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, "ExceptionHandling: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        /*Initialise the views*/
        initViews();

        final Intent intent = getIntent();

        if ((savedInstanceState != null) || (intent.hasExtra(UPDATE_MODE))) {
            if(savedInstanceState != null){
                DEFAULT_TASK_ID = savedInstanceState.getInt(UPDATE_MODE);
            }
            if(intent.hasExtra(UPDATE_MODE)){
                DEFAULT_TASK_ID = intent.getIntExtra(UPDATE_MODE, 0);
            }
            /*Populating views*/
            btnOK.setText(R.string.update);
            AddEditTaskViewModelFactory factory = new AddEditTaskViewModelFactory(getApplication(), DEFAULT_TASK_ID);
            addEditTaskViewModel = new ViewModelProvider(AddEditTaskActivity.this, factory).get(AddEditTaskViewModel.class);
            final LiveData<TodoEntity> taskList = addEditTaskViewModel.getTask();
            taskList.observe(this, new Observer<TodoEntity>() {

                @Override
                public void onChanged(TodoEntity taskEntry) {
                    taskList.removeObserver(this);
                    populateUI(taskEntry);
                }
            });

        }
        else{
            DEFAULT_TASK_ID = -1;
        }

        /*Task Priority Drop Down list*/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        taskPriority.setAdapter(adapter);
        taskPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               int[] arrayValues =  getPriorityFromViews(taskPriority.getSelectedItemPosition() + 1);
                setBackgroundPriorityColor(arrayValues[1]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Android DatePicker Dialog when textview is clicked*/
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                taskDate.setBackground(null);
                                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
                                taskDate.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        taskTitle.addTextChangedListener(taskInputValidation);
        taskDescription.addTextChangedListener(taskInputValidation);
        taskDate.addTextChangedListener(taskInputValidation);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });


        banCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                if(intent1.resolveActivity(getPackageManager()) != null)
                    startActivity(intent1) ;
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // can do things when screen size and orientation changes.
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "Screen rotated: Portrait", Toast.LENGTH_SHORT).show();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "Screen rotated: Landscape", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(UPDATE_MODE, DEFAULT_TASK_ID);
        super.onSaveInstanceState(outState);
    }
}
