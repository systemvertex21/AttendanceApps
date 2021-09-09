package com.example.attendanceapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    Button addAttend, viewAttend, forgetPsw, logOut;
    TextView date, date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addAttend = findViewById(R.id.addAttend);
        viewAttend = findViewById(R.id.viewAttend);
        date = findViewById(R.id.DateTextView);
        date2 = findViewById(R.id.DateTextView2);
        forgetPsw = findViewById(R.id.forgetPsw);
        logOut = findViewById(R.id.logOut1);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, day);
                        String dateText = DateFormat.format("    MM-dd-yyyy", calendar1).toString();
                        date.setText(dateText);
                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, day);
                        String dateText = DateFormat.format("    MM-dd-yyyy", calendar1).toString();
                        date2.setText(dateText);
                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.show();
            }
        });

        addAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = date.getText().toString().trim();
                if (date1.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
                } else if (date1.equals("    Date")) {
                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(),AddAttendActivity.class);
                    intent.putExtra("date", date1);
                    v.getContext().startActivity(intent);
                }
            }
        });
        viewAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateView = date2.getText().toString().trim();
                if (dateView.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
                } else if (dateView.equals("    Date")) {
                    Toast.makeText(getApplicationContext(),"Please Required Attend Date", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(),ViewAttendActivity.class);
                    intent.putExtra("dateView", dateView);
                    v.getContext().startActivity(intent);
                }
            }
        });
        forgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ChangePasswordActivity.class));
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });



    }
}