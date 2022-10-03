package com.dinithi_creation.bustracking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class selects_Activity extends AppCompatActivity {

    private Button driver,passenger;
    private FirebaseAuth firebaseAuth;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdriver);

        firebaseAuth = FirebaseAuth.getInstance();

        passenger = findViewById(R.id.selectbtnpassenger);
        driver = findViewById(R.id.selectbtndriver);

        passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selects_Activity.this, Register_Activity.class);
                startActivity(intent);

            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selects_Activity.this, driver_register.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(selects_Activity.this, Driver_Home_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}