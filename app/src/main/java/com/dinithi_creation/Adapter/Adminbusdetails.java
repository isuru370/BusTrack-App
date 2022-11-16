package com.dinithi_creation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinithi_creation.bustracking.R;
import com.dinithi_creation.driverdetails.Bus_ditails;

import java.util.ArrayList;

public class Adminbusdetails extends RecyclerView.Adapter<Adminbusdetails.Adminbusdetailsviewholder> {

    Context context;
    ArrayList<Bus_ditails> busDitailsArrayList;

    public Adminbusdetails(Context context, ArrayList<Bus_ditails> busDitailsArrayList) {
        this.context = context;
        this.busDitailsArrayList = busDitailsArrayList;
    }

    @NonNull
    @Override
    public Adminbusdetails.Adminbusdetailsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_busdeatails_design,parent,false);
        return new Adminbusdetailsviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adminbusdetails.Adminbusdetailsviewholder holder, int position) {

        Bus_ditails table = busDitailsArrayList.get(position);
        holder.busNo.setText("BUS NO :- " + table.getBusNumber());
        holder.busRootNo.setText("ROOT NO :- " + table.getBusRootNumber());
        holder.destance.setText("DISTANCE :- "+table.getBusDistance());
        holder.busFare.setText("LKR :- " + table.getBusFare());

    }

    @Override
    public int getItemCount() {
        return busDitailsArrayList.size();
    }

    public class Adminbusdetailsviewholder extends RecyclerView.ViewHolder{

        TextView busNo, busRootNo, destance, busFare;
        ImageView adminBusDBtn;

        public Adminbusdetailsviewholder(@NonNull View itemView) {
            super(itemView);

            busNo = itemView.findViewById(R.id.AC1);
            busRootNo = itemView.findViewById(R.id.AC2);
            destance = itemView.findViewById(R.id.AC3);
            busFare = itemView.findViewById(R.id.AC4);
            adminBusDBtn = itemView.findViewById(R.id.busdetailsbtn);
        }
    }
}
