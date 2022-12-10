package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreatNewAccount extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue, next;
    EditText username, password, email;
    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_account);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        next = findViewById(R.id.next);
        LinearLayout kembali= (LinearLayout) findViewById(R.id.kembali);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //loading
                next.setEnabled(false);
                next.setText("Loading..");

                //mengambil username pada firebase
                reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //jika username tersedia
                        if (snapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "Username telah digunakan", Toast.LENGTH_SHORT);
                            next.setEnabled(true);
                            next.setText("CONTINUE");
                        } else {
                            //menyimpan data pada lokal
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, username.getText().toString());
                            editor.apply();

                            //simpan ke database
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().child("username").setValue(username.getText().toString());
                                    snapshot.getRef().child("password").setValue(password.getText().toString());
                                    snapshot.getRef().child("email").setValue(email.getText().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Toast.makeText(getApplicationContext(), "username " + username.getText().toString(), Toast.LENGTH_SHORT).show();

                            Intent go = new Intent(CreatNewAccount.this, CreatNewAccountTwo.class);
                            startActivity(go);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(CreatNewAccount.this, GetStarted.class);
                startActivity(back);
                finish();
            }
        });
    }
}
