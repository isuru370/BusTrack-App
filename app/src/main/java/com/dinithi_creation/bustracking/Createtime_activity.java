package com.dinithi_creation.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Createtime_activity extends AppCompatActivity {

    private TextView startTimeId,endTimeId;
    private int t1Hours,t1Minute;
    private String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtime);

        startTimeId = findViewById(R.id.starttime);
        endTimeId = findViewById(R.id.endtime);

        startTimeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeSelect();
                startTimeId.setText(format);
            }
        });

        endTimeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeSelect();
                endTimeId.setText(format);
            }
        });
    }

    private void startTimeSelect() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(Createtime_activity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t1Hours = hourOfDay;
                        t1Minute = minute;

                        String time = t1Hours + ":" + t1Minute;

                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm:aa");
                            format = f12Hours.format(date);
                            // startTimeId.setText(f12Hours.format(date));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, 12,0,false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours,t1Minute);
        timePickerDialog.show();
    }
}