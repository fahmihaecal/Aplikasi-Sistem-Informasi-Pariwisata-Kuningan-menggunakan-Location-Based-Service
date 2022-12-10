package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fahmi.ekuningan.algo.GPSHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TambahObjekWisata extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    Button btn_img1, btn_img2, btn_img3, btn_thumb, simpan;
    EditText namaobjek, alamatobjek, deskripsi, latitude, longtitude;
    ImageView img1, img2, img3, imgthumb;

    DatabaseReference reference, reference_objek, reference2;
    StorageReference storage;
    MapView mMapView;
    TextView longla;

    Uri photo_location, photo_location2, photo_location3, photo_location4;
    Integer photo_max = 1;
    Integer photo_max2 = 2;
    Integer photo_max3 = 3;
    Integer photo_max4 = 4;

    private GoogleMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private LatLng mOrigin;
    String OBJEK_KEY = "objekkey";
    String objek_key = "";

    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_objek_wisata);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btn_img1 = findViewById(R.id.btn_img1);
        btn_img2 = findViewById(R.id.btn_img2);
        btn_img3 = findViewById(R.id.btn_img3);
        btn_thumb = findViewById(R.id.btn_thumb);
        simpan = findViewById(R.id.simpan);
        namaobjek = findViewById(R.id.namaobjek);
        alamatobjek = findViewById(R.id.alamatobjek);
        deskripsi = findViewById(R.id.deskripsi);
        latitude = findViewById(R.id.latitude);
        longtitude = findViewById(R.id.longtitude);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        imgthumb = findViewById(R.id.imgthumb);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(TambahObjekWisata.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(TambahObjekWisata.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        btn_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, photo_max);
            }
        });

        btn_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, photo_max2);
            }
        });
        btn_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, photo_max3);
            }
        });
        btn_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, photo_max4);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan.setEnabled(false);
                simpan.setText("Loading....");

                //mengambil nama objek pada firebase
                reference_objek = FirebaseDatabase.getInstance().getReference().child("Objek_Wisata").child(namaobjek.getText().toString());
                reference_objek.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "nama objek sudah ada", Toast.LENGTH_LONG);
                            simpan.setEnabled(true);
                            simpan.setText("SIMPAN");
                        } else {
                            SharedPreferences sharedPreferences = getSharedPreferences(OBJEK_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(objek_key, namaobjek.getText().toString());
                            editor.apply();

                            reference = FirebaseDatabase.getInstance().getReference().child("Objek_Wisata").child(namaobjek.getText().toString());
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().child("nama_wisata").setValue(namaobjek.getText().toString());
                                    snapshot.getRef().child("lokasi").setValue(alamatobjek.getText().toString());
                                    snapshot.getRef().child("deskripsi").setValue(deskripsi.getText().toString());
                                    snapshot.getRef().child("latitude").setValue(latitude.getText().toString());
                                    snapshot.getRef().child("longitude").setValue(longtitude.getText().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            storage = FirebaseStorage.getInstance().getReference().child("Photoobjek");
                            if (photo_location != null) {
                                StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                                storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uri_photo = uri.toString();
                                                reference.getRef().child("url_foto1").setValue(uri_photo);
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {

                                            }
                                        });
                                    }
                                });
                            }
                            if (photo_location != null) {
                                StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location2));
                                storageReference.putFile(photo_location2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uri_photo = uri.toString();
                                                reference.getRef().child("url_foto2").setValue(uri_photo);
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {

                                            }
                                        });
                                    }
                                });
                            }
                            if (photo_location != null) {
                                StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location3));
                                storageReference.putFile(photo_location3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uri_photo = uri.toString();
                                                reference.getRef().child("url_foto3").setValue(uri_photo);
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {

                                            }
                                        });
                                    }
                                });
                            }
                            if (photo_location != null) {
                                StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location4));
                                storageReference.putFile(photo_location4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uri_photo = uri.toString();
                                                reference.getRef().child("url_thumb").setValue(uri_photo);
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {

                                            }
                                        });
                                    }
                                });
                            }

                            Toast.makeText(getApplicationContext(), "nama_wisata " + namaobjek.getText().toString(), Toast.LENGTH_SHORT).show();

                            //pindah activity
                            Intent gotocontinue = new Intent(TambahObjekWisata.this, DashboardAdmin.class);
                            startActivity(gotocontinue);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(TambahObjekWisata.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        latitude.setText(Html.fromHtml("<font color='#6200EE'></font>"
                                + addresses.get(0).getLatitude()));

                        longtitude.setText(Html.fromHtml(""
                                + addresses.get(0).getLongitude()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
    }

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(img1);
        }

        if (requestCode == photo_max2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location2 = data.getData();
            Picasso.get().load(photo_location2).centerCrop().fit().into(img2);
        }

        if (requestCode == photo_max3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location3 = data.getData();
            Picasso.get().load(photo_location3).centerCrop().fit().into(img3);
        }

        if (requestCode == photo_max4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location4 = data.getData();
            Picasso.get().load(photo_location4).centerCrop().fit().into(imgthumb);
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),12.0f));
    }
}