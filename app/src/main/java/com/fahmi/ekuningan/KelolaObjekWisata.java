package com.fahmi.ekuningan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class KelolaObjekWisata extends AppCompatActivity {

    Button simpan, hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_objek_wisata);

        simpan=findViewById(R.id.simpan);
        hapus=findViewById(R.id.hapus);
    }
}