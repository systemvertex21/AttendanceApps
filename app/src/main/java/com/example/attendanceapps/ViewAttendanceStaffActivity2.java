package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewAttendanceStaffActivity2 extends AppCompatActivity {

    TextView supervisorName;
    DatabaseReference databaseReference;
    Query query;
    RecyclerView recyclerView;
    ArrayList<Attendance> attendances;
    EditStaffAttendAdapter2 editStaffAttendAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_staff2);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Staff Attend List");


        final String supervisorId = getIntent().getStringExtra("supervisorId");
        final String lname = getIntent().getStringExtra("lname");
        final String date = getIntent().getStringExtra("date");


        recyclerView = findViewById(R.id.staffList);
        supervisorName = findViewById(R.id.supervisorName);

        supervisorName.setText(lname);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendances = new ArrayList<Attendance>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(supervisorId).child(date);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Attendance attendance = dataSnapshot.getValue(Attendance.class);
                    attendances.add(attendance);
                }
                editStaffAttendAdapter2 = new EditStaffAttendAdapter2(attendances, ViewAttendanceStaffActivity2.this);
                recyclerView.setAdapter(editStaffAttendAdapter2);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewAttendanceStaffActivity2.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}