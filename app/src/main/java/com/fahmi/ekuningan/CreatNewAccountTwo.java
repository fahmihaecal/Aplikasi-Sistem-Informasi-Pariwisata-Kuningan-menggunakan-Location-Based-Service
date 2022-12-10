package com.fahmi.ekuningan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreatNewAccountTwo extends AppCompatActivity {

    Uri photo_location;
    Integer photo_max = 1;

    String USERNAME_KEY = "usernamekey";
    String username_key="";
    String username_key_new="";

    ImageView btn_add_foto;
    EditText nama,bio;
    Button next;
    LinearLayout back;

    DatabaseReference reference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_account_two);

        getUsernameLocal();

        btn_add_foto= findViewById(R.id.btn_add_foto);
        next= findViewById(R.id.next);
        back= findViewById(R.id.back);
        nama= findViewById(R.id.nama);
        bio =  findViewById(R.id.bio);

        btn_add_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findFoto();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setEnabled(false);
                next.setText("Loading....");

                //menyimpan ke firbase
                reference= FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                storage= FirebaseStorage.getInstance().getReference().child("Photouser").child(username_key_new);

                //validasi apakah ada file
                if (photo_location != null){
                    StorageReference storageReference = storage.child(System.currentTimeMillis()+"."+getFileExtension(photo_location));
                    storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo=uri.toString();
                                    reference.getRef().child("url_Photo_profile").setValue(uri_photo);
                                    reference.getRef().child("nama").setValue(nama.getText().toString());
                                    reference.getRef().child("bio").setValue(bio.getText().toString());

                                    Toast.makeText(getApplicationContext(),"Registrasi berhasil!/\nSilahkan untuk Login",Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Intent go= new Intent(CreatNewAccountTwo.this,SignIn.class);
                                    startActivity(go);
                                    finish();
                                }
                            });
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //pindah acitivty
                            //Intent go = new Intent(CreatNewAccountTwo.this,SignIn.class);
                            //startActivity(go);

                        }
                    });
                }
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void findFoto(){
        Intent pic= new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==photo_max && resultCode==RESULT_OK && data!=null &&data.getData() !=null){
            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(btn_add_foto);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}