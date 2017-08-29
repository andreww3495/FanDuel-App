package com.example.andrewwalker1.fanduelapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("Tag", results);

        nextTurn();

    }


    public void afterTurn(){
        TextView label = new TextView(this);
        Button button = new Button(this);
        if(game.getScore() < 10) {


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextTurn();
                }
            });

            button.setText("Next Turn");
        } else{

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    game.newGame();
                    nextTurn();
                }
            });

            button.setText("New Game?");
        }

        label.setText(game.generateMessage());
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main);
        ll.addView(label);
        ll.addView(button);
    }

    //set up the next turn
    public void nextTurn(){
        score = new TextView(this);
        score.setText("Correct Guesses: " + game.getScore());

        game.nextTurn();
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main);

        //set up layout
        ll.removeAllViews();
        ll.addView(score);

        for(int i=0;i<game.getCurrentPlayers().size();i++){
            ll.addView(game.getCurrentPlayers().get(i).getLayout());
        }
    }


    public void setScore(){
        score.setText("Correct Guesses: " + game.getScore());
    }
}
