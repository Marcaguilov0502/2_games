package com.marcapps.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnPegSolitaire = (Button) findViewById(R.id.btnPegSolitaire);
        btnPegSolitaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setPegActivity = new Intent(getApplicationContext(), PegSolitaire_activity.class);
                startActivity(setPegActivity);
            }
        });

        Button btn2048 = (Button) findViewById(R.id.btn2048);
        btn2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent set2048Activity = new Intent(getApplicationContext(), Activity2048.class);
                startActivity(set2048Activity);
            }
        });







    }

    public void splash(View v) {
        Intent wa = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(wa);
    }

}