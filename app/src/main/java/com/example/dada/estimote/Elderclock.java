package com.example.dada.estimote;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Elderclock extends ForDrawer {
    private Button btn_set;
    private Button btn_cancel;
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private TextView tvclock;
    private TextView tvmajor;
    private TextView tvminor;
    private GlobalVariable globalVariable;
    private Button btnsetconnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderclock);
        globalVariable = (GlobalVariable)Elderclock.this.getApplicationContext();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        toolbar.setTitle("設定");
        setUpToolBar();
        CurrentMenuItem = 3;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        bindview();
        tvmajor.setText(Integer.toString(globalVariable.major));
        tvminor.setText(Integer.toString(globalVariable.minor));
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(Elderclock.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //设置当前时间
                                final Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());
                                // 根据用户选择的时间来设置Calendar对象
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                tvclock.setText(" "+hourOfDay+" 點 "+minute+" 分");
                                Toast.makeText(Elderclock.this, "hour: "+hourOfDay +" minute: " + minute,Toast.LENGTH_SHORT).show();
                                // ②设置AlarmManager在Calendar对应的时间启动Activity
                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                Log.e("HEHE",c.getTimeInMillis()+"");   //这里的时间是一个unix时间戳
                                // 提示闹钟设置完毕:
                                //Toast.makeText(Elderclock.this, "闹钟设置完毕~"+ c.getTimeInMillis(),Toast.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();
//                btn_cancel.setVisibility(View.VISIBLE);
            }
        });

        /*btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pi);
                btn_cancel.setVisibility(View.GONE);
                Toast.makeText(Elderclock.this, "闹钟已取消", Toast.LENGTH_SHORT)
                        .show();
            }
        });*/


    }

    private void bindview() {

        btn_set = (Button) findViewById(R.id.btn_set);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        tvclock = (TextView) findViewById(R.id.tvclock);
        tvmajor = (TextView) findViewById(R.id.tvdevmajor);
        tvminor = (TextView) findViewById(R.id.tvdevminor);


        Intent intent = new Intent(Elderclock.this, ClockActivity.class);
        pi = PendingIntent.getActivity(Elderclock.this, 0, intent, 0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }
}
