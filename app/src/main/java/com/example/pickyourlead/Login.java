package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    public void options_page(View view) {//moving to next screen
        System.out.println("suc");
        Intent next = new Intent(this, ConductPoll.class);
        startActivity(next);
    }

    public void print(View view) {
        System.out.println("hi");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

}