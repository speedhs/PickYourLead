package com.example.pickyourlead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PollsList extends AppCompatActivity {

    static String pollsOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_list);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(PollsList.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.council_elections));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {
                String item = mySpinner.getSelectedItem().toString();
                pollsOption=item;
                if(!(item.equals("CLASS REPRESENTATIVE"))){
                    Register.branch=item;
                    Register.batch="2022";
                }
                else{
                    Register.branch=Register.originalBranch;
                    Register.batch=Register.originalBatch;
                }
                //Toast.makeText(getApplicationContext(), branch , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void navigate(View view){
        if(Home.nextpage.equals("Vote")){
            Intent intent = new Intent(PollsList.this, Vote.class);
            startActivity(intent);
        }
        else if(Home.nextpage.equals("Contest")){
            Intent intent = new Intent(PollsList.this, ContestElection.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(PollsList.this, Results.class);
            startActivity(intent);
        }
    }


}