package com.example.dada.estimote;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dada on 2017/8/30.
 */

public class MyFragAdapter extends FragmentPagerAdapter {
    public MyFragAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public Fragment getItem(int position) {
        /*switch (position) {
            case 0:
                return MyFragment.newInstance(position);
            case 1:
                return MyFragment.newInstance(position);
            case 2:
                return MyFragment.newInstance(position);
            default:
                return null;
        }*/
        return null;
    }
}
