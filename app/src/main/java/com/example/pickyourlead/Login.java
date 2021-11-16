package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
public class Login extends AppCompatActivity {
    private EditText email, pass;
    FirebaseAuth mAuth;

    public void options_page(View view) {//moving to next screen
        System.out.println("suc");
        //login();
        Intent next = new Intent(this, Options.class);
        startActivity(next);
    }

//    public void login() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        // Initialize Firebase Auth
////             void onStart(){
////                 super.onStart();
////                 // Check if user is signed in (non-null) and update UI accordingly.
////                 FirebaseUser currentUser = mAuth.getCurrentUser();
////                 if (currentUser != null) {
////                     currentUser.reload();
////                 }
////             }
//        EditText mail,pass;
//        mail=findViewById(R.id.editTextTextEmailAddress2);
//        pass=findViewById(R.id.editTextTextPassword2);
//        String password=pass.getText().toString();
//        String email=mail.getText().toString();
//        mAuth.signInWithEmailAndPassword(email, password);/*.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() /*{
//        @Override
//        public void onComplete(@NonNull Task<AuthResult> task) //{
//            /*if (task.isSuccessful()) {
//            // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "createUserWithEmail:success");
//                FirebaseUser user = mAuth.getCurrentUser();
//                updateUI(user);
//            /*}
//            else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show();
//                updateUI(null);
//            }/
//        }
//    });*/
//    }
//    public void print(View view) {
//        System.out.println("hi");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextTextPassword);
        Button btn_login = findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(v -> {
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
            if (str_pass.length()<6) {
                pass.setError("Length of password should be more than 6");
                pass.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(str_email, str_pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    startActivity(new Intent(Login.this, Options.class));
                }
                else {
                    Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}