package com.example.attendanceapps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ChooseDateActivity extends AppCompatActivity {

    TextView date;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        date = findViewById(R.id.DateTextView1);
        next = findViewById(R.id.next);

        final String supervisorId1 = getIntent().getStringExtra("supervisorId");
        final String lname = getIntent().getStringExtra("lname");


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseDateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, day);
                        String dateText = DateFormat.format("MM-dd-yyyy", calendar1).toString();
                        date.setText(dateText);
                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateView = date.getText().toString().trim();
                if (dateView.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(), ViewAttendanceStaffActivity2.class);
                    intent.putExtra("supervisorId", supervisorId1);
                    intent.putExtra("date", dateView);
                    intent.putExtra("lname", lname);
                    v.getContext().startActivity(intent);
                }
            }
        });

    }
}