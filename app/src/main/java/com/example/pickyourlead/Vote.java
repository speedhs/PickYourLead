package com.example.pickyourlead;

import static android.content.ContentValues.TAG;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    Button p_one,p_two,p_three;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Long flagStatus;
    String cad0, cad1, cad2;
    String uid1, uid2, uid3;

    TextView p1, p2, p3;

    long num;
    String option;

//    Button view1;
//    DatabaseReference dbRef;
//    String msg;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    //StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        b_one=findViewById(R.id.button6);
        b_two=findViewById(R.id.button7);
        b_three=findViewById(R.id.button9);
        p_one=findViewById(R.id.port1);
        p_two=findViewById(R.id.port2);
        p_three=findViewById(R.id.port3);
        gif1 = findViewById(R.id.votegif);
        gif2 = findViewById(R.id.tygif);

        Glide.with(this).load(R.drawable.votegif).into(gif1);


        b_one.setVisibility(View.INVISIBLE);
        b_two.setVisibility(View.INVISIBLE);
        b_three.setVisibility(View.INVISIBLE);
        p_one.setVisibility(View.INVISIBLE);
        p_two.setVisibility(View.INVISIBLE);
        p_three.setVisibility(View.INVISIBLE);

        // view1 = findViewById(R.id.port1);

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
                                p_one.setVisibility(View.VISIBLE);
                            }
                            else if(num==2){
                                b_one.setVisibility(View.VISIBLE);
                                b_two.setVisibility(View.VISIBLE);
                                p_one.setVisibility(View.VISIBLE);
                                p_two.setVisibility(View.VISIBLE);

                            }
                            else{
                                b_one.setVisibility(View.VISIBLE);
                                b_two.setVisibility(View.VISIBLE);
                                b_three.setVisibility(View.VISIBLE);
                                p_one.setVisibility(View.VISIBLE);
                                p_two.setVisibility(View.VISIBLE);
                                p_three.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            Toast.makeText(Vote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        vote2();

        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//        storageReference.child("portfolio/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.png'
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
//        final StorageReference filepath = storageReference.child("portfolio").child(uid + ".pdf");
//        dbRef = FirebaseDatabase.getInstance().getReference().child("portfolio").child(uid + ".pdf");
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                msg = snapshot.getValue(String.class);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Vote.this, "PDF Loading error!", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        view1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(filepath.toString()), "application/pdf");
//                startActivity(Intent.createChooser(intent, "Choose an Application:"));
//            }
//        });
    }

    public void onClickBtn1(View view) {
        option = b_one.getText().toString();
        Glide.with(this).load(R.drawable.tygif).into(gif2);
        gif1.setVisibility(View.GONE);
        vote();
    }

    public void onClickBtn2(View view) {
        option = b_two.getText().toString();
        Glide.with(this).load(R.drawable.tygif).into(gif2);
        gif1.setVisibility(View.GONE);
        vote();
        //gif2.setVisibility(View.INVISIBLE);
    }

    public void onClickBtn3(View view) {
        option = b_three.getText().toString();
        Glide.with(this).load(R.drawable.tygif).into(gif2);
        gif1.setVisibility(View.GONE);
        vote();
        //gif2.setVisibility(View.VISIBLE);
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
                                Toast.makeText(Vote.this, "Sorry,you have already voted", Toast.LENGTH_LONG).show();
                                gif2.setVisibility(View.INVISIBLE);
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
        //storageReference = FirebaseStorage.getInstance().getReference();
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
                            Toast.makeText(Vote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void portfolio(String uID) {
        System.out.println("==========================================================================" + uID);
        storageReference.child("portfolio/" + uID + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
               // Toast.makeText(Vote.this, "Worked", Toast.LENGTH_SHORT).show();
                String url=uri.toString();
                downloadFile(Vote.this,"Mobile",".pdf",DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(Vote.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void portfolio(String uID) throws IOException {
//        StorageReference storageRef = storageReference.child("portfolio/" + uID + ".pdf");
//        File localFile = File.createTempFile("port", "pdf");
//        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                // Local temp file has been created
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
//    }

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
    }

    public void view2(View view) throws IOException {
        portfolio(uid2);
    }

    public void view3(View view) throws IOException {
        portfolio(uid3);
    }
}