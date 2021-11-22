package com.example.pickyourlead;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.util.Objects;

public class Vote extends AppCompatActivity {

    Button button_one;
    Button b_one, b_two, b_three;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Long flagStatus;
    String cad0, cad1, cad2;

    long num;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        b_one=findViewById(R.id.button6);
        b_two=findViewById(R.id.button7);
        b_three=findViewById(R.id.button9);

        b_one.setVisibility(View.INVISIBLE);
        b_two.setVisibility(View.INVISIBLE);
        b_three.setVisibility(View.INVISIBLE);

        b_one=findViewById(R.id.button6);
        b_two=findViewById(R.id.button7);
        b_three=findViewById(R.id.button9);

        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                        num=documentSnapshot.getLong("num");
                        System.out.println("NUM KI VAL=->>>>>>>>"+num);
                            if(num==0){
                                System.out.println("1");
                            }
                            else if(num==1){
                                b_one.setVisibility(View.VISIBLE);
                            }
                            else if(num==2){
                                b_one.setVisibility(View.VISIBLE);
                                b_two.setVisibility(View.VISIBLE);
                            }
                            else{
                                b_one.setVisibility(View.VISIBLE);
                                b_two.setVisibility(View.VISIBLE);
                                b_three.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            Toast.makeText(Vote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        vote2();
    }

    public void onClickBtn1(View view) {
        option = b_one.getText().toString();
        vote();
    }

    public void onClickBtn2(View view) {
        option = b_two.getText().toString();
        vote();
    }

    public void onClickBtn3(View view) {
        option = b_three.getText().toString();
        vote();
    }

    public void vote() {

        db.collection("users").document(Options.uId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flagStatus=documentSnapshot.getLong(PollsList.pollsOption+"flag");
                            System.out.println("FLAG STATUS ---->"+flagStatus);
                            if(flagStatus==0){
                                db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).update(option, FieldValue.increment(1));
                                db.collection("users").document(Options.uId).update(PollsList.pollsOption+"flag", FieldValue.increment(1));
                                Toast.makeText(Vote.this, option+" clicked", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Vote.this, "Sorry,you have already voted", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                           // vote();
                            Toast.makeText(Vote.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void vote2() {

        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            cad0 = documentSnapshot.getString("0");
                            b_one.setText(cad0);

                            cad1 = documentSnapshot.getString("1");
                            b_two.setText(cad1);

                            cad2 = documentSnapshot.getString("2");
                            b_three.setText(cad2);

                        } else {
                            Toast.makeText(Vote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}