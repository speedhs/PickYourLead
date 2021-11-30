package com.example.pickyourlead;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ContestElection extends AppCompatActivity {
    EditText sub;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final int PICK_IMAGE_REQUEST = 234;
    static String position;//CR OR COUNCIL
    StorageReference filepath;
    int pdfFlag;

//    private Button btnChoose;
//    private Button btnUpload;
//    private ImageView imageView;

    //private Uri filePath;


    Uri imageuri = null;
    Button btnChoose;
    ProgressDialog dialog;
    boolean net;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_election);
        sub = findViewById(R.id.candName);
        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net=isConnected();
                if(net==false){
                    Toast.makeText(getApplicationContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ContestElection.this, LostConnection.class));
                }
                else {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("application/pdf");
                    //  startActivityForResult(Intent.createChooser(galleryIntent,"PDF file selected"), 12);
                    startActivityForResult(galleryIntent, 1);
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            pdfFlag=1;
            imageuri = data.getData();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            Toast.makeText(ContestElection.this, "File Attached", Toast.LENGTH_SHORT).show();


            if(PollsList.pollsOption.equals("CLASS REPRESENTATIVE")){

                position="CLASS REPRESENTATIVE";
            }
            else{

                position="COUNCIL";
            }



            filepath = storageReference.child("portfolio").child(position).child(uid + ".pdf");
            //Toast.makeText(ContestElection.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            // dialog.dismiss();

        }
        else{
            pdfFlag=0;
        }
    }


    public void submit (View view) {
        net = isConnected();
        if (net == false) {
            Toast.makeText(getApplicationContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ContestElection.this, LostConnection.class));
        } else {
            if (pdfFlag == 0) {
                Toast.makeText(ContestElection.this, "Please attach your portfolio", Toast.LENGTH_SHORT).show();
                return;
            }


            db.collection("users").document(Options.uId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {

                                System.out.println("UID------->>>" + Options.uId);
                                long flagStatus;
                                if (PollsList.pollsOption.equals("CLASS REPRESENTATIVE")) {
                                    flagStatus = documentSnapshot.getLong(PollsList.pollsOption + "cand");

                                } else {
                                    flagStatus = documentSnapshot.getLong("COUNCILcand");

                                }

                                System.out.println("FLAG STATUS ---->" + flagStatus);
                                if (flagStatus == 0) {
                                    final ProgressDialog pd = new ProgressDialog(ContestElection.this);
                                    pd.setTitle("Uploading ");
                                    pd.show();
                                    System.out.println("FILE NAME ->" + imageuri);
                                    filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            pd.dismiss();
                                            Toast.makeText(ContestElection.this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ContestElection.this, Options.class));

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(ContestElection.this, "Uploading Failed.Try Again", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                            double proPercent = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                            pd.setMessage("Progess: " + (int) proPercent + "%");
                                        }
                                    });


                                    if (position.equals("CLASS REPRESENTATIVE")) {
                                        db.collection("users").document(Options.uId).update(PollsList.pollsOption + "cand", FieldValue.increment(1));
                                        storecand();
                                    } else {
                                        db.collection("users").document(Options.uId).update("COUNCILcand", FieldValue.increment(1));
                                        storecand();
                                    }

                                } else {
                                    Toast.makeText(ContestElection.this, "Sorry you cannot contest for more than once for CR or COUNCIL position", Toast.LENGTH_LONG).show();

                                    return;
                                }
                            } else {
                                Toast.makeText(ContestElection.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void storecand(){
        Map<String, Object> user = new HashMap<>();
        user.put(Long.toString(PollsList.counter), sub.getText().toString());
        user.put(sub.getText().toString(),0);
        user.put(sub.getText().toString()+"uId",Options.uId);

        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).set(user, SetOptions.merge());
        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).update("num", FieldValue.increment(1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_r, menu);
        return true;

    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(ContestElection.this,Options.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ContestElection.this, Home.class));

        }
        return true;
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

}

