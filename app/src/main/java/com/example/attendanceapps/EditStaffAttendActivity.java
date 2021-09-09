package com.example.attendanceapps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditStaffAttendActivity extends AppCompatActivity {

    TextView firstName, lastName, phoneNum, emailAddress, attendanceDate;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth firebaseAuth;
    Button update, cancel;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_attend);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNum = findViewById(R.id.phoneNum);
        emailAddress = findViewById(R.id.emailAddress);
        attendanceDate = findViewById(R.id.attendDate);
        radioGroup = findViewById(R.id.radioGroup);
        update = findViewById(R.id.update);
        cancel = findViewById(R.id.cancel);

        final String firstName1 = getIntent().getStringExtra("staffFirstName");
        firstName.setText(firstName1);
        final String lastName1 = getIntent().getStringExtra("staffLastName");
        lastName.setText(lastName1);
        final String phoneNum1 = getIntent().getStringExtra("phoneNum");
        phoneNum.setText(phoneNum1);
        final String emailAddress1 = getIntent().getStringExtra("emailAddress");
        emailAddress.setText(emailAddress1);
        final String attendanceDate1 = getIntent().getStringExtra("attendDate");
        attendanceDate.setText(attendanceDate1);

        final String attendance = getIntent().getStringExtra("attendance");
        final String staffId = getIntent().getStringExtra("staffId");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser rUser = firebaseAuth.getCurrentUser();
        assert rUser != null;
        String userId = rUser.getUid();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selected_attendance = radioGroup.findViewById(selectedId);

                if(selected_attendance == null){
                    Toast.makeText(EditStaffAttendActivity.this,"Please Select Attendance", Toast.LENGTH_SHORT).show();
                } else {
                    final String attendance1 = selected_attendance.getText().toString();

                    databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(userId).child(attendanceDate1).child(staffId);
                    Map<String, Object> hashMap = new HashMap<>();
                    hashMap.put("attendance" , attendance1);
                    databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditStaffAttendActivity.this, "The Staff's Attendance Is Updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(EditStaffAttendActivity.this, HomeActivity.class));
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(v -> {
            startActivity(new Intent(EditStaffAttendActivity.this,HomeActivity.class));
        });


    }
}