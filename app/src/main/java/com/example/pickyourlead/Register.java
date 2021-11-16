package com.example.pickyourlead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static int batch;


    public void options_page(View view) {//moving to next screen
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
        user.put("flag",0);
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
        Intent next = new Intent(this, Options.class);
        startActivity(next);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                batch = Integer.parseInt(mySpinner2.getSelectedItem().toString());
                //Toast.makeText(getApplicationContext(), branch , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


}