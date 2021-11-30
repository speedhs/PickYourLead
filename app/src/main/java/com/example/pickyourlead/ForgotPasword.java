package com.example.pickyourlead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasword extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText email ;


    public void reset_pass (View view) {
        String str_email = email.getText().toString();
        if  (str_email.isEmpty()) {
            email.setError("Email is empty");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            email.setError("Enter a valid email id");
            email.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(str_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasword.this, "Please check your mail", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ForgotPasword.this, "Sorry, this email is not registered", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pasword);
        email= findViewById(R.id.forgotpassword);

    }
}