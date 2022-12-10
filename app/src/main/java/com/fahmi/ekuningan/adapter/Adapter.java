package com.fahmi.ekuningan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.fahmi.ekuningan.MyTour;
import com.fahmi.ekuningan.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<ObjekWisata> objekWisata;
    public Adapter(Context c, ArrayList<ObjekWisata>p) {
        context = c;
        objekWisata = p;
    }
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tour, parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama_objek.setText(objekWisata.get(position).getNama_wisata());
        Picasso.get().load(objekWisata.get(position).getUrl_thumb()).into(holder.foto);
        final String getNama_wisata=objekWisata.get(position).getNama_wisata();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go= new Intent(context, MyTour.class);
                go.putExtra("nama_wisata", getNama_wisata);
                context.startActivity(go);

            }
        });

    }

    @Override
    public int getItemCount() {
        return objekWisata.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView nama_objek;
        ImageView  foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_objek=itemView.findViewById(R.id.nama_objek);
            foto=itemView.findViewById(R.id.foto);

        }
    }
}
