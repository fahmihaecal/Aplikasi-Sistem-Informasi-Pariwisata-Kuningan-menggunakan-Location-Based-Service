package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class UpdateObjekWisata extends AppCompatActivity {
    DatabaseReference reference;
    EditText namaobjek,alamatobjek,deskripsi,latitude,longtitude;
    ImageView imgthumb;
    TextView admin;
    Button simpan,hapus,btn_thumb;

    Uri photo_location;
    Integer photo_max = 1;
    StorageReference storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_objek_wisata);

        namaobjek=findViewById(R.id.namaobjek);
        alamatobjek=findViewById(R.id.alamatobjek);
        deskripsi=findViewById(R.id.deskripsi);
        latitude=findViewById(R.id.latitude);
        longtitude=findViewById(R.id.longtitude);
        admin=findViewById(R.id.admin);

        imgthumb=findViewById(R.id.imgthumb);
        simpan=findViewById(R.id.simpan);
        hapus=findViewById(R.id.hapus);
        btn_thumb=findViewById(R.id.btn_thumb);

        Bundle bundle=getIntent().getExtras();
        String update= bundle.getString("nama_wisata");

        reference=FirebaseDatabase.getInstance().getReference().child("Objek_Wisata").child(update);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.WHITE)
                        .cornerRadiusDp(13)
                        .oval(false)
                        .build();

                namaobjek.setText(snapshot.child("nama_wisata").getValue().toString());
                alamatobjek.setText(snapshot.child("lokasi").getValue().toString());
                deskripsi.setText(snapshot.child("deskripsi").getValue().toString());
                latitude.setText(snapshot.child("latitude").getValue().toString());
                longtitude.setText(snapshot.child("longitude").getValue().toString());

                Picasso.get().load(snapshot.child("url_thumb").getValue().toString()).transform(transformation).centerCrop().fit().into(imgthumb);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {;
                        snapshot.getRef().child("nama_wisata").setValue(namaobjek.getText().toString());
                        snapshot.getRef().child("lokasi").setValue(alamatobjek.getText().toString());
                        snapshot.getRef().child("deskripsi").setValue(deskripsi.getText().toString());
                        snapshot.getRef().child("latitude").setValue(latitude.getText().toString());
                        snapshot.getRef().child("longitude").setValue(longtitude.getText().toString());

                        storage=FirebaseStorage.getInstance().getReference().child("Photoobjek");
                        if (photo_location != null){
                            StorageReference storageReference = storage.child(System.currentTimeMillis()+"."+ getFileExtension(photo_location));
                           storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                   storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           String uri_photo= uri.toString();
                                           reference.getRef().child("url_thumb").setValue(uri_photo);
                                       }
                                   }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {

                                       }
                                   });

                               }
                           });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getApplicationContext(), " Data berhasil di update", Toast.LENGTH_LONG).show();
                Intent go = new Intent(UpdateObjekWisata.this,DashboardAdmin.class);
                go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go);
                finish();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            snapshot1.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getApplicationContext(), " Data berhasil di hapus", Toast.LENGTH_LONG).show();
                Intent go = new Intent(UpdateObjekWisata.this, DashboardAdmin.class);
                go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go);
                finish();
            }
        });


    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==photo_max && resultCode==RESULT_OK && data!=null && data.getData() != null){
            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(imgthumb);
        }
    }
}