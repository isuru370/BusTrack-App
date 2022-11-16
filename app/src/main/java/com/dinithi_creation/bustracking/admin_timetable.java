package com.dinithi_creation.bustracking;

import static com.dinithi_creation.bustracking.Register_Activity.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.dinithi_creation.Adapter.Admintimetable;
import com.dinithi_creation.Adapter.Bus_fare;
import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.driverdetails.Time_table;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class admin_timetable extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Time_table> timeTableArrayList;
    Admintimetable timeTable;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_timetable);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewadmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        timeTableArrayList = new ArrayList<Time_table>();
        timeTable = new Admintimetable(admin_timetable.this, timeTableArrayList);

        recyclerView.setAdapter(timeTable);

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

                                timeTableArrayList.add(dc.getDocument().toObject(Time_table.class));

                            }
                            timeTable.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}
