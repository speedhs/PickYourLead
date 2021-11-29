package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PollsList extends AppCompatActivity {
    ImageView gif1;
    static String pollsOption;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String item;
    static long counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_list);
        gif1 = findViewById(R.id.listgif);
        Glide.with(this).load(R.drawable.listgif).into(gif1);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(PollsList.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.council_elections));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {
                item = mySpinner.getSelectedItem().toString();
                pollsOption = item;
                if (!(item.equals("CLASS REPRESENTATIVE"))){
                    Register.branch=item;
                    Register.batch="2022";
                }
                else{
                    Register.branch=Register.originalBranch;
                    Register.batch=Register.originalBatch;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void navigate(View view){
        if (Home.nextpage.equals("Vote")) {
            boolean net= isConnected();
            if (net == false) {

                Toast.makeText(getApplicationContext(), "INTERNET CHECK KAR" , Toast.LENGTH_LONG).show();
                startActivity(new Intent(PollsList.this,LostConnection.class));
            }
            else {
                Intent intent = new Intent(PollsList.this, Vote.class);
                startActivity(intent);}
        }
        else if (Home.nextpage.equals("Contest")) {
            boolean net= isConnected();
            if (net == true) {
                db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                     counter=documentSnapshot.getLong("num");
                                    if(counter==3){
                                        Toast.makeText(PollsList.this,"Limit exceeded",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent intent = new Intent(PollsList.this, ContestElection.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "PLEASE TRY AGAIN" , Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else {
                Toast.makeText(getApplicationContext(), "INTERNET CHECK KAR" , Toast.LENGTH_LONG).show();
                startActivity(new Intent(PollsList.this,LostConnection.class));
               }
        }
        else {
            boolean net= isConnected();
            if (net == false) {
                Toast.makeText(getApplicationContext(), "INTERNET CHECK KAR" , Toast.LENGTH_LONG).show();
                startActivity(new Intent(PollsList.this,LostConnection.class));
            }
            else {
                Intent intent = new Intent(PollsList.this, Results.class);
                startActivity(intent);}
        }
    }


    boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if(networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;
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
            startActivity(new Intent(PollsList.this, Home.class));
        }
        return true;
    }
}