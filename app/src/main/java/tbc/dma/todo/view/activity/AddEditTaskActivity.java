package tbc.dma.todo.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import tbc.dma.todo.R;

public class AddEditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        /*Task Priority Drop Down list*/
        Spinner spinnerTaskPriority = findViewById(R.id.spinnerTaskPriority);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTaskPriority.setAdapter(adapter);

        /*Android DatePicker Dialog when textview is clicked*/
        final TextView dateTextView= findViewById(R.id.textViewTaskDate);
        dateTextView.setOnClickListener(new View.OnClickListener() {
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
                                dateTextView.setBackground(null);
                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year ;
                                dateTextView.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

}
