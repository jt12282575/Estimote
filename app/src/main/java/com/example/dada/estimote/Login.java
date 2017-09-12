package com.example.dada.estimote;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Paint;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends ForDrawer {
    private EditText edid;
    private EditText edpass;
    private Button btnlogin;
    private GlobalVariable globalVariable;
    private Button btnreg;
    private SharedPreferences settings;


    private Firebase myFirebaseRef;
    private final String url = "https://estimote-98d6e.firebaseio.com/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*toolbar.setTitle("登入");
        setUpToolBar();
        CurrentMenuItem = 0;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態*/

        globalVariable = (GlobalVariable)Login.this.getApplicationContext();

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        settings = getSharedPreferences("Est", 0);


        String name = settings.getString("once", "");
        if(name.equals("")) {
            Intent intent = new Intent();
            intent.setClass(Login.this, PersonInfo.class);
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


        Firebase.setAndroidContext(this);
        edid = (EditText) findViewById(R.id.login_ed_id);
        edpass = (EditText) findViewById(R.id.login_ed_pass);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnreg = (Button) findViewById(R.id.btnreg);
        edid.requestFocus();


        edid.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下滑线属性
        edpass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下滑线属性

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edid.getText().toString().equals("") && !edpass.getText().toString().equals("")) {
                    myFirebaseRef = new Firebase(url);
                    Query query = myFirebaseRef.orderByChild("name").equalTo("Vivi");
                    DatabaseReference retrieveData = FirebaseDatabase.getInstance().getReference("Vivi");
                    myFirebaseRef.child(edid.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            // do some stuff once
                    /*for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    }*/
                           /* User user = snapshot.getValue(User.class);
                            user.setId("Vivi");
                            Log.i("ok", "name = " + user.getName() + " , birthday = " + user.getBirthday());*/
                            if (snapshot.exists()) {
                                // dataSnapshot is the "issue" node with all children with id 0
                                /*for (DataSnapshot issue : snapshot.getChildren()) {
                                    // do something with the individual "issues"

                                }*/
                                User user = snapshot.getValue(User.class);
                                Log.i("ok", "name = " + user.getName() + " , birthday = " + user.getBirthday());

                                if (edpass.getText().toString().equals(user.getPassword())) {
                                    globalVariable.UserID = edid.getText().toString();
                                    globalVariable.Name = user.getName();
                                    globalVariable.Birthday = user.getBirthday();
                                    globalVariable.major = -1;
                                    globalVariable.minor = -1;
                                    settings.edit().putString("id",edid.getText().toString()).putString("name",user.getName()).putString("birthday",user.getBirthday()).putString("once","").putInt("major",-1).putInt("minor",-1).commit();
                                    Toast.makeText(Login.this,"登入成功",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    intent.setClass(Login.this, MainFrag.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    edpass.requestFocus();
                                    edpass.setText("");
                                    showDia();
                                }

                            }else{
                                Log.i("ok","no this user");
                                edpass.setText("");
                                showDia();

                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });
                } else {
                    new AlertDialog.Builder(Login.this)
                            .setTitle("提醒")//設定視窗標題
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                            .setMessage("帳號或是密碼不可空白")//設定顯示的文字
                            .setPositiveButton("確定",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })//設定結束的子視窗
                            .show();//呈現對話視窗


                }
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Forfirebase.class);
                startActivity(intent);
                finish();
            }
        });







    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }*/
    private void showDia(){
        new AlertDialog.Builder(Login.this)
                .setTitle("提醒")//設定視窗標題
                .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                .setMessage("帳號或是密碼錯誤")//設定顯示的文字
                .setPositiveButton("確定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })//設定結束的子視窗
                .show();//呈現對話視窗


    }



}

