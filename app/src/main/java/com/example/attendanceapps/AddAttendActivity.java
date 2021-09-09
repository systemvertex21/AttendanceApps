package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddAttendActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView dateShow;
    DatabaseReference databaseReference;
    ArrayList<Staff> staffArrayList;
    StaffSelectAdapter staffSelectAdapter;
    Button addStaff, goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attend);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Add Staff Attend");

        dateShow = findViewById(R.id.dateShow);
        final String attendDate = getIntent().getStringExtra("date");
        dateShow.setText(attendDate);

        recyclerView = findViewById(R.id.staffList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        staffArrayList = new ArrayList<Staff>();

        addStaff = findViewById(R.id.addStaff);
        goBack = findViewById(R.id.goBack);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Staff");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    Staff staff = dataSnapshot1.getValue(Staff.class);
                    staffArrayList.add(staff);
                }
                staffSelectAdapter = new StaffSelectAdapter(AddAttendActivity.this, staffArrayList);
                recyclerView.setAdapter(staffSelectAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(AddAttendActivity.this, "Staff Error", Toast.LENGTH_SHORT).show();
            }
        });

        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddAttendActivity.this, "Add Attend Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddAttendActivity.this,HomeActivity.class));
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(attendDate);
                databaseReference.removeValue();
                startActivity(new Intent(AddAttendActivity.this,HomeActivity.class));
            }
        });


    }
}