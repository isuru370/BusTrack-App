package com.dinithi_creation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dinithi_creation.bustracking.DriverDitels;
import com.dinithi_creation.bustracking.R;
import com.dinithi_creation.bustracking.driverprofile;
import com.dinithi_creation.driverdetails.driver;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    Context context;
    ArrayList<driver> driverArrayList;



    public DriverAdapter(Context context, ArrayList<driver> driverArrayList) {
        this.context = context;
        this.driverArrayList = driverArrayList;
    }

    @NonNull
    @Override
    public DriverAdapter.DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.driverview, parent, false);
        return new DriverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.DriverViewHolder holder, int position) {

        driver dr = driverArrayList.get(position);
        holder.driverfullname.setText("NAME :- " + dr.getDriverfullname());
        holder.driverlecence.setText("LICENCE :- " + dr.getDriverlicenes());
        holder.drivernic.setText("NIC :- " + dr.getDrivernicno());
        holder.driverstatus.setText("STATUS :- " + dr.getDriverstatus());
        String driverproimg = dr.getDriverproimg();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("Appimage/driverproimage/"+driverproimg);
        storageReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(holder.driverproimage);
                            }
                        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, driverprofile.class);
                intent.putExtra("drivernic_id", dr.getDrivernicno());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return driverArrayList.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView driverproimage;
        TextView driverfullname, driverlecence, drivernic, driverstatus;
        CardView cardView;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);

            driverproimage = itemView.findViewById(R.id.driverproimageid);
            driverfullname = itemView.findViewById(R.id.driverfullnameid);
            driverlecence = itemView.findViewById(R.id.driverlicenceid);
            drivernic = itemView.findViewById(R.id.drivernicid);
            driverstatus = itemView.findViewById(R.id.driverstatusid);
            cardView = itemView.findViewById(R.id.cardviewclik);
        }
    }
}
