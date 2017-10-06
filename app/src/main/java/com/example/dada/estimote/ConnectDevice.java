package com.example.dada.estimote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.cloud.api.CloudCallback;
import com.estimote.coresdk.cloud.api.EstimoteCloud;
import com.estimote.coresdk.cloud.model.BeaconInfo;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.exception.EstimoteCloudException;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ConnectDevice extends AppCompatActivity {
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private GlobalVariable globalVariable;
    private int listflag = 0; // use for initial listview only for one time
    private ListView cdlv;
    private Button cdbtnskip;
    private ArrayList<HashMap<String, Object>> Item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_connect_device);
        globalVariable = (GlobalVariable)ConnectDevice.this.getApplicationContext();
        EstimoteSDK.initialize(this,"anti-loss-azv","d43f9b192d3dba4ea9030e70e65c1987");
        cdlv = (ListView) findViewById(R.id.cdlv);
        cdbtnskip = (Button)findViewById(R.id.cdbtnskip);

        Item = new ArrayList<HashMap<String, Object>>();
        beaconManager = new BeaconManager(this);
        beaconRegion = new BeaconRegion("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        final ListAdapter Btnadapter = new ListAdapter(
                ConnectDevice.this,
                Item
        );
        cdlv.setAdapter(Btnadapter);
        cdlv.setOnItemClickListener(listViewOnItemClickListener);
        beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(3), 0);
        beaconManager.setForegroundScanPeriod(TimeUnit.SECONDS.toMillis(3), 0);
        beaconManager.setRangingListener( new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
//                Log.i("ok", String.valueOf(list.get(0).getRssi()));
//                Log.i("ok", String.valueOf(list.get(0).getRssi()));

                for (Beacon beacon : list) {
//                    distance = Math.pow(10, (beacon.getMeasuredPower() - beacon.getRssi()) / 20.0);// 20 from 10 * n ,
                    // n is usually 2 - 2.5;
                    EstimoteCloud.getInstance().fetchBeaconDetails(beacon.getProximityUUID(),beacon.getMajor(),beacon.getMinor(), new CloudCallback<BeaconInfo>() {
                        @Override
                        public void success(BeaconInfo beaconInfo) {
                            Log.i("ok", String.valueOf(beaconInfo.name));
                        }

                        @Override
                        public void failure(EstimoteCloudException e) {
                            Log.i("ok", "BEACON INFO ERROR: " + e);
                        }
                    });
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("Major", beacon.getMajor());
                        map.put("Minor", beacon.getMinor());
                    if (!Item.contains(map)) {
                        Item.add(map);
                    }

                    Log.i("ok","Major: "+beacon.getMajor()+" Minor: "+beacon.getMinor());
//                    Log.i("ok", "A beacon is " + distance + "m away. "+beacon.getMajor());
                    Log.i("ok", "TXPower: "+beacon.getMeasuredPower());
                }
                /*if(listflag==0){

                    listflag++;
                }*/
                Btnadapter.notifyDataSetChanged();
//                Item.clear();


                // Handle here the discovered beacons list of the region.
                // You can for example calculate the distance with the RSSI.
                // You can also get the proximity value with RegionUtils.computeProximity().
            }
        });
        cdbtnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ConnectDevice.this, PersonInfo.class);
                startActivity(intent);
                finish();
            }
        });



        /*for(int i=0; i<20; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemName", "Name");
            map.put("ItemInfo", "Info");
            Item.add(map);
        }*/



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
    private AdapterView.OnItemClickListener listViewOnItemClickListener
            = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            TextView tvmajor = (TextView) view.findViewById(R.id.lvtv1);//tv1 major tv2 minor
            TextView tvminor = (TextView) view.findViewById(R.id.lvtv2);//tv1 major tv2 minor
            globalVariable.major = Integer.parseInt(tvmajor.getText().toString());
            globalVariable.minor = Integer.parseInt(tvminor.getText().toString());
            Toast.makeText(ConnectDevice.this,"Get major: "+globalVariable.major ,Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(ConnectDevice.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

}
