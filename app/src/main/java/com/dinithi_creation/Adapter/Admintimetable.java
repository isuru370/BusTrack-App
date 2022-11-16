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

import com.dinithi_creation.bustracking.Driver_Home_Activity;
import com.dinithi_creation.bustracking.R;
import com.dinithi_creation.driverdetails.Time_table;

import java.util.ArrayList;

public class Admintimetable extends RecyclerView.Adapter<Admintimetable.AdminViewHolder> {

    Context context;
    ArrayList<Time_table> timeTableArrayList;

    public Admintimetable(Context context, ArrayList<Time_table> timeTableArrayList) {
        this.context = context;
        this.timeTableArrayList = timeTableArrayList;
    }

    @NonNull
    @Override
    public Admintimetable.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admintimetabledesign,parent,false);
        return new AdminViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Admintimetable.AdminViewHolder holder, int position) {

        Time_table table = timeTableArrayList.get(position);
        holder.busNo01.setText("BUS NO :- "+table.getBusNumber());
        holder.startT01.setText("START TIME :- "+table.getStartTime());
        holder.endT01.setText("END TIME :- "+table.getEndTime());
        holder.statC01.setText("START CITY :- "+table.getStartcity());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Driver_Home_Activity.class);
                intent.putExtra("busId",table.getBusNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeTableArrayList.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder{

        private TextView busNo01,startT01,endT01,statC01;
        private ImageView imageView;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            busNo01 = itemView.findViewById(R.id.AA1);
            startT01 = itemView.findViewById(R.id.AA2);
            endT01 = itemView.findViewById(R.id.AA3);
            statC01 = itemView.findViewById(R.id.AA4);
            imageView = itemView.findViewById(R.id.nextbtn);
        }
    }
}
