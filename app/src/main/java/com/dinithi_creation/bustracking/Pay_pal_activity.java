package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.dinithi_creation.Config.Config;
import com.dinithi_creation.driverdetails.Time_table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Pay_pal_activity extends AppCompatActivity {

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //use Sandbox because we on test
            .clientId(Config.PAYPAL_CLIENT_ID);

    EditText busPrice;
    TextView busNo, busTime,startCity;
    Button paybtn;
    FirebaseFirestore firestore;
    String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);


        firestore = FirebaseFirestore.getInstance();
        String status = getIntent().getStringExtra("status");

        busPrice = findViewById(R.id.edtAmount);
        busNo = findViewById(R.id.busnumber);
        busTime = findViewById(R.id.busStartTime);
        startCity = findViewById(R.id.startCity);
        paybtn = findViewById(R.id.btnPayNow);

        busPrice.setEnabled(false);

        busPrice.setText("LKR " + getIntent().getStringExtra("busPrice") + ".00");
        busNo.setText("BUS NO :- " + getIntent().getStringExtra("busNumber"));

        firestore.collection("BusTimeTable")
                .whereEqualTo("busNumber", getIntent().getStringExtra("busNumber"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            Time_table table = snapshot.toObject(Time_table.class);
                            busTime.setText("START TIME :- " + table.getStartTime());
                            startCity.setText("START CITY :- "+table.getStartcity());
                        }
                    }
                });

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.trim().equals("1")){
                    processPayment();
                }else{
                    Toast.makeText(getApplicationContext(),"Cancel Bus",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void processPayment() {
        amount = getIntent().getStringExtra("busPrice");
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Pay For Bus Ticket", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (requestCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {

                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount)

                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else if (requestCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }


}
