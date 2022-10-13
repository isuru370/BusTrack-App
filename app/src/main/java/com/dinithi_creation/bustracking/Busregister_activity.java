package com.dinithi_creation.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.Bus_ditails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Busregister_activity extends AppCompatActivity {

    private EditText busNo,busRootNo,busStart,busEnd,busDistance,busFare;
    private Button busDitailsSubmitBtn;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busregister);


        firestore = FirebaseFirestore.getInstance();


        busNo = findViewById(R.id.busno);
        busRootNo = findViewById(R.id.busrootno);
        busStart = findViewById(R.id.busstartcity);
        busEnd = findViewById(R.id.busendcity);
        busDistance = findViewById(R.id.busdistance);
        busFare = findViewById(R.id.busfare);
        busDitailsSubmitBtn = findViewById(R.id.bussubmitbtn);

        busDitailsSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Datasavefirebases();
            }
        });
    }

    private void Datasavefirebases() {
        String s = busNo.getText().toString();
        String s1 = busRootNo.getText().toString();
        String s2 = busStart.getText().toString();
        String s3 = busEnd.getText().toString();
        String s4 = busDistance.getText().toString();
        String s5 = busFare.getText().toString();
        if(!s.isEmpty() && !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty() && !s4.isEmpty() && !s5.isEmpty()){
            Bus_ditails bus_ditails = new Bus_ditails();
            bus_ditails.setBusNumber(s);
            bus_ditails.setBusRootNumber(s1);
            bus_ditails.setBusStartCity(s2);
            bus_ditails.setBusEndCity(s3);
            bus_ditails.setBusDistance(s4);
            bus_ditails.setBusFare(s5);
            bus_ditails.setBusStatus("0");
            firestore.collection("Bus Details").add(bus_ditails);
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Busregister_activity.this, Driver_Home_Activity.class));
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"TextFeild is Empty",Toast.LENGTH_LONG).show();
        }

    }
}