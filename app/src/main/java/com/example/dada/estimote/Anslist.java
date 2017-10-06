package com.example.dada.estimote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Anslist extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anslist);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Ans> arr = new ArrayList<>();
        arr.add(new Ans("0918","12","1,2,3,1,2,3,2,3,1,1,2,1,2,3,1"));
        arr.add(new Ans("0919","17","1,2,3,1,2,3,2,3,1,1,2,1,2,3,2"));
//        recyclerView.setHasFixedSize(true);

        ReAdapter adapter = new ReAdapter(this,arr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}
