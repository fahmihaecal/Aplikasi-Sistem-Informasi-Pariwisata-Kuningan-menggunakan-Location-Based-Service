package com.fahmi.ekuningan.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahmi.ekuningan.MyTour;
import com.fahmi.ekuningan.R;
import com.fahmi.ekuningan.algo.GPSHandler;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.service.controls.ControlsProviderService.TAG;

public class ArrayRecomendAdapter extends RecyclerView.Adapter<ArrayRecomendAdapter.MyViewHolder> implements LocationListener {

    private static final int METERS_100 = 10000 ;
    Context context;
    ArrayList<ObjekWisata> objekWisata;
    private GPSHandler gpsHandler;
    private double startLat;
    private double startlng;
    private double endlat;
    private double endLng;
    private double distance;
    Double latitude,longitude,lat,Lng;
    LatLng myLoc, semua;
    private final int limit=3;
    DatabaseReference reference;
    ArrayList<LatLng> data;

    public  ArrayRecomendAdapter(Context c, ArrayList<ObjekWisata>p){
        context = c;
        objekWisata = p;
    }

    @NonNull
    @Override
    public ArrayRecomendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tour_recomend, parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(objekWisata.get(position).getUrl_fotorecomend()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            final String getNama_wisata=objekWisata.get(position).getNama_wisata();
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
        if (objekWisata.size() > limit ){
            return limit;

        }else {
            return objekWisata.size();
        }
    }

    private void lokasi(){
        reference= FirebaseDatabase.getInstance().getReference().child("Objek_Wisata");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                      lat= child.child("latitude").getValue(Double.class);
                      Lng= child.child("longitude").getValue(Double.class);
                      semua= new LatLng(Double.valueOf(lat),Double.valueOf(Lng));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public double CalculationByDistance(){
        int R = 6371; // km (Earth radius)
        double dLat = toRadians(latitude-lat);
        double dLon = toRadians(longitude-Lng);
        lat = toRadians(lat);
        latitude = toRadians(latitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat) * Math.cos(latitude);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI/180);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude =location.getLongitude();
         myLoc = new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
        Log.e(TAG, "User Location latitude:"+latitude+", longitude:"+longitude);



    }

    class MyViewHolder extends  RecyclerView.ViewHolder {

        ImageView  imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }
}
