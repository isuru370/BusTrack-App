package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.model.UserModel;
import com.dinithi_creation.passengerdetails.passdetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText txtEmailSign, txtPass, txtComPass, txtFirstName, txtlastName,contactSignup,addressSignup;
    private TextView txtButton;
    private Button btnSignUp;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private CollectionReference users;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmailSign = findViewById(R.id.emailSignup);
        txtPass = findViewById(R.id.passwordSignup);
        txtComPass = findViewById(R.id.confirmPassword);
        txtButton = findViewById(R.id.txtButtonSignUp);
        btnSignUp = findViewById(R.id.btnSignup);
        txtFirstName = findViewById(R.id.firstName);
        txtlastName = findViewById(R.id.lastName);
        progressBar = findViewById(R.id.progressBar);
        contactSignup = findViewById(R.id.contactSignup);
        addressSignup = findViewById(R.id.addressSignup);


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        txtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goPassengerActivity2 = new Intent(Register_Activity.this, SignIn_Activity.class);
                startActivity(goPassengerActivity2);
                finish();
            }
        });

    }

    private void createUser() {

        String email = txtEmailSign.getText().toString().trim();
        String password = txtPass.getText().toString().trim();
        String firstName = txtFirstName.getText().toString();
        String lastName = txtlastName.getText().toString();
        String contact = contactSignup.getText().toString().trim();
        String address = addressSignup.getText().toString().trim();



        if(validate()){
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            userID = mAuth.getCurrentUser().getUid();

                            passdetails  passenger = new passdetails(userID,firstName,lastName,email,password,"Passenger",contact,address);
                            UserModel userModel = new UserModel(userID,email,password,"Passenger");
                            fStore.collection("Passenger").add(passenger).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    fStore.collection("User").add(userModel);
                                    Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Register_Activity.this, SignIn_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Wrong details", Toast.LENGTH_LONG).show();
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_LONG).show();
                        }
                    });



        }else{
            Toast.makeText(getApplicationContext(),"Wrong details", Toast.LENGTH_LONG).show();
        }


    }

    public  boolean validate(){
        String email = txtEmailSign.getText().toString().trim();
        String password = txtPass.getText().toString().trim();
        String comPassword = txtComPass.getText().toString().trim();
        String firstName = txtFirstName.getText().toString();
        String lastName = txtlastName.getText().toString();

        if (!lastName.isEmpty()) {
            return true;
        }else if (!firstName.isEmpty()) {
            return true;
        }else if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }else if (!password.isEmpty()) {
            return true;
        }else if (!(password.length() < 7)) {
            return true;
        }else if (!comPassword.isEmpty()) {
            return true;
        }else if (password.equals(comPassword)) {
            return true;
        }else{
            return  false;
        }

    }
}