package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private EditText email, pass;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar spinner;


    public void forgot_pass(View view) {
        Intent next = new Intent(this, ForgotPasword.class);
        startActivity(next);
        spinner.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        email = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextTextPassword);

        Button btn_login = findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(v -> {
            boolean internet=isConnected();
            if(internet) {
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
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
                if (str_pass.isEmpty()) {
                    pass.setError("Password is empty");
                    pass.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(str_email, str_pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        db.collection("users").document(uId).get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            Register.branch= (String) documentSnapshot.get("branch");
                                            Register.originalBranch=Register.branch;
                                            Register.batch= (String) documentSnapshot.get("batch");
                                            Register.originalBatch=Register.batch;
                                        }
                                        else {
                                            Toast.makeText(Login.this, "Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        spinner.setVisibility(View.VISIBLE);
                        startActivity(new Intent(Login.this, Options.class));
                        spinner.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(Login.this, "Please check your Login credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "Check Internet conn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,LostConnection.class));
            }
        });
    }


    boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            if(networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;
    }
}