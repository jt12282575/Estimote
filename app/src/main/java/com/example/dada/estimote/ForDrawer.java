package com.example.dada.estimote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForDrawer extends AppCompatActivity {

    private DrawerLayout DL;
    private FrameLayout FL;
    protected NavigationView NV;
    protected Toolbar toolbar;
    private GlobalVariable globalVariable;
    private SharedPreferences settings;
    protected int CurrentMenuItem = 0;//紀錄目前User位於哪一個項目
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_drawer);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentView = (TextView) findViewById(R.id.content_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(ForDrawer.this, menuItem.getTitle() + " pressed", Toast.LENGTH_LONG).show();
                contentView.setText(menuItem.getTitle());

                menuItem.setChecked(true);
                int fid = menuItem.getItemId();
                switch (fid){
                    case R.id.navigation_item_1:
                        Toast.makeText(ForDrawer.this, "item1", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_item_2:
                        Toast.makeText(ForDrawer.this, "item2", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_item_3:
                        Toast.makeText(ForDrawer.this, "item3", Toast.LENGTH_LONG).show();
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.drawer_open , R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super .onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super .onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }*/

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        DL = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_for_drawer, null);
        FL = (FrameLayout) DL.findViewById(R.id.content_frame);
        NV = (NavigationView)DL.findViewById(R.id.navigation_view);
        getLayoutInflater().inflate(layoutResID, FL, true);
        super.setContentView(DL);
        toolbar = (Toolbar) findViewById(R.id.ToolBar);
        setUpNavigation();
        globalVariable = (GlobalVariable)ForDrawer.this.getApplicationContext();
        settings = getSharedPreferences("Est", 0);
    }

    private void setUpNavigation() {
        // Set navigation item selected listener
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(!(menuItem == NV.getMenu().getItem(CurrentMenuItem))) {//判斷使者者是否點擊當前畫面的項目，若不是，根據所按的項目做出分別的動作
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_item_1:
                            Intent intent = new Intent();
                            intent.setClass(ForDrawer.this, PersonInfo.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.navigation_item_2:
                            Intent intent2 = new Intent();
                            intent2.setClass(ForDrawer.this, MainActivity.class);
                            startActivity(intent2);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.navigation_item_3:
                            Intent intent3 = new Intent();
                            intent3.setClass(ForDrawer.this, Question.class);
                            startActivity(intent3);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.navigation_item_4:
                            Intent intent4 = new Intent();
                            intent4.setClass(ForDrawer.this, Elderclock.class);
                            startActivity(intent4);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.navigation_item_5:
                            new AlertDialog.Builder(ForDrawer.this)
                                    .setTitle("登出")
                                    .setMessage("你確定要登出嗎？")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            globalVariable.UserID = "";
                                            globalVariable.Name = "";
                                            globalVariable.Birthday = "";
                                            globalVariable.major = -1;
                                            globalVariable.minor = -1;
                                            settings.edit().putString("id","").putString("name","").putString("birthday","").putString("once","-1").putInt("major",-1).putInt("minor",-1).commit();
                                            Intent intent5 = new Intent();
                                            intent5.setClass(ForDrawer.this, Login.class);
                                            startActivity(intent5);
                                            overridePendingTransition(0, 0);
                                            finish();

                                        }

                                    })
                                    .setNegativeButton("否", null)
                                    .show();

                            break;
                    }
                }
                else {//點擊當前項目時，收起Navigation
                    DL.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

    }
    public void setUpToolBar() {//設置ToolBar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DL.openDrawer(GravityCompat.START);
            }
        });
        //設定當使用者點擊ToolBar中的Navigation Icon時，Icon會隨著轉動
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle( this, DL, toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super .onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {

                super .onDrawerOpened(drawerView);
            }
        };
        DL.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
