package com.example.dada.estimote;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Q5.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Q5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Q5 extends PageView{
    private Button btn1;


    public Q5(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_q5, null);
        addView(view);
        btn1 = (Button)findViewById(R.id.frag5_btn1);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PersonInfo.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public void refreshView() {

    }
}