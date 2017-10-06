package com.example.dada.estimote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dada on 2017/10/5.
 */

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Ans> mData;

    public ReAdapter(Context context, ArrayList<Ans> data) {
        this.mContext = context;
        this.mData = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.itemlayout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.tv_date = (TextView) view.findViewById(R.id.item_tv_date);
        holder.tv_score = (TextView) view.findViewById(R.id.item_tv_score);
        holder.tv_answer = (TextView) view.findViewById(R.id.item_tv_ans);
        holder.tv_ans1 = (TextView) view.findViewById(R.id.item_tv_ans1);
        holder.tv_ans2 = (TextView) view.findViewById(R.id.item_tv_ans2);
        holder.tv_ans3 = (TextView) view.findViewById(R.id.item_tv_ans3);
        holder.tv_ans4 = (TextView) view.findViewById(R.id.item_tv_ans4);
        holder.tv_ans5 = (TextView) view.findViewById(R.id.item_tv_ans5);
        holder.tv_ans6 = (TextView) view.findViewById(R.id.item_tv_ans6);
        holder.tv_ans7 = (TextView) view.findViewById(R.id.item_tv_ans7);
        holder.tv_ans8 = (TextView) view.findViewById(R.id.item_tv_ans8);
        holder.tv_ans9 = (TextView) view.findViewById(R.id.item_tv_ans9);
        holder.tv_ans10 = (TextView) view.findViewById(R.id.item_tv_ans10);
        holder.tv_ans11 = (TextView) view.findViewById(R.id.item_tv_ans11);
        holder.tv_ans12 = (TextView) view.findViewById(R.id.item_tv_ans12);
        holder.tv_ans13 = (TextView) view.findViewById(R.id.item_tv_ans13);
        holder.tv_ans14 = (TextView) view.findViewById(R.id.item_tv_ans14);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ans ans = mData.get(position);
        String[] ansSplit = ans.answer.split(",");
        holder.tv_date.setText("日期: "+ans.date);
        holder.tv_score.setText("分數: "+ans.score);
        if (Integer.parseInt(ans.score)>9) {
            holder.tv_answer.setText("情況: 記憶力似乎衰退嚴重，請尋求家人及醫師協助！");
        }else if(Integer.parseInt(ans.score)<=9 && Integer.parseInt(ans.score)>=5){
            holder.tv_answer.setText("情況: 記憶力有點衰退摟，再加油！");
        }else if(Integer.parseInt(ans.score)<5 && Integer.parseInt(ans.score)>=0){
            holder.tv_answer.setText("情況: 記憶情形良好，繼續保持！");
        }
        holder.tv_ans1.setText("答案一: "+ansSplit[0]);
        holder.tv_ans2.setText("答案二: "+ansSplit[1]);
        holder.tv_ans3.setText("答案三: "+ansSplit[2]);
        holder.tv_ans4.setText("答案四: "+ansSplit[3]);
        holder.tv_ans5.setText("答案五: "+ansSplit[4]);
        holder.tv_ans6.setText("答案六: "+ansSplit[5]);
        holder.tv_ans7.setText("答案七: "+ansSplit[6]);
        holder.tv_ans8.setText("答案八: "+ansSplit[7]);
        holder.tv_ans9.setText("答案九: "+ansSplit[8]);
        holder.tv_ans10.setText("答案十: "+ansSplit[9]);
        holder.tv_ans11.setText("答案十一: "+ansSplit[10]);
        holder.tv_ans12.setText("答案十二: "+ansSplit[11]);
        holder.tv_ans13.setText("答案十三: "+ansSplit[12]);
        holder.tv_ans14.setText("答案十四: "+ansSplit[13]);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date;
        public TextView tv_score;
        public TextView tv_answer;
        public TextView tv_ans1;
        public TextView tv_ans2;
        public TextView tv_ans3;
        public TextView tv_ans4;
        public TextView tv_ans5;
        public TextView tv_ans6;
        public TextView tv_ans7;
        public TextView tv_ans8;
        public TextView tv_ans9;
        public TextView tv_ans10;
        public TextView tv_ans11;
        public TextView tv_ans12;
        public TextView tv_ans13;
        public TextView tv_ans14;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
