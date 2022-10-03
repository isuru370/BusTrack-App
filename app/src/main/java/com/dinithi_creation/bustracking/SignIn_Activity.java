package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class SignIn_Activity extends AppCompatActivity {

    private TextView txtSignUp, txtForgetPass;
    private EditText edtEmailSignIn, edtPasswordSignIn;
    private Button btnLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userId;
    public String verifyUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        txtSignUp = findViewById(R.id.txtButtonSignIn);
        txtForgetPass = findViewById(R.id.txtForgetpassword);
        edtEmailSignIn = findViewById(R.id.emailSignIn);
        edtPasswordSignIn = findViewById(R.id.passwordSignIn);
        btnLogin = findViewById(R.id.btnLogIn);
        progressBar = findViewById(R.id.progressBar);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link");
                passwordResetDialog.setView(resetMail);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();

                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SignIn_Activity.this, "Reset Link Sent To Your Email. ",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(SignIn_Activity.this, " Error ! Reset Link is Not Sent. " +e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn_Activity.this,selects_Activity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }


    private void checkValidation() {
        String email = edtEmailSignIn.getText().toString().trim();
        String password = edtPasswordSignIn.getText().toString().trim();


        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                if (!(password.length() < 7))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();

                                fStore.collection("User").whereEqualTo("userId",currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for(QueryDocumentSnapshot snapshot : task.getResult()){
                                            final UserModel userModel = snapshot.toObject(UserModel.class);
                                            if(userModel.getUserType().equals("Driver")){
                                                startActivity(new Intent(SignIn_Activity.this,Driver_Home_Activity.class));
                                            }else if(userModel.getUserType().equals("Passenger")){
                                                startActivity(new Intent(SignIn_Activity.this,Passenger_Home_Activity.class));
                                            }else{
                                                startActivity(new Intent(SignIn_Activity.this,Admin_Home_Activity.class));
                                            }
                                        }
                                    }
                                });

                                Toast.makeText(SignIn_Activity.this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(SignIn_Activity.this, "Login Error !", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }else
                {
                    edtPasswordSignIn.setError("Passwords length should be >=7 !");
                }

            }else
            {
                edtPasswordSignIn.setError("Empty Fields Are Not Allowed !");
            }
        }else if (email.isEmpty())
        {
            edtEmailSignIn.setError("Empty Fields Are Not Allowed !");
        }else
        {
            edtEmailSignIn.setError("Please Enter Correct Email !");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser !=null){
            fStore.collection("User").whereEqualTo("userId",currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(QueryDocumentSnapshot snapshot : task.getResult()){
                        final UserModel userModel = snapshot.toObject(UserModel.class);
                        if(userModel.getUserType().equals("Driver")){
                            startActivity(new Intent(SignIn_Activity.this,Driver_Home_Activity.class));
                        }else if(userModel.getUserType().equals("Passenger")){
                            startActivity(new Intent(SignIn_Activity.this,Passenger_Home_Activity.class));
                        }else{
                            startActivity(new Intent(SignIn_Activity.this,Admin_Home_Activity.class));
                        }
                    }
                }
            });
        }
    }
}