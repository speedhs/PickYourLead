package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class LostConnection extends AppCompatActivity {
    ImageView gif1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_connection);
        gif1 = findViewById(R.id.imageView2);
        Glide.with(this).load(R.drawable.peasnointernet).into(gif1);
    }


    public void lastPage(View view){
        boolean internet=isConnected();
        if (internet) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Firse check kar",Toast.LENGTH_LONG).show();
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
}