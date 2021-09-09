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

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class RegAdminActivity extends AppCompatActivity {

    EditText username, email, password, cPassword, phoneNum;
    Button register;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Register Admin");

        firebaseAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNum = findViewById(R.id.phoneNum);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.register);

        progressBar = findViewById(R.id.progressBar2);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username1 = username.getText().toString();
                final String email1 = email.getText().toString();
                final String phoneNum1 = phoneNum.getText().toString();
                final String aRole = "Admin";
                final String password1 = password.getText().toString();
                final String confirmPassword1 = cPassword.getText().toString();

                if (!TextUtils.isEmpty(password1) && !TextUtils.isEmpty(confirmPassword1))
                {
                    if(password1.equals(confirmPassword1)) {
                        if (TextUtils.isEmpty(username1) || TextUtils.isEmpty(email1) || TextUtils.isEmpty(phoneNum1) ||
                                TextUtils.isEmpty(password1)) {
                            Toast.makeText(RegAdminActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                        } else {
                            register(username1, aRole, email1, phoneNum1, password1);
                        }
                    }
                    else {
                        Toast.makeText(RegAdminActivity.this,"Password Not Match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void register(String username1, String aRole, String email1, String phoneNum1, String password1) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser rUser = firebaseAuth.getCurrentUser();
                assert rUser != null;
                String userId = rUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("userId", userId);
                hashMap.put("username", username1);
                hashMap.put("role", aRole);
                hashMap.put("email", email1);
                hashMap.put("mobile", phoneNum1);
                hashMap.put("password",password1);
                databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    progressBar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
                        Intent intent = new Intent(RegAdminActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(RegAdminActivity.this,"Register Completed",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegAdminActivity.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegAdminActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}