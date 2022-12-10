package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignIn extends AppCompatActivity {
    TextView creatnewaccount;
    Button login;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        creatnewaccount=findViewById(R.id.creatnewaccount);
        xusername=findViewById(R.id.xusername);
        xpassword=findViewById(R.id.xpassword);
        login=findViewById(R.id.login);


//        creatnewaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gocreat= new Intent(SignIn.this, CreatNewAccount.class);
//                startActivity(gocreat);
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username= xusername.getText().toString();
                final String password= xpassword.getText().toString();
                if (Objects.equals(xusername.getText().toString(),"admin")&&Objects.equals(xpassword.getText().toString(),"admin")){
                    Toast.makeText(getApplicationContext(),"Login Admin Sukses", Toast.LENGTH_SHORT).show();
                    Intent goadmin= new Intent(SignIn.this, DashboardAdmin.class);
                    startActivity(goadmin);
                }else {
                    if (username.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Username Kosong", Toast.LENGTH_SHORT).show();
                        login.setEnabled(true);
                        login.setText("SIGN IN");
                    }else {
                        if (password.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                            login.setEnabled(false);
                            login.setText("Loading....");
                        }else{
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                            login.setEnabled(false);
                            login.setText("Loading....");

                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        String passwordFromFirebase= snapshot.child("password").getValue().toString();
                                        if (password.equals(passwordFromFirebase)){
                                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(username_key, xusername.getText().toString());
                                            editor.apply();

                                            Intent gologin= new Intent(SignIn.this, DashboardAdmin.class);
                                            startActivity(gologin);
                                            finish();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Password salah", Toast.LENGTH_SHORT).show();
                                            login.setEnabled(true);
                                            login.setText("SIGN IN");

                                        }
                                        Toast.makeText(getApplicationContext(), "Username Terdaftar", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Username tidak terdaftar", Toast.LENGTH_SHORT).show();
                                        login.setEnabled(true);
                                        login.setText("SIGN IN");

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                }


            }
        });
    }
}