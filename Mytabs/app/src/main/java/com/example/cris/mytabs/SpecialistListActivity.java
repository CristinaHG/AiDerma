package com.example.cris.mytabs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class SpecialistListActivity extends ActionBarActivity {

    private RecyclerView recView;

    private ArrayList<Specialist> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        data=new ArrayList<Specialist>();
        data.add(0,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(1,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(2,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(3,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(4,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(5,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(6,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(7,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(8,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(9,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(10,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(11,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(12,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(13,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(14,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(15,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(16,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));
        data.add(17,new Specialist("Dr. Kathleen J. Smith","http://dermpathatlanta.com/wp-content/uploads/2014/05/Dr-Kathleen-Smith_for-web.jpg"));

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
