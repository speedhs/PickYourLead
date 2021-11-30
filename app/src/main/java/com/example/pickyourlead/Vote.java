package com.example.pickyourlead;

import static android.content.ContentValues.TAG;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Vote extends AppCompatActivity {
    ImageView gif1, gif2;
    Button b_one, b_two, b_three;
    TextView p1, p2, p3;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Long flagStatus;
    String cad0, cad1, cad2;
    String uid1, uid2, uid3;
    boolean internet;
    long num;
    String option;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String pOption;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        b_one=findViewById(R.id.button6);
        b_two=findViewById(R.id.button7);
        b_three=findViewById(R.id.button9);
        p1 = findViewById(R.id.textView15);
        p2 = findViewById(R.id.textView16);
        p3 = findViewById(R.id.textView17);
        gif1 = findViewById(R.id.votegif);
        gif2 = findViewById(R.id.tygif);

        Glide.with(this).load(R.drawable.votegif).into(gif1);

        b_one.setVisibility(View.INVISIBLE);
        b_two.setVisibility(View.INVISIBLE);
        b_three.setVisibility(View.INVISIBLE);
        p1.setVisibility(View.INVISIBLE);
        p2.setVisibility(View.INVISIBLE);
        p3.setVisibility(View.INVISIBLE);

        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            num=documentSnapshot.getLong("num");
                           // System.out.println("NUM KI VAL=->>>>>>>>"+num);
                            if (num == 0){
                                System.out.println("1");
                            }
                            else if (num == 1){
                                b_one.setVisibility(View.VISIBLE);
                                p1.setVisibility(View.VISIBLE);
                            }
                            else if (num == 2){
                                b_one.setVisibility(View.VISIBLE);
                                b_two.setVisibility(View.VISIBLE);
                                p1.setVisibility(View.VISIBLE);
                                p2.setVisibility(View.VISIBLE);

                            }
                            else {
                                b_one.setVisibility(View.VISIBLE);
                                b_two.setVisibility(View.VISIBLE);
                                b_three.setVisibility(View.VISIBLE);
                                p1.setVisibility(View.VISIBLE);
                                p2.setVisibility(View.VISIBLE);
                                p3.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            Toast.makeText(Vote.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        vote2();
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(Vote.this,Options.class));
    }
    public void onClickBtn1(View view) {
        option = b_one.getText().toString();
        Glide.with(this).load(R.drawable.tygif).into(gif2);
        gif1.setVisibility(View.GONE);
        internet=isConnected();
        if(internet) {
            vote(); }
        else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Vote.this,LostConnection.class));
        }
    }

    public void onClickBtn2(View view) {
        option = b_two.getText().toString();
        Glide.with(this).load(R.drawable.tygif).into(gif2);
        gif1.setVisibility(View.GONE);
        internet=isConnected();
        if(internet) {
            vote();
        } else {
            Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Vote.this,LostConnection.class));
        }
    }

    public void onClickBtn3(View view) {
        option = b_three.getText().toString();
        Glide.with(this).load(R.drawable.tygif).into(gif2);
        gif1.setVisibility(View.GONE);
        internet=isConnected();
        if(internet) {
            vote();
        } else {
            Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Vote.this,LostConnection.class));
        }
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
                                Toast.makeText(Vote.this, option+" clicked", Toast.LENGTH_LONG).show();
                                gif2.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(Vote.this, "Sorry,but you have already voted", Toast.LENGTH_LONG).show();
                                gif2.setVisibility(View.INVISIBLE);
                            }
                        }
                        else {
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
                            uid1 = documentSnapshot.getString(cad0+"uId");

                            cad1 = documentSnapshot.getString("1");
                            b_two.setText(cad1);
                            uid2 = documentSnapshot.getString(cad1+"uId");

                            cad2 = documentSnapshot.getString("2");
                            b_three.setText(cad2);
                            uid3 = documentSnapshot.getString(cad2+"uId");

                        } else {
                            Toast.makeText(Vote.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void portfolio(String uID) {
        String root;
        if(PollsList.item.equals("CLASS REPRESENTATIVE")){
                root=PollsList.item;
        }
        else{
            root="COUNCIL";
        }
        storageReference.child("portfolio/"+root+"/" + uID + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFile(Vote.this,pOption,".pdf",DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Vote.this, "Portfolio is not available", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void downloadFile(Context context,String fileName,String fileExtension,String destinationDirectory,String url){
        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);
        downloadManager.enqueue(request);
    }

    public void view1(View view) throws IOException {
        portfolio(uid1);
        pOption=cad0;
    }

    public void view2(View view) throws IOException {
        portfolio(uid2);
        pOption=cad1;
    }

    public void view3(View view) throws IOException {
        portfolio(uid3);
        pOption=cad2;
    }


    boolean isConnected(){
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
            startActivity(new Intent(Vote.this, Home.class));

        }
        return true;
    }
}