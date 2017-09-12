package com.example.dada.estimote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Q2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Q2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Q2 extends PageView{
    public Q2(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_q1, null);
        TextView textView = (TextView) view.findViewById(R.id.text_1);
        textView.setText("Page two");
        Button btnfrag = (Button)view.findViewById(R.id.fragbtn1);
        btnfrag.setVisibility(View.VISIBLE);
        addView(view);
        btnfrag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ConnectDevice.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public void refreshView() {

    }
}
