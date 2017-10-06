package com.example.dada.estimote;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Forfirebase extends AppCompatActivity {
    private Button btnreg;
    private Button btnbirth;
    private Button btntest;
    private TextView tvbirthtext;
    private TextView tvbirthval;
    private int mYear, mMonth, mDay;
    private EditText regedid;
    private EditText regedpass;
    private EditText regedname;
    private String date;
    private ProgressDialog psDialog;

    private final String url = "https://estimote-98d6e.firebaseio.com/ques";
    private Firebase myFirebaseRef;

    private Firebase mref;
    private Firebase mreftest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forfirebase);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://estimote-98d6e.firebaseio.com/");
        btnreg = (Button)findViewById(R.id.btnreg);

        btnbirth = (Button)findViewById(R.id.regbtnbirth);
        tvbirthtext = (TextView)findViewById(R.id.reg_tvbirthtext);
        tvbirthval = (TextView)findViewById(R.id.regtvbirthval);
        regedid = (EditText)findViewById(R.id.regedid);
        regedpass = (EditText)findViewById(R.id.regedpass);
        regedname = (EditText)findViewById(R.id.regedname);
        btntest = (Button)findViewById(R.id.btntestfb);
        Toolbar mytoolbar = (Toolbar)findViewById(R.id.ToolBar);
        setSupportActionBar(mytoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        actionBar.setTitle("註冊");
        date = "1990-1-1";
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Firebase mRefChild = mref.child("Name").child("name1");
//
//                mRefChild.setValue("Dadada");
                Log.i("ok","id: "+regedid.getText().toString());
                Log.i("ok","id: "+regedpass.getText().toString());
                Log.i("ok","id: "+regedname.getText().toString());
                if (!regedid.getText().toString().equals("") && !regedpass.getText().toString().equals("") && !regedname.getText().toString().equals("")) {
                    psDialog = ProgressDialog.show(Forfirebase.this, "註冊", "資料傳輸中，請稍候...");
                    mref.child("users").child(regedid.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                psDialog.dismiss();
                                new AlertDialog.Builder(Forfirebase.this,R.style.AlertDialogCustom)
                                        .setTitle("提醒")//設定視窗標題
                                        .setIcon(R.mipmap.startlogo2)//設定對話視窗圖示
                                        .setMessage("此手機號碼已被註冊")//設定顯示的文字
                                        .setPositiveButton("了解",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })//設定結束的子視窗
                                        .show();//呈現對話視窗

                            }else{
                                psDialog.dismiss();
                                writeNewUser(regedid.getText().toString(),regedpass.getText().toString(),regedname.getText().toString(),date);
                                new AlertDialog.Builder(Forfirebase.this,R.style.AlertDialogCustom)
                                        .setTitle("註冊成功")//設定視窗標題
                                        .setIcon(R.mipmap.startlogo2)//設定對話視窗圖示
                                        .setMessage("按下確定後去登入頁面")//設定顯示的文字
                                        .setPositiveButton("確定",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setClass(Forfirebase.this, Login.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })//設定結束的子視窗
                                        .show();//呈現對話視窗

                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });


                }else{
                    new AlertDialog.Builder(Forfirebase.this,R.style.AlertDialogCustom)
                            .setTitle("提醒")//設定視窗標題
                            .setIcon(R.mipmap.startlogo2)//設定對話視窗圖示
                            .setMessage("個人資料尚未填寫完整")//設定顯示的文字
                            .setPositiveButton("了解",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })//設定結束的子視窗
                            .show();//呈現對話視窗
                }
            }
        });

        tvbirthval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        mreftest = new Firebase("https://estimote-98d6e.firebaseio.com/");
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference cineIndustryRef = rootRef.child("ques").child("dada").push();
                String key = cineIndustryRef.getKey();
                Map<String, Object> map = new HashMap<>();
                map.put("date", "2017161");
                map.put("ans", "1,2,3,1,2,3");
                map.put("score", "87");
                cineIndustryRef.updateChildren(map);*/  //write data

                myFirebaseRef = new Firebase(url);
                myFirebaseRef.child("dada").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {// for each to get each query
                            // dataSnapshot is the "issue" node with all children with id 0
                                for (DataSnapshot issue : snapshot.getChildren()) {
                                    // do something with the individual "issues"
//                                    User user = snapshot.getValue(User.class);
                                    String ans = (String) issue.child("ans").getValue();
                                    String date = (String) issue.child("date").getValue();
                                    String score = (String) issue.child("score").getValue();
                                    Log.i("ok","ans: "+ans);
                                    Log.i("ok","date: "+date);
                                    Log.i("ok","score: "+score);
                                }




                        }else{

                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });



            }
        });

        /*mref.addListenerForSingleValueEvent(new ValueEventListener() { // 1 check if duplicate
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child("12").exists()) {
                        //do ur stuff
                    } else {
                        //do something
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });// 1 */


    }
    private void writeNewUser(String userId,String password, String name, String birthday) {
        User user = new User(password,name, birthday);

        mref.child("users").child(userId).setValue(user);
    }

    private void showDatePickerDialog(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // 跳出日期選擇器
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 完成選擇，顯示日期
                        tvbirthval.setText(year + " 年 " + (monthOfYear + 1) + " 月 "
                                + dayOfMonth + "日");
                        date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

                    }
                }, 1990, 0, 1);
        dpd.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
