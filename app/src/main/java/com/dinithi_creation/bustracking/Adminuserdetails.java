package com.dinithi_creation.bustracking;

import static com.dinithi_creation.bustracking.Register_Activity.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.dinithi_creation.Adapter.Adminpassemger;
import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.model.UserModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Adminuserdetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<UserModel> userModelArrayList;
    Adminpassemger userModel;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminuserdetails);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        userModelArrayList = new ArrayList<UserModel>();
        userModel = new Adminpassemger(Adminuserdetails.this, userModelArrayList);

        recyclerView.setAdapter(userModel);

        EventChangeListener();
    }

    private void EventChangeListener() {
        firebaseFirestore.collection("User")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.i(TAG, "Firedtore  " + error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                userModelArrayList.add(dc.getDocument().toObject(UserModel.class));

                            }
                            userModel.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}
