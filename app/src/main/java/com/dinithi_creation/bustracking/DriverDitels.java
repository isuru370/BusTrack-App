package com.dinithi_creation.bustracking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.dinithi_creation.Adapter.DriverAdapter;
import com.dinithi_creation.driverdetails.driver;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DriverDitels extends AppCompatActivity {

    private static final String TAG = "DriverDitels";
    RecyclerView recyclerView;
    ArrayList<driver> drivers;
    DriverAdapter driverAdapter;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ditels);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.show();

        recyclerView = findViewById(R.id.driverrecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        drivers = new ArrayList<driver>();
        driverAdapter = new DriverAdapter(DriverDitels.this,drivers);

        recyclerView.setAdapter(driverAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {

        firebaseFirestore.collection("Driver")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if(error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.i(TAG,"Firedtore  "+error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){


                                drivers.add(dc.getDocument().toObject(driver.class));

                            }
                            driverAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}