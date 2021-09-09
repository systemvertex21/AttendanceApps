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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AddSupervisorActivity extends AppCompatActivity {

    EditText firstName, lastName, phoneNum, emailAddress, password;
    Button register, cancel;
    ProgressBar progressBar;
    Supervisor supervisor;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supervisor);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Register Supervisor");

        firebaseAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        phoneNum = findViewById(R.id.editTextPhone);
        emailAddress = findViewById(R.id.editTextEmailAddr);
        password = findViewById(R.id.editTextPassword);

        progressBar = findViewById(R.id.progressBar2);
        register = findViewById(R.id.registerButton);
        cancel = findViewById(R.id.cancelButton);

        supervisor = new Supervisor();

        register.setOnClickListener(v -> {
            final String firstName1 = firstName.getText().toString().trim();
            final String lastName1 = lastName.getText().toString().trim();
            final String phoneNum1 = phoneNum.getText().toString().trim();
            final String emailAddress1 = emailAddress.getText().toString().trim();
            final String password1 = password.getText().toString().trim();
            final String supervisorId1 = UUID.randomUUID().toString();

            if (TextUtils.isEmpty(firstName1) || TextUtils.isEmpty(lastName1) || TextUtils.isEmpty(phoneNum1) ||
                    TextUtils.isEmpty(emailAddress1)) {
                Toast.makeText(AddSupervisorActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if(!emailAddress1.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            } else if(!phoneNum1.matches(MobilePattern)) {
                Toast.makeText(getApplicationContext(),"Invalid Phone Number", Toast.LENGTH_SHORT).show();
            } else {
                register(firstName1, lastName1, phoneNum1, emailAddress1, password1);
            }

        });

        cancel.setOnClickListener(v -> startActivity(new Intent(AddSupervisorActivity.this, AdminHomeActivity.class)));


    }

    private void register(String firstName1, String lastName1, String phoneNum1, String emailAddress1, String password1) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(emailAddress1, password1).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser rUser = firebaseAuth.getCurrentUser();
                assert rUser != null;
                String userId = rUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("supervisorId", userId);
                hashMap.put("firstName", firstName1);
                hashMap.put("lastName", lastName1);
                hashMap.put("phoneNum", phoneNum1);
                hashMap.put("emailAddress", emailAddress1);
                hashMap.put("password", password1);
                hashMap.put("role", "Supervisor");
                databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    progressBar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
                        Intent intent = new Intent(AddSupervisorActivity.this, AdminHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(AddSupervisorActivity.this, "Register Completed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddSupervisorActivity.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddSupervisorActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}