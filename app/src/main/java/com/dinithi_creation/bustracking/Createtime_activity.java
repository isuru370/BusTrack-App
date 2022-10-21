package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.driverdetails.Time_table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Createtime_activity extends AppCompatActivity {


    private String uid;
    private TextView busid;
    private FirebaseFirestore firestore;
    private Spinner start;
    public static String busNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtime);

        firestore = FirebaseFirestore.getInstance();
        busid = findViewById(R.id.userbusid);
        start = findViewById(R.id.startcityid);




        firestore.collection("Bus Details")
                .whereEqualTo("busId",uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            Bus_ditails busDitails = queryDocumentSnapshot.toObject(Bus_ditails.class);
                            busNumber = busDitails.getBusNumber();
                            Toast.makeText(getApplicationContext(),
                                    "Bus No is "+busNumber,
                                    Toast.LENGTH_LONG).show();
                            busid.setText(busNumber);
                           /* arrayList1.add(busNumber);
                            arrayAdapter = new ArrayAdapter<String>(Createtime_activity.this,
                                    android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            busid.setAdapter(arrayAdapter);*/
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String citystart = adapterView.getItemAtPosition(i).toString();
                if (citystart.trim().equals("Select Start City")) {
                    Toast.makeText(getApplicationContext(),
                            "Please select start city",
                            Toast.LENGTH_LONG).show();
                }else{
                    if (citystart.trim().equals("Mathara")) {
                        replaceFragmant(new CityMathara());
                    } else if (citystart.trim().equals("Colombo")) {
                        replaceFragmant(new CityColombo());
                    } else {

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void replaceFragmant(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.citychange, fragment);
        fragmentTransaction.commit();
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
    }
}