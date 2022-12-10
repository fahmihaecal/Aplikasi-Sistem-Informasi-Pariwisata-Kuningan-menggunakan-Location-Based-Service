package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fahmi.ekuningan.adapter.ObjekAdapter;
import com.fahmi.ekuningan.adapter.ObjekWisata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DashboardAdmin extends AppCompatActivity {
    Button tambahobjek;
    RecyclerView listobjek;
    SwipeRefreshLayout swipe;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key="";
    String username_key_new="";

    ArrayList<ObjekWisata> list;
    ObjekAdapter objekAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        getUsernameLocal();

        tambahobjek= findViewById(R.id.tambahobjek);

        listobjek= findViewById(R.id.listobjek);
        listobjek.setLayoutManager(new LinearLayoutManager(this));
        listobjek.setHasFixedSize(true);
        list= new ArrayList<ObjekWisata>();

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });

        listobjek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gokelola = new Intent(DashboardAdmin.this, KelolaObjekWisata.class);
                startActivity(gokelola);
            }
        });

        tambahobjek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goobjek = new Intent(DashboardAdmin.this, TambahObjekWisata.class);
                startActivity(goobjek);
            }
        });

        reference=FirebaseDatabase.getInstance().getReference().child("Objek_Wisata");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    ObjekWisata p= snapshot1.getValue(ObjekWisata.class);
                    list.add(p);
                }

                objekAdapter=new ObjekAdapter(DashboardAdmin.this, list);
                listobjek.setAdapter(objekAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardAdmin.this);
        builder.setIcon(R.drawable.btn_white).setMessage("anda yakin ingin keluar?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}