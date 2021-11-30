package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Options extends AppCompatActivity {
    static String uId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public void activePoll(View view) {
        Home.nextpage = "Vote";
        db.collection("flag").document("flag").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flag = documentSnapshot.getBoolean("VOTE");
                            if (flag) {
                                Intent next = new Intent(Options.this, PollsList.class);
                                startActivity(next);
                            } else {
                                Toast.makeText(Options.this, "Voting Period is not live", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // vote();
                            Toast.makeText(Options.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void viewResults(View view) {
        Home.nextpage = "Results";
        db.collection("flag").document("flag").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flag = documentSnapshot.getBoolean("RESULT");
                            if (flag) {
                                Intent next = new Intent(Options.this, PollsList.class);
                                startActivity(next);
                            } else {
                                Toast.makeText(Options.this, "Results are not out", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Options.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void contestElection(View view) {
        Home.nextpage = "Contest";
        db.collection("flag").document("flag").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            flag = documentSnapshot.getBoolean("CONTEST");
                            if (flag) {
                                Intent next = new Intent(Options.this, PollsList.class);
                                startActivity(next);
                            } else {
                                Toast.makeText(Options.this, "Contesting period is not live", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Options.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_r, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Options.this, Home.class));

        }
        return true;
    }

}