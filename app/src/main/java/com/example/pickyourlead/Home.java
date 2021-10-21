package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {


    public void login(View view) {//moving to next screen
        System.out.println("suc");
        Intent next = new Intent(this, Login.class);
        startActivity(next);
    }
    public void register(View view) {//moving to next screen
        System.out.println("su");
        Intent next = new Intent(this, Register.class);
        startActivity(next);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}