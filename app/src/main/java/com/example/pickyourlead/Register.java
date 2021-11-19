package com.example.pickyourlead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

//firebase
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String branch;
    static String originalBranch;
    static String originalBatch;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static String batch;
    private ProgressBar spinner;



    public void options_page(View view) {//moving to next screen
        spinner.setVisibility(View.VISIBLE);
        System.out.println("suc");
        re();

    }

    public void re() {

        // Initialize Firebase Auth
//             void onStart(){
//                 super.onStart();
//                 // Check if user is signed in (non-null) and update UI accordingly.
//                 FirebaseUser currentUser = mAuth.getCurrentUser();
//                 if (currentUser != null) {
//                     currentUser.reload();
//                 }
//             }
        EditText mail,pass;
        mail=findViewById(R.id.editTextTextEmailAddress2);
        pass=findViewById(R.id.editTextTextPassword2);
        String password=pass.getText().toString();
        String email=mail.getText().toString();
        if  (email.isEmpty()) {
            mail.setError("Email is empty");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid email id");
            mail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password is empty");
            pass.requestFocus();
            return;
        }
        if (password.length()<6) {
            pass.setError("Length of password should be more than 6");
            pass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            storefire(email);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });


    }

    public void storefire(String email)
    {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("branch",branch);
        user.put("batch", batch);
        user.put("CLASS REPRESENTATIVEflag",0);
        user.put("PRESIDENTflag",0);
        user.put("VICE PRESIDENTflag",0);
        user.put("CULTURAL SECRETARYflag",0);
        user.put("SPORTS SECRETARYflag",0);
        user.put("SECRETARY OF ACADEMIC AFFAIRSflag",0);
        user.put("SECRETARY OF EXTERNAL AFFAIRSflag",0);
        user.put("SECRETARY OF SENATEflag",0);

        user.put("CLASS REPRESENTATIVEcand",0);
        user.put("PRESIDENTcand",0);
        user.put("VICE PRESIDENTcand",0);
        user.put("CULTURAL SECRETARYcand",0);
        user.put("SPORTS SECRETARYcand",0);
        user.put("SECRETARY OF ACADEMIC AFFAIRScand",0);
        user.put("SECRETARY OF EXTERNAL AFFAIRScand",0);
        user.put("SECRETARY OF SENATEcand",0);





        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);


        Intent next = new Intent(this, Options.class);
        startActivity(next);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner5);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.branches));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.batch));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {
                branch = mySpinner.getSelectedItem().toString();
                originalBranch=branch;
                //Toast.makeText(getApplicationContext(), branch , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {
                batch = mySpinner2.getSelectedItem().toString();


                originalBatch=batch;
                //Toast.makeText(getApplicationContext(), branch , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


}