package com.example.dada.estimote;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

public class Forfirebase extends AppCompatActivity {
    private Button btnreg;
    private Button btnbirth;
    private TextView tvbirth;
    private int mYear, mMonth, mDay;
    private EditText regedid;
    private EditText regedpass;
    private EditText regedname;
    private String date;
    private Button btnbacklogin;

    private Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forfirebase);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://estimote-98d6e.firebaseio.com/");
        btnreg = (Button)findViewById(R.id.btnreg);
        btnbacklogin = (Button)findViewById(R.id.btnbacklogin);

        btnbirth = (Button)findViewById(R.id.regbtnbirth);
        tvbirth = (TextView)findViewById(R.id.regtvbirth);
        regedid = (EditText)findViewById(R.id.regedid);
        regedpass = (EditText)findViewById(R.id.regedpass);
        regedname = (EditText)findViewById(R.id.regedname);

        tvbirth.setText("1990 年 1 月 1 日");
        date = "1990-1-1";
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Firebase mRefChild = mref.child("Name").child("name1");
//
//                mRefChild.setValue("Dadada");
                if (!regedid.getText().toString().equals("") && !regedpass.getText().toString().equals("") && !regedname.getText().toString().equals("")) {
                    mref.child("users").child(regedid.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                new AlertDialog.Builder(Forfirebase.this)
                                        .setTitle("提醒")//設定視窗標題
                                        .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                        .setMessage("此手機號碼已被註冊")//設定顯示的文字
                                        .setPositiveButton("了解",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })//設定結束的子視窗
                                        .show();//呈現對話視窗

                            }else{
                                writeNewUser(regedid.getText().toString(),regedpass.getText().toString(),regedname.getText().toString(),date);
                                Intent intent = new Intent();
                                intent.setClass(Forfirebase.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });


                }else{
                    new AlertDialog.Builder(Forfirebase.this)
                            .setTitle("提醒")//設定視窗標題
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
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

        btnbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnbacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent();
                intent5.setClass(Forfirebase.this, Login.class);
                startActivity(intent5);
                finish();
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
                        tvbirth.setText(year + " 年 " + (monthOfYear + 1) + " 月 "
                                + dayOfMonth + "日");
                        date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

                    }
                }, 1990, 0, 1);
        dpd.show();

    }
}
