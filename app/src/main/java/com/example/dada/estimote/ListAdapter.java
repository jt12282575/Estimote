package com.example.dada.estimote;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dada on 2017/9/4.
 */

public class ListAdapter extends BaseAdapter
{
    private LayoutInflater mLayInf;
    private Context mContext;
    ArrayList<HashMap<String, Object>> mItemList;
    private ImageButton ib;
    private TextView tvmajor;
    private TextView tvminor;
    private  AlertDialog.Builder dialog;
    private GlobalVariable globalVariable;
    private SharedPreferences settings;
    public ListAdapter(Context context, ArrayList<HashMap<String, Object>> itemList)
    {
        mLayInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemList = itemList;
        mContext = context;
        globalVariable = (GlobalVariable)mContext.getApplicationContext();
        settings = mContext.getSharedPreferences("Est", 0);

    }

    @Override
    public int getCount()
    {
        //取得 ListView 列表 Item 的數量
        return mItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        //取得 ListView 列表於 position 位置上的 Item
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        //取得 ListView 列表於 position 位置上的 Item 的 ID
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //設定與回傳 convertView 作為顯示在這個 position 位置的 Item 的 View。
        View v = mLayInf.inflate(R.layout.listviewlayout, parent, false);

//        ImageView imgView = (ImageView) v.findViewById(R.id.imgView);
         tvmajor = (TextView) v.findViewById(R.id.lvtv1);
         tvminor = (TextView) v.findViewById(R.id.lvtv2);
         ib = (ImageButton) v.findViewById(R.id.btdib1);
        ib.setOnClickListener(new ItemButton_Click(position));

        HashMap<String, Object> appInfo = mItemList.get(position);
//        imgView.setImageResource(Integer.valueOf(mItemList.get(position).get("Item icon").toString()));
//        txtView.setText(mItemList.get(position).get("Item title").toString());
        tvmajor.setText(appInfo.get("Major").toString());
        tvminor.setText(appInfo.get("Minor").toString());
//        tvminor.setText(mItemList.get(position).get("Minor").toString());
        return v;
    }


    class ItemButton_Click implements View.OnClickListener {
        private int position;

        ItemButton_Click(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View v) {
            int vid=v.getId();
            if (vid == ib.getId()){
                dialog   = new AlertDialog.Builder(mContext,R.style.AlertDialogCustom);
                dialog.setTitle("藍芽");
                dialog.setMessage("是否選擇配對");

                dialog.setNegativeButton("是",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        globalVariable.major = Integer.parseInt(tvmajor.getText().toString());
                        globalVariable.minor = Integer.parseInt(tvminor.getText().toString());
                        Toast.makeText(mContext,"Get major: "+tvmajor.getText().toString() ,Toast.LENGTH_LONG).show();
                        settings.edit().putInt("major",globalVariable.major).putInt("minor",globalVariable.minor).commit();
                        /*Intent intent = new Intent();
                        intent.setClass(mContext, MainActivity.class);
                        mContext.startActivity(intent);*/
                        ((Activity) mContext).finish();

                    }

                });
                dialog.setPositiveButton("否",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }

                });
                dialog.show();
            }

        }


        //   return null;
    }
}
