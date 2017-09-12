package com.example.dada.estimote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dada on 2017/8/28.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bData = intent.getExtras();
        if(bData.get("msg").equals("playsong")) {
            Intent i = new Intent(context, ClockActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.e("HEHE", "運行了");
            Toast.makeText(context, "hehe it works", Toast.LENGTH_LONG).show();
            context.startActivity(i);
        }
    }
}
