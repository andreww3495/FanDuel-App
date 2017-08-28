package com.example.andrewwalker1.fanduelapp;

import android.app.Activity;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by andrewwalker1 on 27/08/2017.
 */

public class Player {

    private ImageView image;
    private Activity main;

    private GameLogic game;


    private String player_name;
    private String image_url;
    private String player_id;

    private float fppg;

    public Player(Activity activity, GameLogic gl, String name, String url, String id, float fp){
        this.game = gl;
        this.main = activity;
        image = new ImageView(main);

        this.player_name = name;
        this.image_url = url;
        this.player_id = id;

        this.fppg = fp;

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(main, "Name = " + player_name + " fppg = " + getFppg(), Toast.LENGTH_SHORT).show();
                Log.d("PLayer", "Name = " + player_name + " fppg = " + getFppg());
                playerSelected();

            }
        });
    }

    public ImageView getImage(){
        return image;
    }

    public String getImageUrl(){
        return image_url;
    }

    public float getFppg(){
        return fppg;
    }

    public String getId(){
        return player_id;
    }

    public String getName(){
        return player_name;
    }

    public void playerSelected(){
        this.game.turnTaken(this);
    }

    public void removeListeners(){
        this.image.setOnClickListener(null);
    }

}
