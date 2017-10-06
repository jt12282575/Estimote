package com.example.dada.estimote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;
import android.widget.Toast;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Q3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Q3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Q3 extends PageView{
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private GlobalVariable globalVariable;
    private ListView cdlv;
    private ArrayList<HashMap<String, Object>> Item;
    public Q3(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_q3, null);
        addView(view);
        cdlv = (ListView)findViewById(R.id.frag3_lv1);
        Item = new ArrayList<HashMap<String, Object>>();
        beaconManager = new BeaconManager(context);
        beaconRegion = new BeaconRegion("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        final ListAdapter Btnadapter = new ListAdapter(
                context,
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


    }

    @Override
    public void refreshView() {

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
//            Toast.makeText(getContext(),"Get major: "+globalVariable.major ,Toast.LENGTH_LONG).show();
//            Intent intent = new Intent();
//            intent.setClass(ConnectDevice.this, MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    };

}
