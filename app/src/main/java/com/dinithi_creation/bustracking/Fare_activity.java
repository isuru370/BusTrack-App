package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.Bus_ditails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Fare_activity extends AppCompatActivity {

    private Button timeTableBtn, editBtn;
    private EditText rootNo1, cityNo1, cityNo2, kmNo1, mCoust1, fareNo1;
    private SwitchCompat status;
    private TextView busNo1;
    private FirebaseFirestore firestore;
    String uid, id, a;
    double i = 35.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);

        firestore = FirebaseFirestore.getInstance();

        timeTableBtn = findViewById(R.id.faretimetbtn);
        editBtn = findViewById(R.id.busEditBtn);
        rootNo1 = findViewById(R.id.rootno1);
        cityNo1 = findViewById(R.id.citys1);
        cityNo2 = findViewById(R.id.citye1);
        kmNo1 = findViewById(R.id.km1);
        mCoust1 = findViewById(R.id.mcost1);
        fareNo1 = findViewById(R.id.fare1);
        status = findViewById(R.id.status1);
        busNo1 = findViewById(R.id.busno1);

        timeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Fare_activity.this, Timetable_ditails.class));
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.isChecked()) {
                    a = "1";
                } else {
                    a = "0";
                }
                firestore.collection("Bus Details").document(id)
                        .update("busRootNumber", rootNo1.getText().toString().split("=")[0]
                                , "busStartCity", cityNo1.getText().toString().split("=")[0]
                                , "busEndCity", cityNo2.getText().toString().split("=")[0]
                                , "busDistance", kmNo1.getText().toString().split("=")[0]
                                , "busFare", "LKR "+fareNo1.getText().toString()
                                , "busStatus", a
                                ,"busId",uid)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Edit Success", Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

        firestore.collection("Bus Details").whereEqualTo("busId", uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            id = snapshot.getId();
                            Bus_ditails ditails = snapshot.toObject(Bus_ditails.class);

                            busNo1.setText(ditails.getBusNumber());
                            rootNo1.setText(ditails.getBusRootNumber() + " = Root No");
                            cityNo1.setText(ditails.getBusStartCity() + " = Start City");
                            cityNo2.setText(ditails.getBusEndCity() + " = End City");
                            kmNo1.setText(ditails.getBusDistance() + " = Distances");
                            mCoust1.setText("1Km :- LKR " + i);
                            fareNo1.setText(ditails.getBusFare());
                            String busStatus = ditails.getBusStatus();
                            if (busStatus.trim().equals("1")) {
                                status.setChecked(true);
                            } else {
                                status.setChecked(false);
                            }

                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
    }
}