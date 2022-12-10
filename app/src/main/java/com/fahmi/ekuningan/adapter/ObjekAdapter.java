package com.fahmi.ekuningan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahmi.ekuningan.R;
import com.fahmi.ekuningan.TambahObjekWisata;
import com.fahmi.ekuningan.UpdateObjekWisata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class ObjekAdapter extends RecyclerView.Adapter<ObjekAdapter.MyViewHolder> {

    Context context;
    ArrayList<ObjekWisata> objekWisata;
    public ObjekAdapter(Context c, ArrayList<ObjekWisata>p) {
        context = c;
        objekWisata = p;
    }
    @NonNull
    @Override
    public ObjekAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_objek, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .cornerRadiusDp(13)
                .oval(false)
                .build();

        holder.xnama_wisata.setText(objekWisata.get(position).getNama_wisata());
        holder.xlokasi.setText(objekWisata.get(position).getLokasi());
        Picasso.get().load(objekWisata.get(position).getUrl_thumb()).fit().transform(transformation).into(holder.xfoto);

        final String getNamaWisata=objekWisata.get(position).getNama_wisata();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go= new Intent(context, UpdateObjekWisata.class);
                go.putExtra("nama_wisata", getNamaWisata);
                context.startActivity(go);
            }
        });

    }

    @Override
    public int getItemCount() {
        return objekWisata.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView xnama_wisata,xlokasi;
        ImageView xfoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata=itemView.findViewById(R.id.xnama_wisata);
            xlokasi=itemView.findViewById(R.id.xlokasi);
            xfoto=itemView.findViewById(R.id.xfoto);

        }
    }

}
