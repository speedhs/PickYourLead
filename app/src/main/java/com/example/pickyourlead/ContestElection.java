package com.example.pickyourlead;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
    Uri imageuri = null;
    Button btnChoose;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_election);
        sub = findViewById(R.id.candName);
        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.show();
            imageuri = data.getData();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            Toast.makeText(ContestElection.this, imageuri.toString(), Toast.LENGTH_SHORT).show();
            final StorageReference filepath = storageReference.child("portfolio").child(uid + ".pdf");
            Toast.makeText(ContestElection.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        Toast.makeText(ContestElection.this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(ContestElection.this,  "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void submit (View view) throws InterruptedException {
        db.collection("users").document(Options.uId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            long flagStatus = documentSnapshot.getLong(PollsList.pollsOption+"cand");
                            if (flagStatus == 0){
                                db.collection("users").document(Options.uId).update(PollsList.pollsOption+"cand", FieldValue.increment(1));
                                Toast.makeText(ContestElection.this, "Thank you!", Toast.LENGTH_SHORT).show();
                                storecand();
                            }
                            else {
                                Toast.makeText(ContestElection.this, "Sorry you have already contested for this position", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
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
                            user.put(sub.getText().toString()+"uId",Options.uId);

                            db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).set(user, SetOptions.merge());
                            db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).update("num", FieldValue.increment(1));

                        }
                        else {
                            System.out.println("Hello");
                        }
                    }
                });
    }
}
