package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class ViewAttendanceStaffActivity extends AppCompatActivity {

    TextView date;
    DatabaseReference databaseReference;
    Query query;
    RecyclerView recyclerView;
//    ArrayList<Attendance> attendances;
    ArrayList<Supervisor> supervisors;
    SupervisorListAdapter2 supervisorListAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_staff);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Choose Supervisor First");

        recyclerView = findViewById(R.id.staffList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        supervisors = new ArrayList<Supervisor>();

        query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("role").equalTo("Supervisor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Supervisor supervisor = dataSnapshot.getValue(Supervisor.class);
                    supervisors.add(supervisor);
                }
                supervisorListAdapter2 = new SupervisorListAdapter2(ViewAttendanceStaffActivity.this, supervisors);
                recyclerView.setAdapter(supervisorListAdapter2);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewAttendanceStaffActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}