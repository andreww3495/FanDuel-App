package com.example.andrewwalker1.fanduelapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements AsyncParent{

    public DataRetriever dr;
    private String data_path = "https://cdn.rawgit.com/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/raw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json";
    private GameLogic game;

    TextView score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new GameLogic(this);

        dr = new DataRetriever(data_path, this);
        dr.execute();

    }

    @Override
    public void processResults(String results){
        game.setJsonString(results);

        nextTurn();

    }


    public void afterTurn(){

        if(game.getScore() < 10) {
            TextView label = new TextView(this);
            Button button = new Button(this);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextTurn();
                }
            });

            button.setText("Next Turn");

                label.setText(game.generateMessage());


            LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main);
            ll.addView(label);
            ll.addView(button);
        }
    }

    //set up the next turn
    public void nextTurn(){
        game.resetCurrentPlayers();
        score = new TextView(this);
        score.setText("Correct Guesses: " + game.getScore());

        Player p = game.createPlayer();
        Player p2 = game.createPlayer();

        LinearLayout rl = (LinearLayout) findViewById(R.id.activity_main);


        ImageLoader il = new ImageLoader(this);
        il.loadIntoView(p.getImage(), p.getImageUrl());
        il.loadIntoView(p2.getImage(), p2.getImageUrl());

        //set up layout
        rl.removeAllViews();
        rl.addView(score);
        rl.addView(p.getImage());
        rl.addView(p2.getImage());
    }


    public void setScore(){
        score.setText("Correct Guesses: " + game.getScore());
    }
}
