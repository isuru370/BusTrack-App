package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dinithi_creation.driverdetails.Time_table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Admin_bus_timetable_seen extends AppCompatActivity {

    private TextView tId1,tId2,tId3,tId4;
    private Button btnnext;
    private FirebaseFirestore firestore;
    String busNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bus_timetable_seen);

        busNo2 = getIntent().getStringExtra("busNo2");
        firestore = FirebaseFirestore.getInstance();

        tId1 =findViewById(R.id.id1);
        tId2 =findViewById(R.id.id2);
        tId3 =findViewById(R.id.id3);
        tId4 =findViewById(R.id.id4);
        btnnext = findViewById(R.id.idbtn);

        firestore.collection("BusTimeTable").whereEqualTo("busNumber",busNo2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            Time_table table = snapshot.toObject(Time_table.class);
                            tId1.setText(table.getStartcity());
                            tId2.setText(table.getEndCity());
                            tId3.setText(table.getStartTime());
                            tId4.setText(table.getEndTime());
                        }
                    }
                });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_bus_timetable_seen.this,DriverDitels.class));
            }
        });
    }
}