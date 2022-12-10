package com.fahmi.ekuningan.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fahmi.ekuningan.R;
import com.fahmi.ekuningan.adapter.ArrayAdapter;
import com.fahmi.ekuningan.adapter.ObjekWisata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentPhoto extends Fragment {

    RecyclerView recyclerView2;
    DatabaseReference reference;

    ImageView foto1,foto2,foto3,foto4;


    public FragmentPhoto(){
    }


    public static Fragment newInstance(int page, String title) {
        FragmentPhoto fragmentPhoto=new FragmentPhoto();
        Bundle args = new Bundle();
        fragmentPhoto.setArguments(args);
        return fragmentPhoto;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_photo, container, false);
        foto1 =  view.findViewById(R.id.foto1);
        foto2=view.findViewById(R.id.foto2);
        foto3=view.findViewById(R.id.foto3);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String bundle = getActivity().getIntent().getExtras().getString("nama_wisata");
        reference= FirebaseDatabase.getInstance().getReference().child("Objek_Wisata").child(bundle);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.child("url_foto1").getValue().toString()).into(foto1);
                Picasso.get().load(snapshot.child("url_foto2").getValue().toString()).into(foto2);
                Picasso.get().load(snapshot.child("url_foto3").getValue().toString()).into(foto3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
