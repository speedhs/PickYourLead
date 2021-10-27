package com.example.pickyourlead;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

//firebase
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {


    public void options_page(View view) {//moving to next screen
        System.out.println("suc");
        re();
        Intent next = new Intent(this, ConductPoll.class);
        startActivity(next);
    }

         public void re() {
             FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
             mAuth.createUserWithEmailAndPassword(email, password);/*.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() /*{
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) //{
            /*if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            /*}
            else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }/
        }
    });*/
         }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.branches));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }
}