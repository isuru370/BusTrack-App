package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.Bus_ditails;
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

    private TextView startTimeId, endTimeId;
    private int t1Hours, t1Minute;
    private String format;
    private Spinner start, end,busid;
    private FirebaseFirestore firestore;
    private String uid;
    private ArrayList<String> arrayList1;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtime);

        firestore = FirebaseFirestore.getInstance();

        startTimeId = findViewById(R.id.starttime);
        endTimeId = findViewById(R.id.endtime);
        start = findViewById(R.id.startcityid);
        end = findViewById(R.id.endcityid);
        busid = findViewById(R.id.userbusid);


        firestore.collection("Bus Details")
                .whereEqualTo("busId",uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            Bus_ditails busDitails = queryDocumentSnapshot.toObject(Bus_ditails.class);
                            String busNumber = busDitails.getBusNumber();
                            arrayList1.add(busNumber);
                            arrayAdapter = new ArrayAdapter<String>(Createtime_activity.this,
                                    android.R.layout.simple_spinner_dropdown_item, arrayList1);
                            busid.setAdapter(arrayAdapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        startTimeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeSelect();
//                startTimeId.setText(format);

            }
        });

        endTimeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeSelect();
//                endTimeId.setText(format);

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


    private void startTimeSelect() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(Createtime_activity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t1Hours = hourOfDay;
                        t1Minute = minute;

                        String time = t1Hours + ":" + t1Minute;

                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm:aa");
//                            format = f12Hours.format(date);
                            startTimeId.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void endTimeSelect() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(Createtime_activity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t1Hours = hourOfDay;
                        t1Minute = minute;

                        String time = t1Hours + ":" + t1Minute;

                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm:aa");
//                            format = f12Hours.format(date);
                            endTimeId.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
    }
}