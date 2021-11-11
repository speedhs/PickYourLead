package com.example.pickyourlead;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Vote extends AppCompatActivity {

    Button button_one;
    FirebaseFirestore db;
    Long flagStatus;
    String uId=FirebaseAuth.getInstance().getCurrentUser().getUid();
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
         db = FirebaseFirestore.getInstance();
    }

    public  void nextPage(View view){
        Intent next = new Intent(this, Results.class);
        startActivity(next);
    }

    public void onClickVirat(View view){

    button_one=findViewById(R.id.button6);
    option=button_one.getText().toString();
    vote();

    }

    public void onClickRohit(View view){

        button_one=findViewById(R.id.button7);
        option=button_one.getText().toString();
        vote();
    }

    public void vote(){

        db.collection("users").document(uId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flagStatus=documentSnapshot.getLong("flag");
                            System.out.println("FLAG STATUS ---->"+flagStatus);
                            if(flagStatus==0){
                                db.collection("trial").document("players").update(option, FieldValue.increment(1));
                                db.collection("users").document(uId).update("flag", FieldValue.increment(1));
                                Toast.makeText(Vote.this, option+"->... clicked", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(Vote.this, "Sorry,you have already voted", Toast.LENGTH_SHORT).show();
                            }
//

                        } else {
                            Toast.makeText(Vote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

