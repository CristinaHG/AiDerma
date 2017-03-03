package com.example.cris.mytabs;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    File imagesFolder = new File(Environment.getExternalStorageDirectory(), "AiDerma");

//    private final String image_titles[] = imagesFolder.list();
//
//    private ArrayList<String> images_AbsolutePaths =createPathsArray();

    private RecyclerView recView;

    private ArrayList<CreateList> data;



    private ArrayList<String> createPathsArray(File folder ){

        ArrayList<String> image_paths =new ArrayList<>();

        for(int i=0; i< folder.list().length;i++){
            image_paths.add(imagesFolder.getAbsolutePath()+"/"+folder.list()[i]);
        }
        return image_paths;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        data=new ArrayList<CreateList>();
        String image_titles[] = imagesFolder.list();
        ArrayList<String> images_AbsolutePaths =createPathsArray(imagesFolder);
        data = prepareData(image_titles,images_AbsolutePaths);


        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recView.setLayoutManager(layoutManager);

//        ArrayList<CreateList> createLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), data);
        recView.setAdapter(adapter);

    }

    private ArrayList<CreateList> prepareData(String[] t,ArrayList<String> s){

        ArrayList<CreateList> theimage = new ArrayList<>();

        for(int i = 0; i< t.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(t[i]);
            createList.setImage_AbsolutePath(s.get(i));
            theimage.add(createList);
        }
        return theimage;
    }
}
