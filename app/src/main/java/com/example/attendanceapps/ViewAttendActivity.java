package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewAttendActivity extends AppCompatActivity {

    TextView date;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    ArrayList<Attendance> attendances;
    EditStaffAttendAdapter editStaffAttendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attend);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("View Staff Attend");

        date = findViewById(R.id.DateTextView);
        recyclerView = findViewById(R.id.staffList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendances = new ArrayList<Attendance>();

        final String attendDate = getIntent().getStringExtra("dateView");
        date.setText(attendDate);

        String dateString = date.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser rUser = firebaseAuth.getCurrentUser();
        assert rUser != null;
        String userId = rUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(userId).child(dateString);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Attendance attendance = dataSnapshot.getValue(Attendance.class);
                        attendances.add(attendance);
                    }
                    editStaffAttendAdapter = new EditStaffAttendAdapter(attendances, ViewAttendActivity.this);
                    recyclerView.setAdapter(editStaffAttendAdapter);

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(ViewAttendActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
//        }
    }
}