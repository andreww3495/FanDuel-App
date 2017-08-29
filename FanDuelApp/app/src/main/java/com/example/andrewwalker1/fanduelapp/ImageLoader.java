package com.example.andrewwalker1.fanduelapp;

import android.app.Activity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by andrewwalker1 on 25/08/2017.
 * handle the loading of images into a given view
 */

public class ImageLoader {

    //required for picasso
    private Activity main;

    //defualt empty constructor
    public ImageLoader(){}

    //take activity as argument
    public ImageLoader(Activity activity){
        this.main = activity;
    }

    //load the image from the given url into the given image view
    public void loadIntoView(ImageView image_view, String image_path){
        Picasso.with(main)
                .load(image_path)
                .into(image_view);
    }
}
