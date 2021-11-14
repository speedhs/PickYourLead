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
    Button b_one, b_two, b_three;
    FirebaseFirestore db;
    Long flagStatus;
    String cad0, cad1, cad2;
    String uId;

    String option;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //System.out.println("+++++++++++++++++++++++=========================================" + "BRANCH: ");
       // System.out.println(Register.branch);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vote);
        uId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        //button_one=findViewById(R.id.button9);

        //button_one.setText(db.collection("users").document(uId).get);

        b_one=findViewById(R.id.button6);
        b_two=findViewById(R.id.button7);
        b_three=findViewById(R.id.button9);

        vote2();

    }

    public  void nextPage(View view) {
        Intent next = new Intent(this, Results.class);
        startActivity(next);
    }

    public void onClickVirat(View view) {

        button_one=findViewById(R.id.button6);
        option=button_one.getText().toString();
        vote();
    }

    public void onClickRohit(View view) {

        button_one=findViewById(R.id.button7);
        option=button_one.getText().toString();
        vote();
    }

    public void onClickYuzi(View view) {

        button_one=findViewById(R.id.button9);
        //button_one.setText(db.collection("users").document(uId));
        option=button_one.getText().toString();
        vote();
    }

    public void vote() {

        db.collection("users").document(uId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flagStatus=documentSnapshot.getLong("flag");
                            System.out.println("FLAG STATUS ---->"+flagStatus);
                            if(flagStatus==0){
                                db.collection("trial").document(Register.branch).update(option, FieldValue.increment(1));
                                db.collection("users").document(uId).update("flag", FieldValue.increment(1));
                                Toast.makeText(Vote.this, option+"->... clicked", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(Vote.this, "Sorry,you have already voted", Toast.LENGTH_SHORT).show();
                            }
//

                        } else {
                            vote();
                            //Toast.makeText(Vote.this, "Document... does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void vote2() {

        db.collection("trial").document(Register.branch).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            cad0=documentSnapshot.getString("0");
                            System.out.println("CAD 0 ---->"+cad0);
                            b_one.setText(cad0);
                            cad1=documentSnapshot.getString("1");
                            System.out.println("CAD 1 ---->"+cad1);
                            b_two.setText(cad1);
                            cad2=documentSnapshot.getString("2");
                            System.out.println("CAD 2 ---->"+cad2);
                            b_three.setText(cad2);


                        } else {
                            Toast.makeText(Vote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

