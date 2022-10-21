package com.dinithi_creation.bustracking;

import static com.dinithi_creation.bustracking.Createtime_activity.busNumber;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dinithi_creation.driverdetails.Time_table;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CityColombo extends Fragment {

    private TextView timeA, timeB, timeC, timeD, timeE, timeF, timeG;
    private int t1Hours, t1Minute;
    private TextView startTimeId, endTimeId;
    private Spinner start2, end;
    private FirebaseFirestore firestore;
    private Button timeCreate, skip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_colombo, container, false);

        firestore = FirebaseFirestore.getInstance();
        startTimeId = view.findViewById(R.id.starttime01);
        endTimeId = view.findViewById(R.id.endtime01);
        start2 = view.findViewById(R.id.startcityid01);
        end = view.findViewById(R.id.endcityid01);
        timeCreate = view.findViewById(R.id.timecreatebtn01);
        skip = view.findViewById(R.id.skipbtn01);
        timeA = view.findViewById(R.id.time01);
        timeB = view.findViewById(R.id.time02);
        timeC = view.findViewById(R.id.time03);
        timeD = view.findViewById(R.id.time04);
        timeE = view.findViewById(R.id.time05);
        timeF = view.findViewById(R.id.time06);
        timeG = view.findViewById(R.id.time07);


        startTimeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeSelect();
//                startTimeId.setText(format);

            }
        });

        endTimeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeSelect();
//                endTimeId.setText(format);

            }
        });


        timeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_a();
            }
        });
        timeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_b();
            }
        });
        timeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_c();
            }
        });
        timeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_d();
            }
        });
        timeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_e();
            }
        });
        timeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_f();
            }
        });
        timeG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelect_g();
            }
        });
        timeCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = start2.getSelectedItem().toString();
                String s2 = end.getSelectedItem().toString();
                String s3 = startTimeId.getText().toString();
                String s4 = endTimeId.getText().toString();
                String s5 = timeA.getText().toString();
                String s6 = timeB.getText().toString();
                String s7 = timeC.getText().toString();
                String s8 = timeD.getText().toString();
                String s9 = timeE.getText().toString();
                String s10 = timeF.getText().toString();
                String s11 = timeG.getText().toString();
                Time_table table = new Time_table();
                table.setBusNumber(busNumber);
                table.setStartcity(s1);
                table.setEndCity(s2);
                table.setStartTime(s3);
                table.setEndTime(s4);
                table.setTime01(s5);
                table.setTime02(s6);
                table.setTime03(s7);
                table.setTime04(s8);
                table.setTime05(s9);
                table.setTime06(s10);
                table.setTime07(s11);
                firestore.collection("BusTimeTable").add(table);
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CityColombo.this.getContext(), Driver_Home_Activity.class));

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CityColombo.this.getContext(), Driver_Home_Activity.class));

            }
        });

        return view;
    }

    private void startTimeSelect() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            startTimeId.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void endTimeSelect() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            endTimeId.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_a() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeA.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_b() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeB.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_c() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeC.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_d() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeD.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_e() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeE.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_f() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeF.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }

    private void TimeSelect_g() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(CityColombo.this.getContext(),
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
//                            format = f12Hours.format(date);
                            timeG.setText(f12Hours.format(date));
                            // startTimeId.setText(f12Hours.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hours, t1Minute);
        timePickerDialog.show();
    }
}
