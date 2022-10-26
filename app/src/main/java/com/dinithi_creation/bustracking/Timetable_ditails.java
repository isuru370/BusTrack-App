package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.driverdetails.Time_table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Timetable_ditails extends AppCompatActivity {

    private TextView busNumberid, startCityid, sub1, sub2, sub3, sub4, sub5, sub6, sub7, endCityid;
    private String uid;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_ditails);

        firestore = FirebaseFirestore.getInstance();

        busNumberid = findViewById(R.id.timedetailsbusid);
        startCityid = findViewById(R.id.startcitytimeid);
        sub1 = findViewById(R.id.sub1cititimeid);
        sub2 = findViewById(R.id.sub2citytimeid);
        sub3 = findViewById(R.id.sub3citytimeid);
        sub4 = findViewById(R.id.sub4citytimeid);
        sub5 = findViewById(R.id.sub5citytimeid);
        sub6 = findViewById(R.id.sub6citytimeid);
        sub7 = findViewById(R.id.sub7citytimeid);
        endCityid = findViewById(R.id.endcitytimeid);

        firestore.collection("Bus Details").whereEqualTo("busId", uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            Bus_ditails busDitailsTable = snapshot.toObject(Bus_ditails.class);
                            String busNumber = busDitailsTable.getBusNumber();
                            firestore.collection("BusTimeTable").whereEqualTo("busNumber", busNumber)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                                Time_table timetable = snapshot.toObject(Time_table.class);
                                                busNumberid.setText(timetable.getBusNumber());
                                                String startcity = timetable.getStartcity();
                                                if (startcity.trim().equals("Mathara")) {
                                                    startCityid.setText("Mathara " + timetable.getStartTime());
                                                    sub1.setText("Mirissa " + timetable.getTime01());
                                                    sub2.setText("Koggala " + timetable.getTime02());
                                                    sub3.setText("Galle " + timetable.getTime03());
                                                    sub4.setText("Hikkaduwa " + timetable.getTime04());
                                                    sub5.setText("Kaluthara " + timetable.getTime05());
                                                    sub6.setText("Moratuwa " + timetable.getTime06());
                                                    sub7.setText("Wellawatta " + timetable.getTime07());
                                                    endCityid.setText("Colombo " + timetable.getEndTime());

                                                } else if (startcity.equals("Colombo")) {
                                                    startCityid.setText("Colombo " + timetable.getStartTime());
                                                    sub1.setText("Wellawatta " + timetable.getTime01());
                                                    sub2.setText("Moratuwa " + timetable.getTime02());
                                                    sub3.setText("Kaluthara " + timetable.getTime03());
                                                    sub4.setText("Hikkaduwa " + timetable.getTime04());
                                                    sub5.setText("Galle " + timetable.getTime05());
                                                    sub6.setText("Koggala " + timetable.getTime06());
                                                    sub7.setText("Mirissa " + timetable.getTime07());
                                                    endCityid.setText("Mathara " + timetable.getEndTime());
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
    }
}