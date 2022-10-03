package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.dinithi_creation.bustracking.databinding.ActivityPassengerMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PassengerMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityPassengerMapsBinding binding;

    private ScheduledExecutorService scheduler;

    String selectedDestinationStart = "";
    String selectedDestinationEnd = "";
    boolean isActive = false;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Double lat;
    Double log;

    ArrayList<LatLng> destinationLatLong;
    ArrayList<String> townList_end;
    ArrayList<String> townList_start;

    int startPoint=0, endPoint =0;

    Spinner spinner_start, spinner_end;

    Button submit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPassengerMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        scheduler = Executors.newSingleThreadScheduledExecutor();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        busLocation();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(7.8731, 80.7718);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sri Lanka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,7));

    }

    private void busLocation() {

        Log.d("12345","inside busLocation()");
        selectLocation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stLo = spinner_start.getSelectedItem().toString();
                String enLo = spinner_end.getSelectedItem().toString();

                if(selectedDestinationStart.equals("")){
                    Toast.makeText(PassengerMapsActivity.this,"Please Select StartPoint!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDestinationEnd.equals("")){
                    Toast.makeText(PassengerMapsActivity.this,"Please Select EndPoint!",Toast.LENGTH_SHORT).show();
                    return;
                }

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(destinationLatLong.get(startPoint)).title(stLo));
                mMap.addMarker(new MarkerOptions().position(destinationLatLong.get(endPoint)).title(enLo));

                int difer = startPoint-endPoint;

                if(difer>=-4 && difer<=4){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLong.get(startPoint),10));
                }else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLong.get(startPoint),8));
                }
                getDriverLocation();

            }


        });
    }

    private void selectLocation() {

        Log.i("12345", "inside selectionLocation");
        //        editText = findViewById(R.id.Date);
//        editText1= findViewById(R.id.Time);
        spinner_start = findViewById(R.id.spinner_start);
        spinner_end = findViewById(R.id.spinner_end);
        //start = findViewById(R.id.txt_start);
        //end = findViewById(R.id.txt_end);
        submit = findViewById(R.id.submit);

        townList_start = new ArrayList<>();

        townList_start.add("Select Start Location");
        townList_start.add("Colombo");
        townList_start.add("Wellawatte");
        townList_start.add("Moratuwa");
        townList_start.add("Kalutara");
        townList_start.add("Hikkaduwa");
        townList_start.add("Galle");
        townList_start.add("Koggala");
        townList_start.add("Mirissa");
        townList_start.add("Matara");


        spinner_start.setAdapter(new ArrayAdapter<>(PassengerMapsActivity.this, android.R.layout.simple_spinner_dropdown_item,townList_start));

        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestinationStart ="";
                    //Toast.makeText(getApplicationContext(), "Select Start Location",Toast.LENGTH_SHORT).show();
                    //start.setText("");
                }else {
                    startPoint = position;
                    selectedDestinationStart = parent.getItemAtPosition(position).toString();
                    //start.setText(STown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //.................End Location..................

        townList_end = new ArrayList<>();

        townList_end.add("Select End Location");
        townList_end.add("Colombo");
        townList_end.add("Wellawatte");
        townList_end.add("Moratuwa");
        townList_end.add("Kalutara");
        townList_end.add("Hikkaduwa");
        townList_end.add("Galle");
        townList_end.add("Koggala");
        townList_end.add("Mirissa");
        townList_end.add("Matara");

        //LatLong
        //Kirindiwela

        LatLng Kirindiwela = new LatLng( 6.935074734727916, 79.85515642756634);

        //Radawana
        LatLng Radawana = new LatLng(6.874796535502167, 79.86116881962982);

        //Henegama
        LatLng Henegama = new LatLng(6.84875796166515, 79.87136748929741);

        //Waliweriya
        LatLng Waliweriya = new LatLng(6.630549923158772, 79.96475128057374);

        //Nadungamuwa
        LatLng Nadungamuwa = new LatLng(6.212960533248956, 80.10208038539187);

        //Mudungoda
        LatLng Mudungoda = new LatLng(6.043060790740084, 80.2149402690607);

        //Miriswaththa
        LatLng Miriswaththa = new LatLng(5.988122208165549, 80.32899320350595);

        //Gampaha
        LatLng Gampaha = new LatLng(5.950150475483886, 80.45588544292083);

        //Gampaha
        LatLng Gampaha1 = new LatLng(5.945118161020283, 80.5492704548124);

        destinationLatLong = new ArrayList<>();

        destinationLatLong.add(Kirindiwela);
        destinationLatLong.add(Kirindiwela);
        destinationLatLong.add(Radawana);
        destinationLatLong.add(Henegama);
        destinationLatLong.add(Waliweriya);
        destinationLatLong.add(Nadungamuwa);
        destinationLatLong.add(Mudungoda);
        destinationLatLong.add(Miriswaththa);
        destinationLatLong.add(Gampaha);
        destinationLatLong.add(Gampaha1);


        spinner_end.setAdapter(new ArrayAdapter<>(PassengerMapsActivity.this, android.R.layout.simple_spinner_dropdown_item,townList_end));

        spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestinationEnd ="";
                    //Toast.makeText(getApplicationContext(),"Select End Location",Toast.LENGTH_SHORT).show();
                    //end.setText("");
                }else {
                    endPoint = position;
                    selectedDestinationEnd = parent.getItemAtPosition(position).toString();
                    //end.setText(ETown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void getDriverLocation() {
        Log.i("12345", "inside getDevice location");
        String s = fAuth.getCurrentUser().getUid();
        Log.i("12345", "user is = "+ s);

        if (fAuth.getCurrentUser().getUid() != null){
            Log.i("12345", "user not null");
            userId = fAuth.getCurrentUser().getUid();

            fStore.collection("BusLocations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.i("12345", "is Success"+ task.isSuccessful());
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document.get("isStarted") == null) {
                                Log.i("12345", "ssss "+document.getId());
                                return;
                            }

                            String isStarted = document.get("isStarted").toString();

                            if (isStarted.equals("True")){

                                String direction = document.get("to").toString();
                                String parengerDirection = startPoint>endPoint? "Colombo" : "Matara";

                                if (direction.equals(parengerDirection)) { //Gampaha -> kiridiwala
                                    Log.i("12345", "show buses "+document.getId());
                                    double lat = document.getDouble("lat");
                                    double log = document.getDouble("log");
                                    double location = document.getDouble("location");
                                    float f = (float) location;
                                    String userId = document.get("userID").toString();
                                    String getUID = document.getId();

                                    LatLng Miriswaththa = new LatLng(lat, log);
                                    mMap.addMarker(new MarkerOptions().position(Miriswaththa).title(userId) .snippet("Bus Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_top_icon)).rotation(f).anchor((float)0.5,(float)0.5));

                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {

                                            String markertitle = marker.getTitle();
                                            String stlo = spinner_start.getSelectedItem().toString();
                                            String enlo = spinner_end.getSelectedItem().toString();
                                            String trRoute = parengerDirection;

                                            if (!townList_end.contains(markertitle)){
//                                                Intent i = new Intent(PassengerFindMap.this,BusInsideDetailsActivity.class);
//                                                i.putExtra("title",markertitle);
//                                                i.putExtra("stLocation",stlo);
//                                                i.putExtra("stInt",startPoint);
//                                                i.putExtra("enInt",endPoint);
//                                                i.putExtra("endLocation",enlo);
//                                                i.putExtra("trRoute",trRoute);
//                                                startActivity(i);
                                            }
                                            return false;
                                        }
                                    });

                                }
                            }

                        }

                    } else {
                        Log.d("12345", "Error getting documents: ", task.getException());
                    }
                }
            });


        }else{
            Log.i("12345", "Error user id");
            return;
        }
    }
}