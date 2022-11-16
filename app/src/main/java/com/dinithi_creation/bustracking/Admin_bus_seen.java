package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dinithi_creation.driverdetails.Bus_ditails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Admin_bus_seen extends AppCompatActivity {

    private TextView busNo02,rootNo02,sCity02,endC02,km02,mCoust02,price02,busStatus02;
    private Button nextTimeTable;
    private FirebaseFirestore firestore;
    private String busNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bus_seen);

        firestore = FirebaseFirestore.getInstance();
        busNo = getIntent().getStringExtra("busNo");

        busNo02=findViewById(R.id.busno2);
        rootNo02=findViewById(R.id.rootno2);
        sCity02=findViewById(R.id.citys2);
        endC02=findViewById(R.id.citye2);
        km02=findViewById(R.id.km2);
        mCoust02=findViewById(R.id.mcost2);
        price02=findViewById(R.id.fare2);
        busStatus02=findViewById(R.id.busstatus02);
        nextTimeTable = findViewById(R.id.nexttimetable02);

        firestore.collection("Bus Details").whereEqualTo("busNumber",busNo)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       for (QueryDocumentSnapshot snapshot : task.getResult()){
                           Bus_ditails ditails = snapshot.toObject(Bus_ditails.class);
                           busNo02.setText(ditails.getBusNumber());
                           rootNo02.setText("ROOT NO : "+ditails.getBusRootNumber());
                           sCity02.setText("START CITY : "+ditails.getBusStartCity().toUpperCase());
                           endC02.setText("END CITY : "+ditails.getBusEndCity().toUpperCase());
                           km02.setText("DISTANCE : "+ditails.getBusDistance().toUpperCase());
                           mCoust02.setText("MINIMUM LKR : 35.00");
                           price02.setText("FARE : "+ditails.getBusFare());
                           String busStatus = ditails.getBusStatus();
                           if(busStatus.trim().equals("1")){
                               busStatus02.setText("Active");
                           }else{
                               busStatus02.setText("Cancel");
                           }
                       }
                    }
                });

        nextTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_bus_seen.this,Admin_bus_timetable_seen.class)
                        .putExtra("busNo2",busNo));
            }
        });

    }
}