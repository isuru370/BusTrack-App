package com.dinithi_creation.bustracking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dinithi_creation.CircleTransform;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btnimg1, btnimg2;
    private ShapeableImageView licimg1, licimg2;
    private FirebaseStorage storage;
    private int x;
    private Uri imageUrl1, imageUrl2;
    private String rendomId1, rendomId2, documentid;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = storage.getReference();

        Intent i = getIntent();
        String image1 = i.getStringExtra("image1");
        String image2 = i.getStringExtra("image2");
        documentid = i.getStringExtra("docid");


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
        storageReference2.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(licimg2);
                    }
                });

        btnimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                x = 2;
                activityResultLauncher.launch(intent);
            }
        });

        btnimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                x = 3;
                activityResultLauncher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        assert result.getData() != null;
                        if (x == 2) {
                            imageUrl1 = result.getData().getData();
                            Picasso.get()
                                    .load(imageUrl1)
                                    .transform(new CircleTransform())
                                    .centerCrop()
                                    .resize(150, 150)
                                    .into(licimg1);
                            saveprofileimage1();
                        }
                        if (x == 3) {
                            imageUrl2 = result.getData().getData();
                            Picasso.get()
                                    .load(imageUrl2)
                                    .transform(new CircleTransform())
                                    .centerCrop()
                                    .resize(150, 150)
                                    .into(licimg2);
                            saveprofileimage2();
                        }
                    }
                }
            }
    );

    private void saveprofileimage1() {
        rendomId1 = UUID.randomUUID().toString();
        StorageReference dirproimage = storageReference.child("Appimage").child("driver licences").child("in/" + rendomId1);
        dirproimage.putFile(imageUrl1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        savedatabases();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void savedatabases() {
        firestore.collection("Driver")
                .document(documentid)
                .update("driverlicenesphoto1", rendomId1);
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
    }

    private void saveprofileimage2() {
        rendomId2 = UUID.randomUUID().toString();
        StorageReference dirproimage = storageReference.child("Appimage").child("driver licences").child("out/" + rendomId2);
        dirproimage.putFile(imageUrl2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        savedatabases2();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void savedatabases2() {
        firestore.collection("Driver")
                .document(documentid)
                .update("driverlicenesphoto2", rendomId2);
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
    }

}