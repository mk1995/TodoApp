package tbc.dma.todo.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tbc.dma.todo.R;
import tbc.dma.todo.model.entity.TodoEntity;
import tbc.dma.todo.viewModel.AddEditTaskViewModel;
import tbc.dma.todo.viewModel.AddEditTaskViewModelFactory;

public class AddEditTaskActivity extends AppCompatActivity {

    /*Member variable views*/
    EditText taskTitle;
    EditText taskDescription;
    Spinner taskPriority;
    String taskPriorityValue;
    TextView taskDate;
    Button btnCancle;
    Button btnOK;

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;

    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    //ViewModel
    AddEditTaskViewModel addEditTaskViewModel;

    /**
     * initViews is called from onCreate to initialise the member variable views
     */
    private void initViews() {
        taskTitle = findViewById(R.id.editTextAddEditTaskTitle);
        taskDescription = findViewById(R.id.editTextAddEditTaskDescription);
        taskPriority = findViewById(R.id.spinnerAddEditTaskPriority);
        taskDate = findViewById(R.id.textViewAddEditTaskDate);
        btnCancle = findViewById(R.id.buttonAddEditTaskCancle);
        btnOK = findViewById(R.id.buttonAddEditTaskOK);
    }

    /*textview validation*/
    private TextWatcher taskInputValidation = new TextWatcher(){

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
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        try {

            String title = taskTitle.getText().toString();
            String description = taskDescription.getText().toString();
            int priority = getPriorityFromViews();
            String date = taskDate.getText().toString();
            Date sqlDate = new Date(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).getTime());
            TodoEntity todo = new TodoEntity(title, description, priority, sqlDate);
            if(mTaskId == DEFAULT_TASK_ID){
                addEditTaskViewModel.insertTask(todo);
                Toast toast = Toast.makeText(getApplicationContext(), " \" "+ title +" \""+" added successfully.  ", Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.DKGRAY);
                toast.show();
            }
            else{
                todo.setTaskID(mTaskId);
                addEditTaskViewModel.updateTask(todo);
                Toast toast = Toast.makeText(getApplicationContext(), " \" "+ title +" \""+" updated successfully.  ", Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.DKGRAY);
                toast.show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
           Toast.makeText(this, "ExceptionHandling: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int getPriorityFromViews() {
        int priority = 1;
        switch (taskPriorityValue) {
            case "High":
                priority = PRIORITY_HIGH;
                break;
            case "Medium":
                priority = PRIORITY_MEDIUM;
                break;
            case "Low":
                priority = PRIORITY_LOW;
        }
        return priority;
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
        int indexPositionValue = task.getTaskPriority();
        int indexPosition = -1;
        switch (indexPositionValue){
            case 1:
            indexPosition =0;
            break;
            case 2:
                indexPosition = 1;
                break;
            case 3:
                indexPosition = 2;
                break;
            default:
                indexPosition = 0;
        }
        taskTitle.setText(task.getTaskTitle());
        taskDescription.setText(task.getTaskDescription());
        taskPriority.setSelection(indexPosition);
        taskDate.setBackground(null);
        taskDate.setText(task.getTaskDate().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        /*Initialise the views*/
        initViews();

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
                taskPriorityValue = taskPriority.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), taskPriorityValue, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Android DatePicker Dialog when textview is clicked*/
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

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

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            btnOK.setText("Update");
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI

                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                AddEditTaskViewModelFactory factory = new AddEditTaskViewModelFactory(getApplication(), mTaskId);
                addEditTaskViewModel = new ViewModelProvider(this, factory).get(AddEditTaskViewModel.class);

                addEditTaskViewModel.getTask().observe(this, new Observer<TodoEntity>() {
                    @Override
                    public void onChanged(TodoEntity taskEntry) {
                        addEditTaskViewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });

            }
        }else{
            AddEditTaskViewModelFactory factory = new AddEditTaskViewModelFactory(getApplication(), mTaskId);
            addEditTaskViewModel = new ViewModelProvider(this, factory).get(AddEditTaskViewModel.class);
        }

        taskTitle.addTextChangedListener(taskInputValidation);
        taskDescription.addTextChangedListener(taskInputValidation);
        taskDate.addTextChangedListener(taskInputValidation);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }
}
