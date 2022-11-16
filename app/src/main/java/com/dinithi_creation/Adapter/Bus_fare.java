package com.dinithi_creation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinithi_creation.bustracking.Pay_pal_activity;
import com.dinithi_creation.bustracking.R;
import com.dinithi_creation.bustracking.pay;
import com.dinithi_creation.driverdetails.Bus_ditails;

import java.util.ArrayList;

public class Bus_fare extends RecyclerView.Adapter<Bus_fare.Bus_fareHolder>{

    Context context;
    ArrayList<Bus_ditails> busDitailsArrayList;

    public Bus_fare(Context context, ArrayList<Bus_ditails> busDitailsArrayList) {
        this.context = context;
        this.busDitailsArrayList = busDitailsArrayList;
    }

    @NonNull
    @Override
    public Bus_fare.Bus_fareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bus_faredesing, parent, false);
        return new Bus_fareHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Bus_fareHolder holder, int position) {

        Bus_ditails table = busDitailsArrayList.get(position);
        holder.busNo.setText("BUS NO :- " + table.getBusNumber());
        holder.stratTime.setText("ROOT NO :- " + table.getBusRootNumber());
        holder.destance.setText("DISTANCE :- "+table.getBusDistance());
        holder.busFare.setText("LKR :- " + table.getBusFare()+".00");


        holder.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Pay_pal_activity.class);
                intent.putExtra("busNumber", table.getBusNumber());
                intent.putExtra("busPrice",table.getBusFare());
                intent.putExtra("status",table.getBusStatus());
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {

        return busDitailsArrayList.size();
    }

    public class Bus_fareHolder extends RecyclerView.ViewHolder{
        TextView busNo, stratTime, destance, busFare;
        Button payBtn;

        public Bus_fareHolder(@NonNull View itemView) {
            super(itemView);
            busNo = itemView.findViewById(R.id.A1);
            stratTime = itemView.findViewById(R.id.A2);
            destance = itemView.findViewById(R.id.A3);
            busFare = itemView.findViewById(R.id.A4);
            payBtn = itemView.findViewById(R.id.paybtn);

        }
    }
}
