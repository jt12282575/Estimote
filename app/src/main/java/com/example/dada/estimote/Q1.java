package com.example.dada.estimote;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Q1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Q1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Q1 extends PageView{
    public Q1(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_q2, null);

        addView(view);
    }

    @Override
    public void refreshView() {

    }
}