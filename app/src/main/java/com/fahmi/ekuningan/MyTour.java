package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fahmi.ekuningan.adapter.ViewPagerAdapter;
import com.fahmi.ekuningan.fragment.FragmentDesc;
import com.fahmi.ekuningan.fragment.FragmentMaps;
import com.fahmi.ekuningan.fragment.FragmentPhoto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyTour extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton map;

    ImageView header;
    TextView nama_objek, alamat,desc;
    ViewPagerAdapter viewPagerAdapter;
    Button rute;


    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tour);


        header = findViewById(R.id.header);
        nama_objek = findViewById(R.id.nama_objek);
        alamat = findViewById(R.id.alamat);
        desc=findViewById(R.id.desc);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
//        rute=findViewById(R.id.rute);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Bundle bundle = getIntent().getExtras();
        String objek_wisata = bundle.getString("nama_wisata");
        reference = FirebaseDatabase.getInstance().getReference().child("Objek_Wisata").child(objek_wisata);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama_objek.setText(snapshot.child("nama_wisata").getValue().toString());
                alamat.setText(snapshot.child("lokasi").getValue().toString());
                Picasso.get().load(snapshot.child("url_thumb").getValue().toString()).centerCrop().fit().into(header);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        rute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String nama_wisata=nama_objek.getText().toString();
//                Intent go= new Intent(MyTour.this, MapsRoute.class);
//                go.putExtra("nama_wisata",nama_wisata);
//                startActivity(go);
//                finish();
//            }
//        });

    }

}