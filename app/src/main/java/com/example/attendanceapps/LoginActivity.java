package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Button register, login;
    private EditText email, password;
    private ProgressBar progressBar;
    private DatabaseReference jLoginDatabase;
    private TextView forgetPassword;
    private FirebaseAuth firebaseAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final String A = "Admin";
        final String S = "Supervisor";

        register = findViewById(R.id.buttonRegister);
        login = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgetPassword = findViewById(R.id.forgetPassword);

        //Register Admin
        register.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,RegAdminActivity.class)));

        //forget Password
        forgetPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class)));

        //login user
        login.setOnClickListener(v -> {
            String email1 = email.getText().toString();
            String password1 = password.getText().toString();

            if(TextUtils.isEmpty(email1) || TextUtils.isEmpty(password1)) {
                Toast.makeText(LoginActivity.this,"All Fields Required", Toast.LENGTH_SHORT).show();
            } else if(!email1.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            } else {
                login(email1.trim(),password1.trim(),A,S);
            }
        });
    }

    private void login(String email1, String password1, final String A, final String S) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            assert currentUser != null;
            String RegisteredUserID = currentUser.getUid();

            jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);
            jLoginDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String userType = (Objects.requireNonNull(snapshot.child("role").getValue())).toString();
                    if (userType.equals(A)) {
                        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else if (userType.equals(S)) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        });
    }
}