package com.example.cris.mytabs;

import android.graphics.Bitmap;

import java.net.URI;

/**
 * Created by cris on 27/02/17.
 */

public class Specialist {

    private String name;
    private int img;

    public Specialist(String name, int url){
        this.name=name;
        this.img=url;
    }

    public String getName(){
        return name;
    }

    public int getImg(){
        return img;
    }


}
