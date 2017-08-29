package com.example.andrewwalker1.fanduelapp;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by andrewwalker1 on 27/08/2017.
 */

public class Player {

    private ImageView image;
    private Activity main;
    private LinearLayout layout;
    private TextView name_label;

    private GameLogic game;


    private String player_name;
    private String image_url;
    private String player_id;

    private float fppg;

    public Player(Activity activity, GameLogic gl, String name, String url, String id, float fp){
        this.game = gl;
        this.main = activity;
        image = new ImageView(main);
        layout = new LinearLayout(main);
        name_label = new TextView(main);


        this.player_name = name;
        this.image_url = url;
        this.player_id = id;

        name_label.setText(this.player_name);

        this.fppg = fp;

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                playerSelected();

            }
        });

        layout.addView(image);
        layout.addView(name_label);
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

    public LinearLayout getLayout(){
        return layout;
    }

}
