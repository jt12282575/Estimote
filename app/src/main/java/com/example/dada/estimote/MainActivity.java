package com.example.dada.estimote;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

public class MainActivity extends ForDrawer {
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private double distance;
    private GlobalVariable globalVariable;
    private TextView tvmain1;
    private TextView tvmain2;
    private int listflag = 0; // use for initial listview only for one time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalVariable = (GlobalVariable)MainActivity.this.getApplicationContext();
        toolbar.setTitle("鑰匙在哪");
        setUpToolBar();
        CurrentMenuItem = 1;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        tvmain1 = (TextView)findViewById(R.id.maintv1);
        tvmain2 = (TextView)findViewById(R.id.maintv2);
        distance = 0.0;
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
                        tvmain1.setText(Double.toString(distance));
                        if (distance<15 && distance>=5 ) {
                            tvmain2.setText("離鑰匙還很遠");
                        }else if(distance < 5 && distance>= 1){
                            tvmain2.setText("鑰匙在不遠處");
                        }else if(distance < 1){
                            tvmain2.setText("找到鑰匙了");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }


}

