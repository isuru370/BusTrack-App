package com.dinithi_creation.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dinithi_creation.bustracking.R;
import com.dinithi_creation.bustracking.bus_time_details;
import com.dinithi_creation.bustracking.driverprofile;
import com.dinithi_creation.bustracking.pay;
import com.dinithi_creation.driverdetails.Time_table;

import java.util.ArrayList;

public class passengerview extends RecyclerView.Adapter<passengerview.PassengerViweHolder> {

    Context context;
    ArrayList<Time_table> passengerArrayList;

    public passengerview(Context context, ArrayList<Time_table> passengerArrayList) {
        this.context = context;
        this.passengerArrayList = passengerArrayList;
    }

    @NonNull
    @Override
    public passengerview.PassengerViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.passenger_time_table, parent, false);
        return new PassengerViweHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull passengerview.PassengerViweHolder holder, int position) {

       Time_table table = passengerArrayList.get(position);
        holder.busNo.setText("BuS NO :- " + table.getBusNumber());
        holder.stratCity.setText("START CITY :- " + table.getStartcity());
        holder.endCity.setText("END CITY :- " + table.getEndCity());
        holder.time.setText("TIME :- " + table.getStartTime()+" - "+table.getEndTime());


        holder.cardViewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(context, bus_time_details.class);
                    intent.putExtra("busNumber", table.getBusNumber());
                    context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return passengerArrayList.size();
    }

    public class PassengerViweHolder extends RecyclerView.ViewHolder {

        TextView busNo, stratCity, endCity, time;
        CardView cardViewPass;

        public PassengerViweHolder(@NonNull View itemView) {
            super(itemView);

            busNo = itemView.findViewById(R.id.busnoid);
            stratCity = itemView.findViewById(R.id.startcityid);
            endCity = itemView.findViewById(R.id.endcityid);
            time = itemView.findViewById(R.id.timeid);
            cardViewPass = itemView.findViewById(R.id.passengerCardView);

        }
    }
}
