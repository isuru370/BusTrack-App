package com.dinithi_creation.bustracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.passengerdetails.passdetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class PassengerProfile_Activity extends AppCompatActivity {

    private static final int RESULT_OK = -1;
    private Button qrBtn, logOut,findBusBtn;
    private TextView userFullNameTxt, userEmailTxt, contact, address,userUserName_Profile;

    private Uri imageUri;
    private Bitmap compressor;
    ImageView userImageP;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String vui;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_profile);

        userEmailTxt = findViewById(R.id.passenger_profile_email);
        userFullNameTxt = findViewById(R.id.passenger_profile_name);
        contact = findViewById(R.id.passenger_profile_phonenumberB3);
        userUserName_Profile = findViewById(R.id.passenger_profile_usernameB2);
        address = findViewById(R.id.passenger_profile_user_address);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userImageP = findViewById(R.id.imageProfile_profile);
        storageReference = FirebaseStorage.getInstance().getReference();

        ImageView userImageP = findViewById(R.id.imageProfile_profile);
        String userId = fAuth.getCurrentUser().getUid();



        StorageReference profileRef = storageReference.child("user profile").child(userId +".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userImageP);
            }
        });

        userImageP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(PassengerProfile_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(PassengerProfile_Activity.this,"Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(PassengerProfile_Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {


                       // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(PassengerProfile_Activity.this);
                    }
                }else{
                   // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(PassengerProfile_Activity.this);

                }
            }
        });

        userDetails();




    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // ImageView userImageP = getView().findViewById(R.id.imageProfile_home);
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode== RESULT_OK){
//                imageUri = result.getUri();
//                userImageP.setImageURI(imageUri);
//                //ImageView userImageP = getView().findViewById(R.id.imageProfile_home);
//
//                StorageReference fileRef = storageReference.child("user profile").child(userId +".jpg");
//                fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Picasso.get().load(uri).into(userImageP);
//                            }
//                        });
//                        Toast.makeText(PassengerProfile_Activity.this,"Image Uploaded !",Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @NotNull Exception e) {
//                        Toast.makeText(PassengerProfile_Activity.this,"Failed !",Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Exception error = result.getError();
//            }
//        }
//    }

    public void userDetails(){

        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

            fStore.collection("Passenger").whereEqualTo("passeuid",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    userId = fAuth.getCurrentUser().getUid();
                    for(QueryDocumentSnapshot snapshot: task.getResult()){
                        final passdetails passdetails = snapshot.toObject(passdetails.class);
                        userEmailTxt.setText(passdetails.getPasseemail());
                        userFullNameTxt.setText(passdetails.getPassefname() + " " + passdetails.getPasselname());
                        userUserName_Profile.setText("@"+passdetails.getPassefname().toLowerCase()+"_"+passdetails.getPasselname().toLowerCase());
                        contact.setText(passdetails.getContact());
                        address.setText(passdetails.getAddress());
                    }

                }
            });


        }

    }


}