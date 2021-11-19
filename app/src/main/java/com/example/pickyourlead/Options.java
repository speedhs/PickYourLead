package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Options extends AppCompatActivity {
    static String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void activePoll(View view) {

        Home.nextpage= "Vote";

        db.collection("flag").document("flag").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flag=documentSnapshot.getBoolean("VOTE");
                            if(flag){
                                Intent next=new Intent(Options.this,PollsList.class);
                                startActivity(next);
                            }
                            else{
                                Toast.makeText(Options.this, "Sorry Voting Period is over", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            // vote();
                            Toast.makeText(Options.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void viewResults(View view){
        Home.nextpage= "Results";
        db.collection("flag").document("flag").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flag=documentSnapshot.getBoolean("RESULT");
                            if(flag){
                                Intent next=new Intent(Options.this,PollsList.class);
                                startActivity(next);
                            }
                            else{
                                Toast.makeText(Options.this, "Sorry voting period is not over", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            // vote();
                            Toast.makeText(Options.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void contestElection(View view){
        Home.nextpage= "Contest";
        Intent next=new Intent(Options.this,PollsList.class);
        startActivity(next);
    }
}