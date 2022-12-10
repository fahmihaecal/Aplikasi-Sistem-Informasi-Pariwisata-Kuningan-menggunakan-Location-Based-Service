package com.fahmi.ekuningan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button signin;

        Button creatnewaccount= (Button) findViewById(R.id.creatnewaccount);

        signin= findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gosignin= new Intent(GetStarted.this, SignIn.class);
                startActivity(gosignin);
            }
        });

        creatnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(GetStarted.this, Menu.class);
                startActivity(go);
                finish();
            }
        });
    }
}