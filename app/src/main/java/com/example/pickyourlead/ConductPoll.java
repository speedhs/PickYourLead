package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConductPoll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conduct_poll);
    }

    public void activePoll(View view){
        Intent next = new Intent(this, PollsList.class);
        startActivity(next);
    }
}