package com.example.dada.estimote;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by dada on 2017/8/30.
 */

public abstract class PageView extends RelativeLayout {
    public PageView(Context context) {
        super(context);
    }
    public abstract void refreshView();
}
