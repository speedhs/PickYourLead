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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;




public class Vote extends AppCompatActivity {


     Button button_one;
     FirebaseFirestore db;
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
    String option=button_one.getText().toString();
        db.collection("trial").document("players").update("VIRAT", FieldValue.increment(1));
        Toast.makeText(Vote.this, option+"->... clicked", Toast.LENGTH_SHORT).show();
    }

    public void onClickRohit(View view){

        button_one=findViewById(R.id.button7);
        String option=button_one.getText().toString();

        db.collection("trial").document("players").update("ROHIT", FieldValue.increment(1));
        Toast.makeText(Vote.this, option +"->... clicked", Toast.LENGTH_SHORT).show();
    }
}

//    Map<String,Object> note = new HashMap<>();
//    note.put("Dhoni"," M.S. ");
//    db.collection("trial 2").document("players 2").set(note);
//DocumentReference washingtonRef = db.collection("cities").document("DC");
//.update("VIRAT", FieldValue.increment(1))