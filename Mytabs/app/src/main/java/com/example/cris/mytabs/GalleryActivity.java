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

    private final String image_titles[] = imagesFolder.list();

    private ArrayList<String> images_AbsolutePaths =createPathsArray();



    private ArrayList<String> createPathsArray(){

        ArrayList<String> image_paths =new ArrayList<>();

        for(int i=0; i< image_titles.length;i++){
            image_paths.add(imagesFolder.getAbsolutePath()+"/"+image_titles[i]);
        }
        return image_paths;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> createLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();

        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_AbsolutePath(images_AbsolutePaths.get(i));
            theimage.add(createList);
        }

        return theimage;
    }
}
