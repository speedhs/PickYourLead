package com.example.pickyourlead;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Results extends AppCompatActivity {

    TextView textviewone;
    TextView textviewtwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
//        textviewone=findViewById(R.id.textView9);
//        textviewtwo=findViewById(R.id.textView13);
//        db.collection("trial").document("players").get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                          Long vScore=documentSnapshot.getLong("VIRAT");
//                            Long rScore=documentSnapshot.getLong("ROHIT");
////                            String rScore = (String) documentSnapshot.get("ROHIT");
//                            //Map<String, Object> note = documentSnapshot.getData();
//
//                            textviewone.setText("Virat got "+vScore+" votes");
//                            textviewtwo.setText("Rohit got "+rScore+" votes");
//
//                        } else {
//                            Toast.makeText(Results.this, "Document does not exist", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Results.this, "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, e.toString());
//                    }
//                });
    }
}