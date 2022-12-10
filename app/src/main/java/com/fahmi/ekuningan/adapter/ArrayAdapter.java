package com.fahmi.ekuningan.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fahmi.ekuningan.MyTour;
import com.fahmi.ekuningan.R;
import com.fahmi.ekuningan.fragment.FragmentPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ViewHolder> {
    FragmentPhoto context;
    ArrayList<ObjekWisata> objekWisata;
    public ArrayAdapter(FragmentPhoto c, ArrayList<ObjekWisata>p){
        context = c;
        objekWisata= p;
    }

    @NonNull
    @Override
    public ArrayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_tour, parent, false);
        ArrayAdapter.ViewHolder viewHolder = new ArrayAdapter.ViewHolder(view);
        return viewHolder;
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull ArrayAdapter.ViewHolder holder, int position) {
        Picasso.get().load(objekWisata.get(position).getUrl_foto1()).into(holder.foto);
        Picasso.get().load(objekWisata.get(position).getUrl_foto2()).into(holder.foto2);

    }

    @Override
    public int getItemCount() {
        return objekWisata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto,foto2;

        public ViewHolder(View view) {
            super(view);
            foto=itemView.findViewById(R.id.foto);
            foto=itemView.findViewById(R.id.foto2);
        }
    }



}
