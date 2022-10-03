package com.dinithi_creation.bustracking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.dinithi_creation.bustracking.databinding.ActivityMapsStartDriverBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MapsStartDriver_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsStartDriverBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button checkBtn;
    List<String> list = new ArrayList<>();

    double avalFirebase = 0;

    private String mParam1;
    private String mParam2;

    //chanell
    public  String c1 = "channel1";
    int chStart = 1;
    NotificationManagerCompat notificationManagerCompat;

    String selectedDestination = "";

    boolean isActive = false;

    Spinner spinner_start;

    //firebase
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String vui;
    StorageReference storageReference;

    final Handler mHandler = new Handler();
    private Thread mUiThread;

    //map change live location
    public ScheduledExecutorService scheduler;
    private boolean isrun = false;


    private static final int DEFAULT_ZOOM = 12;

    MarkerOptions marker;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    LatLng centerlocation;
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    Vector<MarkerOptions> markerOptions;

    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsStartDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapDriver);
        mapFragment.getMapAsync(this);

        //firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        spinner_start = findViewById(R.id.spinner_start);

        checkBtn = (Button) findViewById(R.id.busStatusBtn);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        centerlocation = new LatLng(6.3, 80.05);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Vector<MarkerOptions> markerOptions = new Vector<>();

        markerOptions.add(new MarkerOptions().title("Colombo")
                .position(new LatLng(6.935074734727916, 79.85515642756634))
                .snippet("Colombo Bus Stand").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Wellawatte")
                .position(new LatLng(6.874796535502167, 79.86116881962982))
                .snippet("Wellawatte Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Moratuwa")
                .position(new LatLng(6.84875796166515, 79.87136748929741))
                .snippet("Moratuwa Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Kalutara")
                .position(new LatLng(6.630549923158772, 79.96475128057374))
                .snippet("Kalutara Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Hikkaduwa")
                .position(new LatLng(6.212960533248956, 80.10208038539187))
                .snippet("Hikkaduwa Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Galle")
                .position(new LatLng(6.043060790740084, 80.2149402690607))
                .snippet("Galle Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Koggala")
                .position(new LatLng(5.988122208165549, 80.32899320350595))
                .snippet("Koggala Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Mirissa")
                .position(new LatLng(5.950150475483886, 80.45588544292083))
                .snippet("Mirissa Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );
        markerOptions.add(new MarkerOptions().title("Matara")
                .position(new LatLng(5.945118161020283, 80.5492704548124))
                .snippet("Matara Bus Stand").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
        );


        mMap = googleMap;

        for (MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }
        getLocationPermission();
        updateLocationUI();

        selectDestination();
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedDestination.equals("")){
                    Toast.makeText(MapsStartDriver_Activity.this,"Please Select Destination!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isActive){
                    isActive = true;

                    checkBtn.setText("STOP");
                    spinner_start.setEnabled(false);


                    Toast.makeText(MapsStartDriver_Activity.this,"Bus Started!",Toast.LENGTH_SHORT).show();

                    scheduler = Executors.newSingleThreadScheduledExecutor();
                    scheduler.scheduleAtFixedRate(new Runnable()
                    {
                        public void run()
                        {
                            if(isrun){
                                runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        getDeviceLocation();
                                        enableMyLocation();

                                        Toast.makeText(MapsStartDriver_Activity.this, "change location",Toast.LENGTH_SHORT).show();
                                        mMap.clear();
                                        for (MarkerOptions mark : markerOptions){
                                            mMap.addMarker(mark);
                                        }
                                    }
                                });
                            }else{
                                isrun = true;
                            }

                        }
                    }, 0, 5, TimeUnit.SECONDS);
                }else {
                    spinner_start.setEnabled(true);
                    isActive = false;
                    checkBtn.setText("RUN");
                    mMap.clear();
                    for (MarkerOptions mark : markerOptions){
                        mMap.addMarker(mark);
                    }
                    Toast.makeText(MapsStartDriver_Activity.this,"Bus Stopped!",Toast.LENGTH_SHORT).show();


                    String userID ;
                    userID = fAuth.getCurrentUser().getUid();
                    firebaseUploaded(false, userID, null);

                    scheduler.shutdownNow();
                }
            }
        });


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,9));
    }



