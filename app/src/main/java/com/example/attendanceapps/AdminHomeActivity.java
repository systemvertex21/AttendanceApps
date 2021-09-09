 package com.example.attendanceapps;

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

 public class AdminHomeActivity extends AppCompatActivity {

    Button addStaff, viewStaff, viewAttendanceStaff, addSupervisor, viewSupervisor, logOut;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addStaff = findViewById(R.id.addStaff);
        viewStaff = findViewById(R.id.viewStaff);
        viewAttendanceStaff = findViewById(R.id.viewAttendanceStaff);
        addSupervisor = findViewById(R.id.addSupervisor);
        viewSupervisor = findViewById(R.id.viewSupervisor);
//        date = findViewById(R.id.DateTextView2);

        logOut = findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,LoginActivity.class));
            }
        });

//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                int YEAR = calendar.get(Calendar.YEAR);
//                int MONTH = calendar.get(Calendar.MONTH);
//                int DATE = calendar.get(Calendar.DATE);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminHomeActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        Calendar calendar1 = Calendar.getInstance();
//                        calendar1.set(Calendar.YEAR, year);
//                        calendar1.set(Calendar.MONTH, month);
//                        calendar1.set(Calendar.DATE, day);
//                        String dateText = DateFormat.format("    MM-dd-yyyy", calendar1).toString();
//                        date.setText(dateText);
//                    }
//                }, YEAR, MONTH, DATE);
//                datePickerDialog.show();
//            }
//        });

        addStaff.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this,AddStaffActivity.class)));
        viewStaff.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this,ViewStaffActivity.class)));

        viewAttendanceStaff.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this,ViewAttendanceStaffActivity.class)));
//        viewAttendanceStaff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String dateView = date.getText().toString().trim();
//                if (dateView.isEmpty()) {
//                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
//                } else if (dateView.equals("    Date")) {
//                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(v.getContext(),ViewAttendanceStaffActivity.class);
//                    intent.putExtra("dateView", dateView);
//                    v.getContext().startActivity(intent);
//                }
//            }
//        });

        addSupervisor.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this,AddSupervisorActivity.class)));
        viewSupervisor.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this,ViewSupervisorActivity.class)));

    }
}