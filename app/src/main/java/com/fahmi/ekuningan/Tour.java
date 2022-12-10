package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.fahmi.ekuningan.adapter.Adapter;
import com.fahmi.ekuningan.adapter.ObjekAdapter;
import com.fahmi.ekuningan.adapter.ObjekWisata;
import com.fahmi.ekuningan.item.SpacesItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tour extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key="";
    String username_key_new="";

    ArrayList<ObjekWisata> list;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        getUsernameLocal();

        recyclerView = findViewById(R.id.recyclerView);
        list= new ArrayList<ObjekWisata>();

        reference= FirebaseDatabase.getInstance().getReference().child("Objek_Wisata");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    ObjekWisata p= snapshot1.getValue(ObjekWisata.class);
                    list.add(p);
                }

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter=new Adapter(Tour.this, list);
                recyclerView.setAdapter(adapter);
                //SpacesItemDecoration decoration= new SpacesItemDecoration(16);
                //recyclerView.addItemDecoration(decoration);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}