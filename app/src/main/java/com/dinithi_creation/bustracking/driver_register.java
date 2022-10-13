package com.dinithi_creation.bustracking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.CircleTransform;
import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.driverdetails.driver;
import com.dinithi_creation.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.UUID;

public class driver_register extends AppCompatActivity {

    private TextView selectimgbtn;
    private ShapeableImageView profimeimage;
    private EditText Dfullname, Demailaddress, Dphoneno, Dlicensno, Dnicnumber, Dhomeaddress, Dpassword, Dcompassword;
    private Button submitbtn;
    private String rendomId1, rendomId2, rendomId3, uid;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri imageUrl1, imageUrl2, imageUrl3;
    private ImageView licencesphoto1, licencesphoto2;
    private int x, y, z;


    final String TAG = "Bus Tracking";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        mAuth = FirebaseAuth.getInstance();


        selectimgbtn = findViewById(R.id.btnproimg);
        profimeimage = findViewById(R.id.driverimg);

        Dfullname = findViewById(R.id.dirfullname);
        Demailaddress = findViewById(R.id.diremail);
        Dphoneno = findViewById(R.id.dirphone);
        Dlicensno = findViewById(R.id.dirlicensno);
        Dnicnumber = findViewById(R.id.dirnicno);
        Dhomeaddress = findViewById(R.id.dirhomeaddress);
        Dpassword = findViewById(R.id.dirpassword);
        Dcompassword = findViewById(R.id.dircompassword);

        licencesphoto1 = findViewById(R.id.driphoto1);
        licencesphoto2 = findViewById(R.id.dirphoto2);


        selectimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                x = 1;
                activityResultLauncher.launch(intent);
            }
        });
        submitbtn = findViewById(R.id.dirbtnsubmit);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationpart();

                String pass1 = Dpassword.getText().toString();
                String pass2 = Dcompassword.getText().toString();
                if (pass1.equals(pass2)) {

                    mAuth.createUserWithEmailAndPassword(Demailaddress.getText().toString(), pass2)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        driver driver1 = new driver(user.getUid(), rendomId1, Dfullname.getText().toString().trim(),
                                                Demailaddress.getText().toString().trim(), Dphoneno.getText().toString().trim(),
                                                Dlicensno.getText().toString().trim(), Dnicnumber.getText().toString().trim(),
                                                Dhomeaddress.getText().toString().trim(), pass2, rendomId2, rendomId3, "INACTIVE");
                                        UserModel userModel = new UserModel(user.getUid(), Demailaddress.getText().toString().trim(),
                                                pass2, "Driver");
                                        firestore.collection("Driver").add(driver1);
                                        firestore.collection("User").add(userModel);
                                        startActivity(new Intent(driver_register.this, Busregister_activity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(driver_register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "password Incarrect", Toast.LENGTH_LONG).show();
                }
            }
        });

        licencesphoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                x = 2;
                activityResultLauncher.launch(intent);
            }
        });
        licencesphoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                x = 3;
                activityResultLauncher.launch(intent);
            }
        });
    }

    private void validationpart() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (x == 1) {
                imageUrl1 = data.getData();
                profimeimage.setImageURI(imageUrl1);
                saveprofileimage1();
            } else if (y == 2) {
                imageUrl2 = data.getData();
                licencesphoto1.setImageURI(imageUrl2);
                saveprofileimage2();

            } else if (z == 3) {
                imageUrl3 = data.getData();
                licencesphoto2.setImageURI(imageUrl3);
                saveprofileimage3();
            }
        }
    }

    private void saveprofileimage1() {
        rendomId1 = UUID.randomUUID().toString();
        StorageReference dirproimage = storageReference.child("Appimage").child("driverproimage/" + rendomId1);
        dirproimage.putFile(imageUrl1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        assert result.getData() != null;
                        if (x == 1) {
                            imageUrl1 = result.getData().getData();
                            Picasso.get()
                                    .load(imageUrl1)
                                    .transform(new CircleTransform())
                                    .centerCrop()
                                    .resize(150, 150)
                                    .into(profimeimage);
                            saveprofileimage1();
                        } else if (x == 2) {
                            imageUrl2 = result.getData().getData();
                            Picasso.get()
                                    .load(imageUrl2)
                                    .transform(new CircleTransform())
                                    .centerCrop()
                                    .resize(150, 150)
                                    .into(licencesphoto1);
                            saveprofileimage2();
                        } else if (x == 3) {
                            imageUrl3 = result.getData().getData();
                            Picasso.get()
                                    .load(imageUrl3)
                                    .transform(new CircleTransform())
                                    .centerCrop()
                                    .resize(150, 150)
                                    .into(licencesphoto2);
                            saveprofileimage3();
                        }
                    }
                }
            }
    );

    private void saveprofileimage2() {
        rendomId2 = UUID.randomUUID().toString();
        StorageReference dirproimage = storageReference.child("Appimage").child("driver licences").child("in/" + rendomId2);
        dirproimage.putFile(imageUrl2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void saveprofileimage3() {
        rendomId3 = UUID.randomUUID().toString();
        StorageReference dirproimage = storageReference.child("Appimage").child("driver licences").child("out/" + rendomId3);
        dirproimage.putFile(imageUrl3)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
        }
    }
}