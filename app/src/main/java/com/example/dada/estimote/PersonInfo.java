package com.example.dada.estimote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class PersonInfo extends ForDrawer {
    private GlobalVariable globalVariable;
    private TextView tvname;
    private TextView tvphone;
    private TextView tvbirth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        globalVariable = (GlobalVariable)PersonInfo.this.getApplicationContext();
        toolbar.setTitle("個人資料");
        setUpToolBar();
        CurrentMenuItem = 0;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        tvname = (TextView)findViewById(R.id.info_tvname);
        tvphone = (TextView)findViewById(R.id.info_tvphone);
        tvbirth = (TextView)findViewById(R.id.info_tvbirth);
        tvname.setText(globalVariable.Name);
        tvbirth.setText(globalVariable.Birthday);
        tvphone.setText(globalVariable.UserID);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }
}
