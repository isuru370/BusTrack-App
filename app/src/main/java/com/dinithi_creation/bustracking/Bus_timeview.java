package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.dinithi_creation.Adapter.passengerview;
import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.driverdetails.Time_table;
import com.dinithi_creation.driverdetails.driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Bus_timeview extends AppCompatActivity {

    private static final String TAG = "Timetable Details";
    RecyclerView recyclerView;
    ArrayList<Time_table> timeTables;
    passengerview passengerAdapter;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    String busNumber;
    ArrayList<String> statusArry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_timeview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.show();

        recyclerView = findViewById(R.id.timeviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        timeTables = new ArrayList<Time_table>();
        passengerAdapter = new passengerview(Bus_timeview.this, timeTables);

        recyclerView.setAdapter(passengerAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {


        firebaseFirestore.collection("BusTimeTable")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.i(TAG, "Firedtore  " + error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                timeTables.add(dc.getDocument().toObject(Time_table.class));

                            }
                            passengerAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Bus Details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot snapshot : task.getResult()){
                            Bus_ditails busDitails = snapshot.toObject(Bus_ditails.class);
                            String status = busDitails.getBusStatus();
                            if(status.trim().equals("1")){
                                 busNumber = busDitails.getBusNumber();
                                 statusArry = new ArrayList<>();
                                 statusArry.add(busNumber);
                            }
                           /* ArrayList<String> statusArray = new ArrayList<>();
                            statusArray.add(status);*/
                        }
                    }
                });
    }
}
