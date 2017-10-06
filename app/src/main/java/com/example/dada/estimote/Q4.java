package com.example.dada.estimote;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Q4.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Q4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Q4 extends PageView{
    private TextView tv1;
    private ToggleButton tgb1;
    private AlarmManager alarmManager;
    private int thr = 20;
    private int tmin = 0;
    public Q4(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_q4, null);
        addView(view);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        tv1 = (TextView)findViewById(R.id.frag4_tv3);
        tgb1 = (ToggleButton)findViewById(R.id.frag4_tgb1);
        tv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(context, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //设置当前时间
//                                final Calendar c = Calendar.getInstance();
//                                c.setTimeInMillis(System.currentTimeMillis());
                                // 根据用户选择的时间来设置Calendar对象
//                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                                c.set(Calendar.MINUTE, minute);
                                String hr = "";
                                String min = "";
                                if (hourOfDay<10) {
                                    hr = "0"+Integer.toString(hourOfDay);
                                } else {
                                    hr = Integer.toString(hourOfDay);
                                }
                                if (minute<10) {
                                    min = "0"+Integer.toString(minute);
                                } else {
                                    min = Integer.toString(minute);
                                }

                                tv1.setText(hr+":"+min);

                                // ②设置AlarmManager在Calendar对应的时间启动Activity
//                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
//                                Log.e("HEHE",c.getTimeInMillis()+"");   //这里的时间是一个unix时间戳
                                // 提示闹钟设置完毕:
                                //Toast.makeText(Elderclock.this, "闹钟设置完毕~"+ c.getTimeInMillis(),Toast.LENGTH_SHORT).show();
                            }
                        }, thr, tmin, false).show();
            }
        });

    }

    @Override
    public void refreshView() {

    }
}

