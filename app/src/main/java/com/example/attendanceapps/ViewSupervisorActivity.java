package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewSupervisorActivity extends AppCompatActivity {

    Query databaseReference;
    RecyclerView recyclerView;
    ArrayList<Supervisor> supervisors;
    SupervisorListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_supervisor);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("All Supervisor");

        recyclerView = findViewById(R.id.supervisorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        supervisors = new ArrayList<Supervisor>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("role").equalTo("Supervisor");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Supervisor supervisorDataList = dataSnapshot.getValue(Supervisor.class);
                    supervisors.add(supervisorDataList);
                }
                listAdapter = new SupervisorListAdapter(ViewSupervisorActivity.this, supervisors);
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewSupervisorActivity.this, "Error...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}