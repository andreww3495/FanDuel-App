package com.example.andrewwalker1.fanduelapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by andrewwalker1 on 24/08/2017.
 */

public class JsonParser {

    private String json;
    private JSONObject object;
    private JSONArray players;

    public JsonParser(){}

    public JsonParser(String data){
        this.json = data;
        try {
            object = new JSONObject(data);
            players = object.getJSONArray("players");

        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
    }

    public JSONArray getPlayers(){
        return players;
    }

    //access a single player object from the list based on the given number
    public JSONObject retrivePlayer(int location) {
        try {
            JSONObject player = players.getJSONObject(location);
            return player;
        } catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return null;
    }

    //access the give attribute of the given player
    public String getSingleAttribute(JSONObject player, String attribute){
        try {
            return player.getString(attribute);
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return null;
    }

    //retrieve the image url from the given object
    public String retrieveUrl(JSONObject player){
        String url = "";
        try {
            JSONObject image = player.getJSONObject("images");
            JSONObject default_image = image.getJSONObject("default");
            url = default_image.getString("url");
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return url;

    }


}
