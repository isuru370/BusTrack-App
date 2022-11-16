package com.dinithi_creation.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private Button btnimg1,btnimg2;
    private ShapeableImageView licimg1,licimg2;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        storage = FirebaseStorage.getInstance();
        Intent i = getIntent();
        String image1 = i.getStringExtra("image1");
        String image2 = i.getStringExtra("image2");


        licimg1 = findViewById(R.id.licimage1);
        licimg2 = findViewById(R.id.licimage2);
        btnimg1 = findViewById(R.id.uploadbtn1);
        btnimg2 = findViewById(R.id.btnupdate2);

        StorageReference storageReference = storage.getReference("Appimage/driver licences/in/" + image1);
        storageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(licimg1);
                    }
                });
        StorageReference storageReference2 = storage.getReference("Appimage/driver licences/out/" + image2);
        storageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(licimg2);
                    }
                });
    }
}