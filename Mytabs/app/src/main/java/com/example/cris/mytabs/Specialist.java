package com.example.cris.mytabs;

import android.graphics.Bitmap;

import java.net.URI;

/**
 * Created by cris on 27/02/17.
 */

public class Specialist {

    private String name;
    private String img;

    public Specialist(String name, String url){
        this.name=name;
        this.img=url;
    }

    public String getName(){
        return name;
    }

    public String getImg(){
        return img;
    }


}
