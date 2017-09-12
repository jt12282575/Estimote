package com.example.dada.estimote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class Question extends ForDrawer {
    private TextView tv1;
    private TextView testscore;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Resources res;
    private int ques_num;
    private int[] ans;
    private int score;
    private String[] quesArray;
    private GlobalVariable globalVariable;
    private int mYear,mMonth,mDay,weekday;
    private SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        globalVariable = (GlobalVariable)Question.this.getApplicationContext();
        toolbar.setTitle("問卷測驗");
        setUpToolBar();
        CurrentMenuItem = 2;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態

        settings = getSharedPreferences("Est", 0);
        String name = settings.getString("once", "");
        if(name.equals("")) {
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
            Intent intent = new Intent();
            intent.setClass(Question.this, Login.class);
            startActivity(intent);
            finish();

        }


        score = 0;
        BindView();
        ans = new int[14];
//        testscore.setText(Integer.toString(score));
        quesArray = res.getStringArray(R.array.question_array);
        ques_num = 0;

        tv1.setText(quesArray[0]);
        btn1.setText("客廳");
        btn2.setText("廚房");
        btn3.setText("樂園");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans[ques_num] = 0;
                if (ques_num<14) {
                    ques_num ++;
                    switchQues(0);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans[ques_num] = 1;
                if (ques_num<14) {
                    ques_num ++;
                    switchQues(1);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans[ques_num] = 2;
                if (ques_num<14) {
                    ques_num ++;
                    switchQues(2);
                }
            }
        });


    }

    private void BindView() {
        tv1 = (TextView)findViewById(R.id.ques_tv1);
        btn1 = (Button) findViewById(R.id.ques_btn1);
        btn2 = (Button) findViewById(R.id.ques_btn2);
        btn3 = (Button) findViewById(R.id.ques_btn3);
        testscore = (TextView)findViewById(R.id.testscore);
        res = getResources();

    }

    private void switchQues(int choose){
        switch (ques_num){
            case 1:
                if(choose==2) score++;
                tv1.setText(quesArray[1]);
                btn1.setText("王大明");
                btn2.setText(globalVariable.Name);// 答案 姓名
                btn3.setText("王小明");
                break;
            case 2:
                if(choose!=1) score++;
                tv1.setText(quesArray[2]);
                btn1.setText("記得");
                btn2.setText("不記得");
                btn3.setVisibility(View.GONE);


                break;
            case 3:
                tv1.setText(quesArray[3]);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                weekday = c.get(Calendar.DAY_OF_WEEK);
                btn1.setText(Integer.toString(mYear)+" 年 "+ Integer.toString(mMonth+1)+" 月 "+Integer.toString((mDay+1)%28)+" 號");// 答案 ， 今天幾號
                btn2.setText(Integer.toString(mYear)+" 年 "+ Integer.toString(mMonth+1)+" 月 "+Integer.toString(mDay)+" 號");// 答案 ， 今天幾號
                btn3.setVisibility(View.VISIBLE);
                btn3.setText(Integer.toString(mYear)+" 年 "+ Integer.toString(mMonth+1)+" 月 "+Integer.toString((mDay+2)%28)+" 號");// 答案 ， 今天幾號
                break;
            case 4:
                if(choose!=1) score++;
                tv1.setText(quesArray[4]);
                btn1.setText("家裡");
                btn2.setText("室外");
                btn3.setVisibility(View.GONE);
                break;
            case 5:
                tv1.setText(quesArray[5]);
                btn3.setVisibility(View.VISIBLE);


                btn1.setText("禮拜"+setWeekDay((weekday)%7+1));
                btn2.setText("禮拜"+setWeekDay(weekday));// 答案, 今天星期幾
                btn3.setText("禮拜"+setWeekDay((weekday)%7+2));
                break;
            case 6:
                tv1.setText(quesArray[6]);
                if(choose!=1) score++;
                final Calendar d = Calendar.getInstance();
                String birth = globalVariable.Birthday;
                String[] AfterSplit = birth.split("-");
                int age = d.get(Calendar.YEAR) - Integer.parseInt(AfterSplit[0]);
                btn1.setText(Integer.toString(age-4));
                btn2.setText(Integer.toString(age-2));
                btn3.setText(Integer.toString(age));//答案  正確的歲數
                break;
            case 7:
                if(choose!=2) score++;
                tv1.setText(quesArray[7]);
                btn1.setText(globalVariable.UserID);//答案 正確的電話
                btn2.setText("0918222444");
                btn3.setText("0916777888");
                break;
            case 8:
                if(choose!=0) score++;
                tv1.setText(quesArray[8]);
                String birthday = globalVariable.Birthday;
                String[] sp = birthday.split("-");
                btn1.setText("1992 年 1 月 16 日");
                btn2.setText(sp[0] + " 年 "+sp[1] + " 月 "+sp[2]+" 日 ");// 答案 生日
                btn3.setText("1992 年 1 月 17 日");
                break;
            case 9:
                if(choose!=1) score++;
                tv1.setText(quesArray[9]);
                btn1.setText("蔡總統");
                btn2.setText("馬總統");// 答案 前總統
                btn3.setText("陳總統");
                break;
            case 10:
                if(choose!=1) score++;
                tv1.setText(quesArray[10]);
                btn1.setText("蔡總統");// 答案 現任總統
                btn2.setText("王總統");
                btn3.setText("馬總統");
                break;
            case 11:
                if(choose!=0) score++;
                tv1.setText(quesArray[11]);
                btn1.setText("徐志摩");
                btn2.setText("林海音");// 答案 選一位女性
                btn3.setText("金城武");
                break;
            case 12:
                if(choose!=1) score++;
                tv1.setText(quesArray[12]);
                btn1.setText("16");
                btn2.setText("17"); // 答案 20 - 3
                btn3.setText("18");
                break;
            case 13:
                if(choose!=1) score++;
                tv1.setText(quesArray[13]);
                btn1.setText("15");
                btn2.setText("14");// 答案 20 - 3 再 - 3
                btn3.setText("13");
                break;
            case 14:
                if(choose!=1) score++;
                new AlertDialog.Builder(Question.this)
                        .setTitle("得分")//設定視窗標題
                        .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                        .setMessage("得到的分數是 "+Integer.toString(score)+" 分\n接著去找鑰匙摟")//設定顯示的文字
                        .setPositiveButton("確定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent5 = new Intent();
                                intent5.setClass(Question.this, MainActivity.class);
                                startActivity(intent5);
                                overridePendingTransition(0, 0);
                                finish();

                            }
                        })//設定結束的子視窗
                        .show();//呈現對話視窗

                break;
            default:
                break;
        }

//        testscore.setText(Integer.toString(score));
    }
    private String setWeekDay(int wd){
        String chinweekday = "";
        switch(wd){
            case 1:
                return "日";
            case 2:
                return "ㄧ";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                break;
        }

        return chinweekday;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }
}
