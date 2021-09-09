package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView resetState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Forget Password");

        mAuth = FirebaseAuth.getInstance();
        final EditText emailAddress = findViewById(R.id.email);
        resetState = findViewById(R.id.resetText);
        Button resetPasswordBtn = findViewById(R.id.sendMessage);
        progressBar = findViewById(R.id.progressBar);

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.fetchSignInMethodsForEmail(emailAddress.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (Objects.requireNonNull(task.getResult()).getSignInMethods().isEmpty()){
                            progressBar.setVisibility(View.GONE);
                            resetState.setText("invalid email this email is not register, please create your own account");
                        }
                        else{
                            mAuth.sendPasswordResetEmail(emailAddress.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()){
                                        resetState.setText("An email to reset your password has be send, you can check to your email");
                                    }
                                    else{
                                        resetState.setText(Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}