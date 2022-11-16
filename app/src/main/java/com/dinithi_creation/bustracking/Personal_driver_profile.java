package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Personal_driver_profile extends AppCompatActivity {

    private TextView fullNamePro, statusDri;
    private EditText proEmail, proPhoneNo, proLicenceNo, proNicNo, proHomeAdd;
    private FirebaseFirestore firestore;
    private Button btnUpdate, btnlicenceimg, timeAddedBtn, changePasswordbtn, editSaveBtn;
    private String uid;
    private ShapeableImageView proimagedri;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private ImageView showTextId;
    private String diverlicenceimg1, diverlicenceimg2, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_driver_profile);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        fullNamePro = findViewById(R.id.profullname);
        proEmail = findViewById(R.id.proemaiid);
        proPhoneNo = findViewById(R.id.prophonenoid);
        proLicenceNo = findViewById(R.id.prolicenceid);
        proNicNo = findViewById(R.id.pronicid);
        proHomeAdd = findViewById(R.id.prohomeaddid);
        proimagedri = findViewById(R.id.prodriverimageid);
        btnUpdate = findViewById(R.id.updatebtnproid);
        showTextId = findViewById(R.id.showtextid);
        statusDri = findViewById(R.id.statusdei);
        timeAddedBtn = findViewById(R.id.time_addedbtn);
        btnlicenceimg = findViewById(R.id.licenceimgbtn);
        changePasswordbtn = findViewById(R.id.changepassword);
        editSaveBtn = findViewById(R.id.updatebtnproid);

        fullNamePro.setEnabled(false);
        proEmail.setEnabled(false);
        proPhoneNo.setEnabled(false);
        proLicenceNo.setEnabled(false);
        proNicNo.setEnabled(false);
        proHomeAdd.setEnabled(false);
        btnUpdate.setEnabled(false);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        Toast.makeText(getApplicationContext(), "Id " + uid, Toast.LENGTH_LONG).show();


        firestore.collection("Driver").whereEqualTo("driverid", uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            id = snapshot.getId();
                            driver toObjectDriver = snapshot.toObject(driver.class);
                            fullNamePro.setText(toObjectDriver.getDriverfullname());
                            proEmail.setText(toObjectDriver.getDriveremail());
                            proPhoneNo.setText(toObjectDriver.getDriverphoneno());
                            proLicenceNo.setText(toObjectDriver.getDriverlicenes());
                            proNicNo.setText(toObjectDriver.getDrivernicno());
                            proHomeAdd.setText(toObjectDriver.getDriverhomeaddress());
                            statusDri.setText(toObjectDriver.getDriverstatus());
                            String driverproimg = toObjectDriver.getDriverproimg();
                            diverlicenceimg1 = toObjectDriver.getDriverlicenesphoto1();
                            diverlicenceimg2 = toObjectDriver.getDriverlicenesphoto2();

                            StorageReference storageReference = storage.getReference("Appimage/driverproimage/" + driverproimg);
                            storageReference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(proimagedri);
                                        }
                                    });
                        }
                    }
                });
        showTextId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullNamePro.setEnabled(true);
                proPhoneNo.setEnabled(true);
                proLicenceNo.setEnabled(true);
                proNicNo.setEnabled(true);
                proHomeAdd.setEnabled(true);
                btnUpdate.setEnabled(true);
            }
        });
        timeAddedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Personal_driver_profile.this, Createtime_activity.class));

            }
        });
        btnlicenceimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Personal_driver_profile.this, MainActivity.class);
                intent.putExtra("image1", diverlicenceimg1);
                intent.putExtra("image2", diverlicenceimg2);
                startActivity(intent);
            }
        });
        changePasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Personal_driver_profile.this, password_change.class));
            }
        });
        editSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("Driver").document(id)
                        .update("driverphoneno", proPhoneNo.getText().toString()
                                , "driverlicenes", proLicenceNo.getText().toString()
                                , "drivernicno", proNicNo.getText().toString()
                                , "driverhomeaddress", proHomeAdd.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Edit Success", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}