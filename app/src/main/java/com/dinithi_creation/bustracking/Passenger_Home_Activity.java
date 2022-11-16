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

public class Passenger_Home_Activity extends AppCompatActivity {

    private Button logOut;
    ImageView map, profile,busTimeTable,busFare,passemargency;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String vui;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_home);

        map = findViewById(R.id.mapBtn);
        profile = findViewById(R.id.passengerProfileImgBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        logOut = findViewById(R.id.logout);
        busTimeTable = findViewById(R.id.imageView7);
        busFare = findViewById(R.id.imageView8);
        passemargency = findViewById(R.id.imageView6);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Passenger_Home_Activity.this, PassengerMapsActivity.class);

                startActivity(intent);

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Passenger_Home_Activity.this, PassengerProfile_Activity.class);

                startActivity(intent);

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Passenger_Home_Activity.this, SignIn_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
               fAuth.signOut();

            }
        });
        busTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Passenger_Home_Activity.this, Bus_timeview.class);
                intent.putExtra("one","One");
                startActivity(intent);
            }
        });
        busFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Passenger_Home_Activity.this, pay.class);
                startActivity(intent);
            }
        });
        passemargency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Passenger_Home_Activity.this,Emargency_activity.class));
            }
        });

    }
}