package com.example.attendanceapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPsw,newPsw,confirmPsw;
    Button changePsw;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Reset Password");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        oldPsw = findViewById(R.id.oldPsw);
        newPsw  = findViewById(R.id.newPsw);
        confirmPsw = findViewById(R.id.confirmPsw);
        changePsw = findViewById(R.id.resetPsw);
        progressBar = findViewById(R.id.progressBar);

        changePsw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String txtOldPsw = oldPsw.getText().toString();
                String txtNewPsw = newPsw.getText().toString();
                String txtConfirmPsw =  confirmPsw.getText().toString();
                if(txtOldPsw.isEmpty() || txtNewPsw.isEmpty() || txtConfirmPsw.isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this,"All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(txtNewPsw.length() < 6){
                    Toast.makeText(ChangePasswordActivity.this,"The new password length should be more then 6 character",Toast.LENGTH_SHORT).show();
                }
                else if (! txtConfirmPsw.equals(txtNewPsw)){
                    Toast.makeText(ChangePasswordActivity.this,"Confirm password is no match will new password",Toast.LENGTH_SHORT).show();
                }
                else{
                    changePassword(txtOldPsw.trim(),txtNewPsw.trim());
                }
            }
        });

    }

    private void changePassword(String txtOldPsw, String txtNewPsw) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(firebaseUser.getEmail()),txtOldPsw);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    firebaseUser.updatePassword(txtNewPsw).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("password" , txtNewPsw);
                                databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ChangePasswordActivity.this, "Your Password Is Updated", Toast.LENGTH_LONG).show();
                                        firebaseAuth.signOut();
                                        Intent intent = new Intent(ChangePasswordActivity.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                            }
                            else{
                                Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}