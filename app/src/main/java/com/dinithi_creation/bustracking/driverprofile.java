package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class driverprofile extends AppCompatActivity {

    ShapeableImageView proimageid;
    ImageView licence1id,licence2id;
    TextView name,email,phoneno,licence,nic,homeadd;
    Button subbtn;
    SwitchCompat status;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    String drivernic_id,driverstatus,id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverprofile);

        drivernic_id = getIntent().getStringExtra("drivernic_id");

        proimageid = findViewById(R.id.proimgid);
        licence1id =findViewById(R.id.prolincence1id);
        licence2id = findViewById(R.id.prolincence2id);

        name = findViewById(R.id.pronameid);
        email = findViewById(R.id.proemaiid);
        phoneno = findViewById(R.id.prophonenoid);
        licence = findViewById(R.id.prolicenceid);
        nic = findViewById(R.id.pronicid);
        homeadd = findViewById(R.id.prohomeaddid);
        status = findViewById(R.id.prostatusid);
        subbtn = findViewById(R.id.proacseptbtnid);

        firebaseStorage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Driver").whereEqualTo("drivernicno", drivernic_id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot result = task.getResult();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            id = queryDocumentSnapshot.getId();
                            driver objectDriver = queryDocumentSnapshot.toObject(driver.class);
                            String driverproimg = objectDriver.getDriverproimg();
                            String driverlicenesphoto1 = objectDriver.getDriverlicenesphoto1();
                            String driverlicenesphoto2 = objectDriver.getDriverlicenesphoto2();
                            firebaseStorage.getReference("Appimage/driverproimage/"+driverproimg).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(proimageid);
                                        }
                                    });
                            firebaseStorage.getReference("Appimage/driver licences/in/"+driverlicenesphoto1).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(licence1id);
                                        }
                                    });
                            firebaseStorage.getReference("Appimage/driver licences/out/"+driverlicenesphoto2).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(licence2id);
                                        }
                                    });
                            name.setText(objectDriver.getDriverfullname());
                            email.setText("EMAIL :- "+objectDriver.getDriveremail());
                            phoneno.setText("PHONE NO :- "+objectDriver.getDriverphoneno());
                            licence.setText("LICENCE NO :- "+objectDriver.getDriverlicenes());
                            nic.setText("NIC NO :- "+objectDriver.getDrivernicno());
                            homeadd.setText("HOME ADDRESS :- "+objectDriver.getDriverhomeaddress());
                            driverstatus = objectDriver.getDriverstatus();
                            statusChack(driverstatus);
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.isChecked()){
                    firestore.collection("Driver").document(id)
                            .update("driverstatus","ACTIVE")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Driver is activeted",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }else{
                    firestore.collection("Driver").document(id)
                            .update("driverstatus","INACTIVE")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Driver is inactiveted",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        });

    }

    private void statusChack(String driverstatus) {
        if(driverstatus.equals("INACTIVE")){
            status.setChecked(false);
        }else{
            status.setChecked(true);
        }
    }


}