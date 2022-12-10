package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fahmi.ekuningan.adapter.Adapter;
import com.fahmi.ekuningan.adapter.ArrayRecomendAdapter;
import com.fahmi.ekuningan.adapter.ObjekWisata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends AppCompatActivity implements LocationListener {

    RecyclerView recomend;
    ArrayList<ObjekWisata> list;
    ArrayRecomendAdapter adapter;
    LinearLayout tour,tour_maps,info;
    Double curentLat,curentLng;

    DatabaseReference reference;


    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recomend = findViewById(R.id.recomend);
        list= new ArrayList<ObjekWisata>();

        ImageView banner=findViewById(R.id.banner);

        tour_maps=findViewById(R.id.tour_maps);
        tour=findViewById(R.id.tour);
        info=findViewById(R.id.info);

       locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go= new Intent(Menu.this,Tour.class);
                startActivity(go);
            }
        });

        tour_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(Menu.this, MapsActivity.class);
                startActivity(go);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                builder.setIcon(R.drawable.btn_white).setTitle("Info Aplikasi").setMessage("Aplikasi ini dibuat guna untuk membantu wisatawan dan juga masyarakat dalam mencari informasi serta lokasi Objek Wisata yang ada di Kabupaten Kuningan. Muhammad Fahmi Haecal 20180910056").setCancelable(false).setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });



        reference= FirebaseDatabase.getInstance().getReference().child("Objek_Wisata");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    ObjekWisata p= snapshot1.getValue(ObjekWisata.class);
                    list.add(0, p);
                }

                recomend.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                adapter=new ArrayRecomendAdapter(Menu.this, list);
                recomend.setAdapter(adapter);
                //SpacesItemDecoration decoration= new SpacesItemDecoration(16);
                //recyclerView.addItemDecoration(decoration);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
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



    @Override
    public void onLocationChanged(@NonNull Location location) {
        curentLat = location.getLatitude();
        curentLng =location.getLongitude();
    }
}