package com.example.cris.mytabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private static final int STATIC_INTEGER_VALUE = 1;
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

        //getting mole description from Image_BodyPart Activity
//        Intent descriptionIntent=getIntent();
//
//        if(descriptionIntent!=null){
//            String description=descriptionIntent.getStringExtra("mole_place");
//            int photo_identifier=descriptionIntent.getIntExtra("photo_id",-1);
//            if(description!=null) {
//                //Log.i("DemoRecView", description + photo_identifier);
//                CreateList newData=new CreateList();
//                newData.setImage_title(data.get(photo_identifier).getImage_title());
//                newData.setImage_AbsolutePath(data.get(photo_identifier).getImage_AbsolutePath());
//                newData.setDescription(description);
//
//                data.set(photo_identifier,newData);
//            }
//        }

        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recView.setLayoutManager(layoutManager);

//        ArrayList<CreateList> createLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), data);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("DemoRecView", "Pulsado el elemento " + recView.getChildAdapterPosition(v));
                Intent intent = new Intent(v.getContext(), Image_BodyPart.class);
                int photo_id=recView.getChildAdapterPosition(v);
                intent.putExtra("photo id",photo_id);
                startActivityForResult(intent,STATIC_INTEGER_VALUE);
            }
        });

        recView.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataDescrip) {
        super.onActivityResult(requestCode, resultCode, dataDescrip);
        switch(requestCode) {
            case (STATIC_INTEGER_VALUE) : {
                if (resultCode == Activity.RESULT_OK) {
                    String description=dataDescrip.getStringExtra("mole_place");
                    int photo_identifier=dataDescrip.getIntExtra("photo_id",-1);

                    CreateList newData=new CreateList();
                    newData.setImage_title(data.get(photo_identifier).getImage_title());
                    newData.setImage_AbsolutePath(data.get(photo_identifier).getImage_AbsolutePath());
                    newData.setDescription(description);

                    data.set(photo_identifier,newData);
                }
                break;
            }
        }
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
