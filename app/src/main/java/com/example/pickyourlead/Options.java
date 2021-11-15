package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void activePoll(View view) {
        System.out.println("blah blah");
        Home.nextpage= "Vote";
        Intent next = new Intent(this, PollsList.class);
        startActivity(next);
    }

    public void viewResults(View view){
        Home.nextpage= "Results";
        Intent next = new Intent(this, PollsList.class);
        startActivity(next);
    }
}