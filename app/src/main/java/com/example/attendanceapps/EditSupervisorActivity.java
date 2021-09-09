package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EditSupervisorActivity extends AppCompatActivity {

    String firstName = "", lastName = "", email = "", phone = "", supervisorId = "";
    EditText firstName1, lastName1, email1, phone1;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supervisor);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Edit Supervisor");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        supervisorId = getIntent().getStringExtra("supervisorId");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

        firstName1 = findViewById(R.id.firstName);
        lastName1 = findViewById(R.id.lastName);
        email1 = findViewById(R.id.email);
        phone1 = findViewById(R.id.phone);

        getSupervisorDetails(supervisorId);

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstName1.getText().toString()) ||
                        TextUtils.isEmpty(lastName1.getText().toString()) ||
                        TextUtils.isEmpty(email1.getText().toString()) ||
                        TextUtils.isEmpty(phone1.getText().toString()) ) {
                    Toast.makeText(EditSupervisorActivity.this, "Please Fill Your Details", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("phoneNum", phone1.getText().toString());
                    map.put("emailAddress", email1.getText().toString());
                    map.put("firstName", firstName1.getText().toString());
                    map.put("lastName", lastName1.getText().toString());


                    FirebaseDatabase.getInstance().getReference("Users").child(supervisorId).updateChildren(map).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditSupervisorActivity.this, "Your Supervisor's Details Is Updated", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(EditSupervisorActivity.this, ViewSupervisorActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditSupervisorActivity.this, "Please Required all field", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    private void getSupervisorDetails(String supervisorId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(supervisorId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Staff staff = snapshot.getValue(Staff.class);
                assert staff != null;
                firstName1.setText(staff.getFirstName());
                lastName1.setText(staff.getLastName());
                email1.setText(staff.getEmailAddress());
                phone1.setText(staff.getPhoneNum());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(EditSupervisorActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}