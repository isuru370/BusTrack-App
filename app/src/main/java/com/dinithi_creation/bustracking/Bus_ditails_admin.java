package com.dinithi_creation.bustracking;

import static com.dinithi_creation.bustracking.Register_Activity.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.dinithi_creation.Adapter.Adminbusdetails;
import com.dinithi_creation.Adapter.Bus_fare;
import com.dinithi_creation.driverdetails.Bus_ditails;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Bus_ditails_admin extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Bus_ditails> busDitailsArrayList;
    Adminbusdetails fareAdapter;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ditails_admin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewbusAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        busDitailsArrayList = new ArrayList<Bus_ditails>();
        fareAdapter = new Adminbusdetails(Bus_ditails_admin.this, busDitailsArrayList);

        recyclerView.setAdapter(fareAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {
        firebaseFirestore.collection("Bus Details")
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

                                busDitailsArrayList.add(dc.getDocument().toObject(Bus_ditails.class));

                            }
                            fareAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}