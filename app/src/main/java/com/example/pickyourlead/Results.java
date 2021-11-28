package com.example.pickyourlead;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class Results extends AppCompatActivity {
    PieChart chart;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String cad0="NA", cad1="NA", cad2="NA";
    long i1=0;
    long i2=0;
    long i3=0;
    long num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        chart= findViewById(R.id.piechart);
        result();
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(Results.this,Options.class));
    }



    public void result() {
        db.collection("trial").document(Register.branch).collection(Register.batch).document(Register.batch).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            TextView t1=findViewById(R.id.Cand0);
                            TextView t2=findViewById(R.id.Cand1);
                            TextView t3=findViewById(R.id.Cand2);

                            TextView c1=findViewById(R.id.c1);
                            TextView c2=findViewById(R.id.c2);
                            TextView c3=findViewById(R.id.c3);

                            TextView p1=findViewById(R.id.p1);
                            TextView p2=findViewById(R.id.p2);
                            TextView p3=findViewById(R.id.p3);

                            cad0=documentSnapshot.getString("0");
                            num=documentSnapshot.getLong("num");
                            if (num > 0) {
                                i1=documentSnapshot.getLong(cad0);
                                chart.addPieSlice(new PieModel(cad0,i1, Color.parseColor("#FFA726")));
                            }
                            if (num > 1) {
                                cad1 = documentSnapshot.getString("1");
                                i2 = documentSnapshot.getLong(cad1);
                                chart.addPieSlice(new PieModel(cad1,i2, Color.parseColor("#66BB6A")));
                            }
                            if (num > 2) {
                                cad2 = documentSnapshot.getString("2");
                                i3 = documentSnapshot.getLong(cad2);
                                chart.addPieSlice(new PieModel(cad2,i3, Color.parseColor("#EF5350")));
                            }
                            double sum=0;
                            sum= i1+i2+i3;
                            double perc1=(i1/sum)*100;
                            double perc2=(i2/sum)*100;
                            double perc3=(i3/sum)*100;
                            t1.setText(cad0);
                            t2.setText(cad1);
                            t3.setText(cad2);

                            c1.setText(cad0);
                            c2.setText(cad1);
                            c3.setText(cad2);
                            String s1=String.format("%.1f",perc1);
                            String s2=String.format("%.1f",perc2);
                            String s3=String.format("%.1f",perc3);
                            p1.setText( s1+"%");
                            p2.setText( s2+"%");
                            p3.setText( s3+"%");

                            chart.startAnimation();
                        }
                        else {
                            chart.addPieSlice(new PieModel("Nothing",5, Color.parseColor("#FFA726")));
                            chart.addPieSlice(new PieModel("Nothing",5, Color.parseColor("#66BB6A")));
                            chart.addPieSlice(new PieModel("Nothing",5, Color.parseColor("#EF5350")));
                            chart.startAnimation();
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
            startActivity(new Intent(Results.this, Home.class));

        }
        return true;
    }
}