package com.example.cris.mytabs;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SpecialistListActivity extends ActionBarActivity {

    private RecyclerView recView;

    private ArrayList<Specialist> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        data=new ArrayList<Specialist>();
        data.add(0,new Specialist("Dr. Olga Robinson", R.drawable.specialist_olga_robinson));
        data.add(1,new Specialist("Dr. Kathleen J. Smith",R.drawable.specialist_kathleen_smith));
        data.add(2,new Specialist("Dr.Anna Chapas",R.drawable.specialist_anna_chapas));
        data.add(3,new Specialist("Dr. Brian Johnson",R.drawable.specialist_johnson));
        data.add(4,new Specialist("Dr. Olga Robinson", R.drawable.specialist_olga_robinson));
        data.add(5,new Specialist("Dr. Kathleen J. Smith",R.drawable.specialist_kathleen_smith));
        data.add(6,new Specialist("Dr.Anna Chapas",R.drawable.specialist_anna_chapas));
        data.add(7,new Specialist("Dr. Brian Johnson",R.drawable.specialist_johnson));


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.setHasFixedSize(true);

        final AdapterSpecialist adapter = new AdapterSpecialist(data);

        recView.setAdapter(adapter);

        recView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

    }

}
