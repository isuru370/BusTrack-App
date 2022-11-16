package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.driverdetails.Time_table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class bus_time_details extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private TextView dBNo,dBRNo,dBSCity,dBECity,dBDistance,dBPrice,dBStatus;
    private TextView bSETime,bT1,bT2,bT3,bT4,bT5,bT6,bT7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time_details);

        String busNumber = getIntent().getStringExtra("busNumber");

        dBNo = findViewById(R.id.dbusid);
        dBRNo = findViewById(R.id.dbusrootno);
        dBSCity = findViewById(R.id.dscityid);
        dBECity = findViewById(R.id.decityid);
        dBDistance = findViewById(R.id.ddid);
        dBPrice = findViewById(R.id.dpricrid);
        dBStatus = findViewById(R.id.dstatusid);

        //time table

        bSETime = findViewById(R.id.dtimeseid);
        bT1 = findViewById(R.id.dtime1);
        bT2 = findViewById(R.id.dtime2);
        bT3 = findViewById(R.id.dtime3);
        bT4 = findViewById(R.id.dtime4);
        bT5 = findViewById(R.id.dtime5);
        bT6 = findViewById(R.id.dtime6);
        bT7 = findViewById(R.id.dtime7);

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Bus Details").whereEqualTo("busNumber",busNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            Bus_ditails ditails = snapshot.toObject(Bus_ditails.class);
                            dBNo.setText(ditails.getBusNumber());
                            dBRNo.setText(ditails.getBusRootNumber());
                            dBSCity.setText(ditails.getBusStartCity());
                            dBECity.setText(ditails.getBusEndCity());
                            dBDistance.setText(ditails.getBusDistance());
                            dBPrice.setText("LKR : "+ditails.getBusFare()+".00");
                            String busStatus = ditails.getBusStatus();
                            if(busStatus.trim().equals("1")){
                                dBStatus.setText("Active");
                            }else{
                                dBStatus.setText("Cancel");
                            }
                        }
                    }
                });

        firestore.collection("BusTimeTable").whereEqualTo("busNumber",busNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            Time_table table = snapshot.toObject(Time_table.class);
                            bSETime.setText(table.getStartTime()+" - "+ table.getEndTime());
                            String startcity = table.getStartcity();
                            if(startcity.trim().equals("Mathara")){
                                bT1.setText(table.getTime01()+" : WELLAWATTA");
                                bT2.setText(table.getTime02()+" : MORATUWA");
                                bT3.setText(table.getTime03()+" : KALUTHARA");
                                bT4.setText(table.getTime04()+" : HIKKADUWA");
                                bT5.setText(table.getTime05()+" : GALLE");
                                bT6.setText(table.getTime06()+" : KOGGALA");
                                bT7.setText(table.getTime07()+" : MIRISSA");

                            }else{
                                bT1.setText(table.getTime01()+" : MIRISSA");
                                bT2.setText(table.getTime02()+" : KOGGALA");
                                bT3.setText(table.getTime03()+" : GALLE");
                                bT4.setText(table.getTime04()+" : HIKKADUWA");
                                bT5.setText(table.getTime05()+" : KALUTHARA");
                                bT6.setText(table.getTime06()+" : MORATUWA");
                                bT7.setText(table.getTime07()+" : WELLAWATTA");
                            }
                        }
                    }
                });
    }
}