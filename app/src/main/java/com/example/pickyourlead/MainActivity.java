package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.Object;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void print(View view) {
        System.out.println("hi");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}