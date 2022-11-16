package com.dinithi_creation.bustracking;

import static com.dinithi_creation.bustracking.Register_Activity.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class password_change extends AppCompatActivity {

    private EditText oldPassword,newPassword,comfromPassword;
    private Button changePassword;
    private FirebaseFirestore firestore;
    private String uid,driverpassword,id,id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        firestore = FirebaseFirestore.getInstance();

        oldPassword = findViewById(R.id.oldpassword);
        newPassword = findViewById(R.id.newpassword);
        comfromPassword = findViewById(R.id.confrompassword);
        changePassword = findViewById(R.id.submitbtn);

        firestore.collection("Driver").whereEqualTo("userId",uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      for (QueryDocumentSnapshot snapshot : task.getResult()){
                          id2 = snapshot.getId();
                      }
                    }
                });
        firestore.collection("User").whereEqualTo("userId",uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            id = snapshot.getId();
                            driver dr = snapshot.toObject(driver.class);
                            driverpassword = dr.getDriverpassword();
                        }
                    }
                });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s2 = oldPassword.getText().toString();
                Log.i(TAG,"password "+s2);
                if(driverpassword.equals(s2)){
                    String s1 = newPassword.getText().toString();
                    String s = comfromPassword.getText().toString();
                    if(s1.trim().equals(s) && s1.length() <=8){
                        firestore.collection("Driver").document(id2)
                                .update("driverpassword",s1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                    }
                                });
                        firestore.collection("User").document(id)
                                .update("password",s1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),"Password Change success", Toast.LENGTH_LONG).show();
                                        FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
                                        firebaseAuth.signOut();
                                    }
                                });
                    }else{
                        Toast.makeText(getApplicationContext(),"Pasword Incarrect", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Pasword Incarrect", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();

    }
}