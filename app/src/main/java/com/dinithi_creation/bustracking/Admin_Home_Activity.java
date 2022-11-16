package com.dinithi_creation.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class Admin_Home_Activity extends AppCompatActivity {

    private Button logOut;
    ImageView driverditalis,emagence,adminTimeTable,passengerProfileImageBtn,adminBusDetails;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String vui;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        logOut = findViewById(R.id.logout);
        driverditalis = findViewById(R.id.imageView9);
        emagence = findViewById(R.id.imageView6);
        adminTimeTable = findViewById(R.id.imageView7);
        passengerProfileImageBtn = findViewById(R.id.passengerProfileImgBtn);
        adminBusDetails = findViewById(R.id.imageView8);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Admin_Home_Activity.this, SignIn_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                fAuth.signOut();

            }
        });
        driverditalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home_Activity.this,DriverDitels.class));
            }
        });
        emagence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home_Activity.this,Emargency_activity.class));
            }
        });
        adminTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home_Activity.this,admin_timetable.class));
            }
        });
        passengerProfileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home_Activity.this,Adminuserdetails.class));
            }
        });
        adminBusDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home_Activity.this,Bus_ditails_admin.class));
            }
        });
    }
}