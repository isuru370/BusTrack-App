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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dinithi_creation.Admin_ditails.Admin_class;
import com.dinithi_creation.CircleTransform;
import com.dinithi_creation.model.UserModel;
import com.dinithi_creation.passengerdetails.passdetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Admin_register extends AppCompatActivity {

    ShapeableImageView adminproimage;
    EditText adminid, addminname, adminemail, adminphoneno, adminpasswoad, comfrompaasswod;
    TextView selectimage;
    Button nextadminbtn, submitbtn;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    String userID, rendomId2;
    int x;
    Uri imageUrl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = firebaseStorage.getReference();

        adminproimage = findViewById(R.id.adminproimgid);
        adminid = findViewById(R.id.adminid);
        addminname = findViewById(R.id.adminnameid);
        adminemail = findViewById(R.id.adminemailid);
        adminphoneno = findViewById(R.id.adminphonenoid);
        adminpasswoad = findViewById(R.id.adminpassworadid);
        comfrompaasswod = findViewById(R.id.admincomformpasswordid);
        selectimage = findViewById(R.id.adminselectimageid);
        nextadminbtn = findViewById(R.id.adminnxtbtnid);
        submitbtn = findViewById(R.id.adminsubmitid);

        addminname.setEnabled(false);
        adminemail.setEnabled(false);
        adminphoneno.setEnabled(false);
        adminpasswoad.setEnabled(false);
        comfrompaasswod.setEnabled(false);
        selectimage.setEnabled(false);

        nextadminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adminId = adminid.getText().toString();
                if (adminId.equals("1234")) {
                    Toast.makeText(getApplicationContext(), "Admin Id Valied", Toast.LENGTH_LONG).show();
                    adminid.setEnabled(false);
                    addminname.setEnabled(true);
                    adminemail.setEnabled(true);
                    adminphoneno.setEnabled(true);
                    adminpasswoad.setEnabled(true);
                    comfrompaasswod.setEnabled(true);
                    selectimage.setEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Admin Id Invalied", Toast.LENGTH_LONG).show();
                    adminid.setText(null);
                }
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = addminname.getText().toString();
                String s1 = adminemail.getText().toString();
                String s2 = adminphoneno.getText().toString();
                String s3 = adminpasswoad.getText().toString();
                String s4 = comfrompaasswod.getText().toString();

                if (!s.isEmpty() && !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty() && !s4.isEmpty()) {
                    if (s3.equals(s4)) {
                        createUser();
                    }else{
                        Toast.makeText(getApplicationContext(), "Password not Equals", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "TextFeild is Empty", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void createUser() {

        String email = adminemail.getText().toString().trim();
        String password = adminpasswoad.getText().toString().trim();
        String firstName = addminname.getText().toString();
        String contact = adminphoneno.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        userID = firebaseAuth.getCurrentUser().getUid();

                        Admin_class adminD = new Admin_class(userID, firstName, email, contact, password, rendomId2);
                        UserModel userModel = new UserModel(userID, email, password, "Admin");
                        firestore.collection("Admin").add(adminD).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                firestore.collection("User").add(userModel);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Admin_register.this, Admin_Home_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Wrong details", Toast.LENGTH_LONG).show();
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("Image/*");
                x = 1;
                activityResultLauncher.launch(intent);
            }
        });

    }

    private void saveprofileimage1() {
        rendomId2 = UUID.randomUUID().toString();
        StorageReference dirproimage = storageReference.child("Admin_profile_image");
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
                                    .into(adminproimage);
                            saveprofileimage1();
                        }
                    }
                }
            }
    );
}