//    public final void runOnUiThread(Runnable action) {
//        if (Thread.currentThread() != mUiThread) {
//            mHandler.post(action);
//        } else {
//            action.run();
//        }
//    }

    //Select Destination
    private void selectDestination(){
        ArrayList<String> townList_start = new ArrayList<>();
        townList_start.add("Choose Destination! ");
        townList_start.add("Colombo");
        townList_start.add("Matara");


        spinner_start.setAdapter(new ArrayAdapter<>(MapsStartDriver_Activity.this, android.R.layout.simple_spinner_dropdown_item,townList_start));

        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestination = "";
                    Toast.makeText(MapsStartDriver_Activity.this,
                            "Select Start Location",Toast.LENGTH_SHORT).show();
                    //start.setText("");
                }else {
                    selectedDestination =  parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void firebaseUploaded(boolean isRun, String userID, Location lastKnownLocation) {
        DocumentReference documentReference = fStore.collection("BusLocations").document(userID);
        Map<String,Object> user = new HashMap<>();


        DocumentReference driverDetails = fStore.collection("Bus").document(userID);
        Map<String,Object> driver = new HashMap<>();

        Map<String,Object> driver1 = new HashMap<>();


        if(isRun){
            user.put("lat",lastKnownLocation.getLatitude());
            user.put("log",lastKnownLocation.getLongitude());
            user.put("to",selectedDestination);
            user.put("isStarted","True");
            user.put("userID",userID);
            user.put("location",lastKnownLocation.getBearing());


            if (selectedDestination.equals("Colombo")){
                driver.put("from","Matara");
                driver.put("to",selectedDestination);
            }else{
                driver.put("from","Matara");
                driver.put("to",selectedDestination);
            }


            driver.put("id",userID);


        }else{
            user.put("to",selectedDestination);
            user.put("isStarted","False");
            user.put("userID",userID);



            driver.put("from","");
            driver.put("to","");

            driver.put("id",userID);
        }

        driverDetails.set(driver);

        FirebaseFirestore.getInstance().collection("Bus").document(userID).update(driver1);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess: Success isStarted False! "+ userID);
            }
        });
    }
    private void drawCircle(LatLng point, double radius_km){
        CircleOptions circleOptions = new CircleOptions();
        //Set center of circle
        circleOptions.center(point);
        //Set radius of circle
        circleOptions.radius(radius_km * 1000);
        //Set border color of circle
        circleOptions.strokeColor(Color.BLUE);
        //Set border width of circle
        circleOptions.strokeWidth(2);
        circleOptions.fillColor(Color.argb(75,94,165,197));
        //Adding circle to map
        Circle mapCircle = mMap.addCircle(circleOptions);
        //We can remove above circle with code bellow
        //mapCircle.remove();

    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(MapsStartDriver_Activity.this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(MapsStartDriver_Activity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }


    }
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */

        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(MapsStartDriver_Activity.this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                LatLng currentlocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                                mMap.addMarker(new MarkerOptions().position(currentlocation).title("current location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_top_icon)).rotation(lastKnownLocation.getBearing()).anchor((float)0.5,(float)0.5));

                                //Toast.makeText(getActivity(), "latitude:" + lastKnownLocation.getLatitude() + " longitude:" +lastKnownLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                                drawCircle(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()),2);
                                //Toast.makeText(getActivity(), "circle is here", Toast.LENGTH_SHORT).show();

                                String userID ;
                                userID = fAuth.getCurrentUser().getUid();

                                firebaseUploaded(true,userID,lastKnownLocation);




                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(MapsStartDriver_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(MapsStartDriver_Activity.this, perms,200);
        }
    }
}