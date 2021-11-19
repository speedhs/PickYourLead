package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ContestElection extends AppCompatActivity {
    EditText sub;
    long flagStatus;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_election);
        sub=findViewById(R.id.candName);
    }

    public void submit (View view) throws InterruptedException {

        db.collection("users").document(Options.uId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flagStatus=documentSnapshot.getLong(PollsList.pollsOption+"cand");
                            System.out.println("FLAG STATUS ---->"+flagStatus);
                            if(flagStatus==0){

                                db.collection("users").document(Options.uId).update(PollsList.pollsOption+"cand", FieldValue.increment(1));
                                storecand();
                            }

                            else{
                                Toast.makeText(ContestElection.this, "Better luck next time", Toast.LENGTH_SHORT).show();
                            }
//

                        }
                        else {
                            // vote();
                            Toast.makeText(ContestElection.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });















    }

    public void storecand(){
        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            long counter=documentSnapshot.getLong("num");
                            if(counter==3){
                                Toast.makeText(ContestElection.this,"Limit exceeded",Toast.LENGTH_LONG).show();

                                return ;
                            }

                            Map<String, Object> user = new HashMap<>();


                            user.put(Long.toString(counter), sub.getText().toString());

                            user.put(sub.getText().toString(),0);
                            db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).set(user, SetOptions.merge());
                            db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).update("num", FieldValue.increment(1));

                        }
                        else {
                            // vote();
                            System.out.println("Hello");

                        }
                    }
                });
    }
}