package com.example.dada.estimote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.internal.spdy.FrameReader;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends ForDrawer {
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private double distance;
    private GlobalVariable globalVariable;
    private TextView dis_tv1;
    private TextView dis_tv2;
    private TextView dis_tv3;
    private TextView dis_tv4;
    private ImageButton dis_ib1;
    private ImageButton dis_ib2;
    private Button dis_btn1;
    private ImageView dis_iv1;
    private ImageView dis_iv2;
    private LinearLayout dis_ll1;
    private LinearLayout dis_ll2;
    private View dis_view1;
    private View dis_view2;
    private boolean flag;
    private int tsec=0,csec=0,cmin=0;
    private boolean startflag = false;

    private int listflag = 0; // use for initial listview only for one time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        globalVariable = (GlobalVariable)MainActivity.this.getApplicationContext();
        toolbar.setTitle("鑰匙在哪");
        setUpToolBar();
        CurrentMenuItem = 1;//目前Navigation項目位置
        flag = false;
        Timer timer01 = new Timer();
        timer01.schedule(task, 0,1000);


//        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        int size = NV.getMenu().size();
        for (int i = 0; i < size; i++) {
            NV.getMenu().getItem(i).setChecked(false);
        }
        dis_tv1 = (TextView)findViewById(R.id.dis_tv1);
        dis_tv2 = (TextView)findViewById(R.id.dis_tv2);
        dis_tv3 = (TextView)findViewById(R.id.dis_tv3);
        dis_tv4 = (TextView)findViewById(R.id.dis_tv4);
        dis_ib1 = (ImageButton) findViewById(R.id.dis_ib1);
        dis_ib2 = (ImageButton) findViewById(R.id.dis_ib2);
        dis_btn1 = (Button) findViewById(R.id.dis_btn1);
        dis_iv1 = (ImageView) findViewById(R.id.dis_iv1);
        dis_iv2 = (ImageView) findViewById(R.id.dis_iv2);
        dis_ll1 = (LinearLayout) findViewById(R.id.dis_ll1);
        dis_ll2 = (LinearLayout) findViewById(R.id.dis_ll2);
        dis_view1 =  findViewById(R.id.dis_view1);
        dis_view2 =  findViewById(R.id.dis_view2);
        dis_tv1.setText("找鑰匙挑戰\n以找到鑰匙為目標");

        dis_ib1.setOnClickListener(new View.OnClickListener() {// start to find
            @Override
            public void onClick(View v) {
                flag = true;
                dis_ib1.setVisibility(View.GONE);
                dis_iv1.setVisibility(View.VISIBLE);
                startflag = true;



                // 開始計時
//                dis_ib1.setVisibility(View.GONE);
//                dis_ll1.setVisibility(View.VISIBLE);
            }
        });
        dis_ib2.setOnClickListener(new View.OnClickListener() {// find it
            @Override
            public void onClick(View v) {
                flag = false;
                startflag = false;
                dis_ll1.setVisibility(View.VISIBLE);
                dis_tv2.setText("找到鑰匙了！！！");
                dis_tv2.setTextColor(Color.parseColor("#006934"));
                dis_tv3.setText("找鑰匙挑戰共花費 ");
                dis_tv4.setTextSize(18);
                dis_tv4.setText(Integer.toString(tsec)+" 秒");
                dis_tv3.setTextColor(Color.parseColor("#000000"));
                dis_view1.setVisibility(View.GONE);
                dis_view2.setVisibility(View.GONE);
                dis_ll2.setGravity(Gravity.CENTER_HORIZONTAL);//tv3 put in horizontal center
                dis_iv2.setVisibility(View.GONE);//key image set to gone
                dis_ib2.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 50, 0, 0);
                dis_btn1.setLayoutParams(params);
                /*final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
                int pixels = (int) (200 * scale + 0.5f);
                dis_btn1.setWidth(pixels);*/
                dis_btn1.setText("完成");
            }
        });
        dis_btn1.setOnClickListener(new View.OnClickListener() {// out of distance or do not connect
            @Override
            public void onClick(View v) {
                /*dis_ll1.setVisibility(View.GONE);
                dis_tv1.setVisibility(View.VISIBLE);
                dis_tv1.setText("找鑰匙挑戰\n以找到鑰匙為目標");
                dis_ib1.setVisibility(View.VISIBLE);
                dis_btn1.setText("完成");
                dis_ib2.setVisibility(View.GONE);
                startflag = false;
                tsec = 0;*/
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        distance = -1.0;
        beaconManager = new BeaconManager(this);
        beaconRegion = new BeaconRegion("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        beaconManager.setRangingListener( new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
//                Log.i("ok", String.valueOf(list.get(0).getRssi()));
//                Log.i("ok", String.valueOf(list.get(0).getRssi()));
                for (Beacon beacon : list) {

                    if (beacon.getMajor()==globalVariable.major && beacon.getMinor()==globalVariable.minor) {
                        distance = Math.pow(10, (beacon.getMeasuredPower() - beacon.getRssi()) / 20.0);// 20 from 10 * n ,
                        // n is usually 2 - 2.5;
                        if (flag) {

                            if (distance<15 && distance>=5 ) {
                                dis_iv1.setBackgroundResource(R.mipmap.dis1_112);
                                dis_tv1.setText("離鑰匙還很遠");
                            }else if(distance < 5 && distance>= 1){
                                dis_iv1.setBackgroundResource(R.mipmap.dis1_122);
                                dis_tv1.setText("鑰匙在不遠處");
                            }else if(distance < 1 && distance >= 0){
                                dis_iv1.setBackgroundResource(R.mipmap.dis1_132);
                                dis_tv1.setVisibility(View.GONE);
                                dis_ib2.setVisibility(View.VISIBLE);
                                flag = false;

                            }else if(distance > 30 || distance < 0  ){
                                dis_ll1.setVisibility(View.VISIBLE);
                                dis_tv1.setVisibility(View.GONE);
                                dis_iv1.setVisibility(View.GONE);
                                flag = false;
                                dis_btn1.setText("完成");
                            }
                        }
                        Log.i("ok","Major: "+beacon.getMajor()+" Minor: "+beacon.getMinor());
                        Log.i("ok", "A beacon is " + distance + "m away. "+beacon.getMajor());
                        Log.i("ok", "TXPower: "+beacon.getMeasuredPower());

                    }
                }


                // Handle here the discovered beacons list of the region.
                // You can for example calculate the distance with the RSSI.
                // You can also get the proximity value with RegionUtils.computeProximity().
            }
        });









    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);

        }
    };
    private TimerTask task = new TimerTask(){
        @Override
        public void run(){
            if (startflag){
                tsec++;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(beaconRegion);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(beaconRegion);

        super.onPause();
    }



}

