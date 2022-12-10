package com.fahmi.ekuningan.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.fahmi.ekuningan.MyTour;
import com.fahmi.ekuningan.R;
import com.fahmi.ekuningan.adapter.ArrayAdapter;
import com.fahmi.ekuningan.adapter.ObjekWisata;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentDesc extends Fragment {


    TextView desc;
    DatabaseReference reference;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    public FragmentDesc(){

    }
    public static  FragmentDesc newInstance(int page, String title){
        FragmentDesc fragmentDesc=new FragmentDesc();
        Bundle args = new Bundle();
        fragmentDesc.setArguments(args);
        return fragmentDesc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_fragment_desc, container, false);
        desc=view.findViewById(R.id.desc);

        // initializing variable for bar chart.
        barChart = view.findViewById(R.id.chart);
        getBarEntries();
        barChart.animateXY(2000,2000);


        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Pengunjung Telaga Biru Cicerem Tahun 2021");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.RED);

        // setting text size
        barDataSet.setValueTextSize(8f);
        barChart.getDescription().setEnabled(false);
        return view;



    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        String desk= bundle.getString("nama_wisata");
        reference=FirebaseDatabase.getInstance().getReference("Objek_Wisata").child(desk);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                desc.setText(snapshot.child("deskripsi").getValue().toString());
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
        ArrayList valueSet1= new ArrayList();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 29109));
        barEntriesArrayList.add(new BarEntry(2f, 12282));
        barEntriesArrayList.add(new BarEntry(3f, 27176));
        barEntriesArrayList.add(new BarEntry(4f, 20253));
        barEntriesArrayList.add(new BarEntry(5f, 52811));
        barEntriesArrayList.add(new BarEntry(6f, 20576));
        barEntriesArrayList.add(new BarEntry(7f, 0));
        barEntriesArrayList.add(new BarEntry(8f, 14513));
        barEntriesArrayList.add(new BarEntry(9f, 31573));
        barEntriesArrayList.add(new BarEntry(10f, 34808));
        barEntriesArrayList.add(new BarEntry(11f, 18202));
        barEntriesArrayList.add(new BarEntry(12f, 19693));
    }


    private ArrayList getXAxisValues() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        xAxis.add("JUL");
        xAxis.add("AGS");
        xAxis.add("SEPT");
        xAxis.add("OKT");
        xAxis.add("NOV");
        xAxis.add("DES");
        return xAxis;
    }

}