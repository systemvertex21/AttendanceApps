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

public class EditStaffActivity extends AppCompatActivity {

    String firstName = "", lastName = "", email = "", phone = "", staffId = "";
    EditText firstName1, lastName1, email1, phone1;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Edit Staff");

        staffId = getIntent().getStringExtra("staffId");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

        firstName1 = findViewById(R.id.firstName);
        lastName1 = findViewById(R.id.lastName);
        email1 = findViewById(R.id.email);
        phone1 = findViewById(R.id.phone);

        getStaffDetails(staffId);

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstName1.getText().toString()) ||
                        TextUtils.isEmpty(lastName1.getText().toString()) ||
                        TextUtils.isEmpty(email1.getText().toString()) ||
                        TextUtils.isEmpty(phone1.getText().toString()) ) {
                    Toast.makeText(EditStaffActivity.this, "Please Fill Your Details", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("phoneNum", phone1.getText().toString());
                    map.put("emailAddress", email1.getText().toString());
                    map.put("firstName", firstName1.getText().toString());
                    map.put("lastName", lastName1.getText().toString());
                    FirebaseDatabase.getInstance().getReference("Staff").child(staffId).updateChildren(map).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditStaffActivity.this, "Your Supervisor's Details Is Updated", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(EditStaffActivity.this, ViewStaffActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditStaffActivity.this, "Please Required all field", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    private void getStaffDetails(String staffId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Staff").child(staffId);
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
                Toast.makeText(EditStaffActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}