package com.example.cris.mytabs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class VolunteersListActivity extends AppCompatActivity {
    private RecyclerView recView;

    private ArrayList<Specialist> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);
        data=new ArrayList<Specialist>();
        data.add(0,new Specialist("Voluntario 1", R.drawable.specialist_olga_robinson));
        data.add(1,new Specialist("Voluntario2",R.drawable.specialist_kathleen_smith));
        data.add(2,new Specialist("Voluntario 3",R.drawable.specialist_anna_chapas));
        data.add(3,new Specialist("Voluntario 4",R.drawable.specialist_johnson));
        data.add(4,new Specialist("Voluntario 5", R.drawable.specialist_olga_robinson));
        data.add(5,new Specialist("Voluntario 6",R.drawable.specialist_kathleen_smith));
        data.add(6,new Specialist("Voluntario 7",R.drawable.specialist_anna_chapas));
        data.add(7,new Specialist("Voluntario 8",R.drawable.specialist_johnson));

        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.setHasFixedSize(true);

        final AdapterSpecialist adapter = new AdapterSpecialist(data);

        recView.setAdapter(adapter);

        recView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }



}
