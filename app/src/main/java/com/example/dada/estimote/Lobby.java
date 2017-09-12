package com.example.dada.estimote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import static com.example.dada.estimote.R.layout.toolbar;

public class Lobby extends ForDrawer {
    private GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        toolbar.setTitle("首頁");
        setUpToolBar();
        CurrentMenuItem = 0;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        globalVariable = (GlobalVariable)Lobby.this.getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }
}
