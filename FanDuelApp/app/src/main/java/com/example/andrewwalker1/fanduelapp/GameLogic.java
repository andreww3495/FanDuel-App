package com.example.andrewwalker1.fanduelapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by andrewwalker1 on 25/08/2017.
 */

public class GameLogic {

    private Random random;
    private String TAG = "GameLogic";

    public GameLogic(){
        random = new Random();
    }



    public JSONObject selectRandomPlayer(JSONArray players){
        JSONObject random_player;
        try {
            //get length of given array
            int limit = players.length();

            //random number within range
            int value = random.nextInt(limit);

            //get player object at this position
            random_player = players.getJSONObject(value);

            return random_player;
        } catch(Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
