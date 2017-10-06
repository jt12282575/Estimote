package com.example.dada.estimote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import static com.example.dada.estimote.R.layout.toolbar;

public class Lobby extends AppCompatActivity {
    private GlobalVariable globalVariable;
    private SharedPreferences settings;
    private Button btnlog;
    private Button btnreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby);
        btnlog = (Button) findViewById(R.id.lobby_btnlog);
        btnreg = (Button) findViewById(R.id.lobby_btnreg);
        globalVariable = (GlobalVariable)Lobby.this.getApplicationContext();
        settings = getSharedPreferences("Est", 0);//登入過之後自動登入
        String name = settings.getString("once", "");
        if(name.equals("")) {
            Intent intent = new Intent();
            intent.setClass(Lobby.this, PersonInfo.class);
            startActivity(intent);
            finish();
            globalVariable.UserID = settings.getString("id", "");
            globalVariable.Name = settings.getString("name", "");
            globalVariable.Birthday = settings.getString("birthday", "");
            globalVariable.major = settings.getInt("major",-1);
            globalVariable.minor = settings.getInt("minor",-1);
        }
        else{
            /*Intent intentgoto = new Intent(Login.this,Lobby.class);
            intentgoto.putExtra("id", name);
            startActivity(intentgoto);
            onDestroy();*/

        }


        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Lobby.this, Login.class);
                startActivity(intent);
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Lobby.this, Forfirebase.class);
                startActivity(intent);
            }
        });
        /*toolbar.setTitle("首頁");
        setUpToolBar();
        CurrentMenuItem = 0;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        globalVariable = (GlobalVariable)Lobby.this.getApplicationContext();*/
    }


}
