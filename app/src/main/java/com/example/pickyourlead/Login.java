package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
public class Login extends AppCompatActivity {

    public void options_page(View view) {//moving to next screen
        System.out.println("suc");
        login();
        Intent next = new Intent(this, Options.class);
        startActivity(next);
    }

    public void login() {
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
        mAuth.signInWithEmailAndPassword(email, password);/*.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() /*{
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
    public void print(View view) {
        System.out.println("hi");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

}