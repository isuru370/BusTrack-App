package com.dinithi_creation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinithi_creation.bustracking.Pay_pal_activity;
import com.dinithi_creation.bustracking.R;
import com.dinithi_creation.driverdetails.Bus_ditails;
import com.dinithi_creation.model.UserModel;

import java.util.ArrayList;

public class Adminpassemger extends RecyclerView.Adapter<Adminpassemger.adminPassengerViewHolder> {

    Context context;
    ArrayList<UserModel> userModelArrayList;

    public Adminpassemger(Context context, ArrayList<UserModel> userModelArrayList) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
    }

    @NonNull
    @Override
    public Adminpassemger.adminPassengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.addmin_passenger_ditals,parent,false);
        return new adminPassengerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adminpassemger.adminPassengerViewHolder holder, int position) {

        UserModel table = userModelArrayList.get(position);
        holder.userId.setText("ID :- " + table.getUserId());
        holder.email.setText( table.getEmail());
        holder.password.setText(table.getPassword());
        holder.userType.setText(table.getUserType());

        holder.userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Pay_pal_activity.class);
                intent.putExtra("busNumber", table.getUserId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public class adminPassengerViewHolder extends RecyclerView.ViewHolder {
        private TextView userId,email,password,userType;
        private ImageView userimg;

        public adminPassengerViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.AB1);
            email = itemView.findViewById(R.id.AB2);
            password = itemView.findViewById(R.id.AB3);
            userType = itemView.findViewById(R.id.AB4);
            userimg = itemView.findViewById(R.id.nextpasenger);
        }
    }
}
