package com.example.dada.estimote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class PersonInfo extends ForDrawer {
    private GlobalVariable globalVariable;
    private SharedPreferences settings;
    private TextView tvname;
    private TextView tvphone;
    private TextView tvbirth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_person_info);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        globalVariable = (GlobalVariable)PersonInfo.this.getApplicationContext();
        settings = getSharedPreferences("Est", 0);

        toolbar.setTitle("個人資料");
        setUpToolBar();
        CurrentMenuItem = 0;//目前Navigation項目位置
//        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        int size = NV.getMenu().size();
        for (int i = 0; i < size; i++) {
            NV.getMenu().getItem(i).setChecked(false);
        }
        tvname = (TextView)findViewById(R.id.info_tvname);
        tvphone = (TextView)findViewById(R.id.info_tvphone);
        tvbirth = (TextView)findViewById(R.id.info_tvbirth);
        tvname.setText(globalVariable.Name);
        tvbirth.setText(globalVariable.Birthday);
        tvphone.setText(globalVariable.UserID);


    }

    private void showDia(){
        new AlertDialog.Builder(PersonInfo.this,R.style.AlertDialogCustom)
                .setTitle("登出")//設定視窗標題
                .setIcon(R.mipmap.startlogo2)//設定對話視窗圖示
                .setMessage("是否要登出？")//設定顯示的文字
                .setNegativeButton("否",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("是",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        globalVariable.UserID = "";
                        globalVariable.Name = "";
                        globalVariable.Birthday = "";
                        globalVariable.major = -1;
                        globalVariable.minor = -1;
                        settings.edit().putString("id","").putString("name","").putString("birthday","").putString("once","-1").putInt("major",-1).putInt("minor",-1).commit();
                        Intent intent5 = new Intent();
                        intent5.setClass(PersonInfo.this, Lobby.class);
                        startActivity(intent5);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                })//設定結束的子視窗
                .show();//呈現對話視窗


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {

           showDia();

        }
        return true;
    }

}
