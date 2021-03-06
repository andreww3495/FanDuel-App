package com.example.andrewwalker1.fanduelapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andrewwalker1 on 25/08/2017.
 *
 * handles the main logic of the game, such as setting up turns, evaluating selections and tracking score
 */

public class GameLogic {

    private Random random;
    private String TAG = "GameLogic";
    private JSONArray players;
    private MainActivity main;
    private JsonParser json_parser;
    private String json_string;
    private ImageLoader il;

    private int score = 0;
    private int player_quantity = 2;

    private ArrayList<Player> currentGame;

    private Player selected;

    public GameLogic(String player_array, MainActivity ma){

        this.json_string = player_array;
        this.main = ma;

        random = new Random();

        json_parser = new JsonParser(json_string);
        players = json_parser.getPlayers();
        currentGame = new ArrayList<Player>();


    }

    public GameLogic(MainActivity ma){

        this.main = ma;
        random = new Random();
        il = new ImageLoader(this.main);
    }

    public void setJsonString(String json){
        this.json_string = json;

        json_parser = new JsonParser(json_string);
        players = json_parser.getPlayers();
        currentGame = new ArrayList<Player>();
    }

    //return a random json object for a player
    public JSONObject selectRandomPlayer(JSONArray players){
        JSONObject random_player;
        try {
            //get length of given array
            int limit = players.length();

            //random number within range
            int value = random.nextInt(limit - 1);

            //get player object at this position
            random_player = players.getJSONObject(value);

            return random_player;
        } catch(Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    //carry out the logic for the next turn based on the selected player
    public void turnTaken(Player selected){
        Boolean correct = true;
        this.selected = selected;
        for(int i =0;i<currentGame.size();i++){
            if(selected.getFppg() < currentGame.get(i).getFppg() && !(selected.getId().equals(currentGame.get(i).getId()))){
                //incorrect guess
                correct = false;
                break;
            }
        }

        //update score
        if(correct){
            score++;
            main.setScore();
        }

        main.afterTurn();

        //ensure players cant be clicked again
        for (Player p: currentGame) {
            p.removeListeners();
        }

        //clear value for selected
        this.selected = null;
    }

    //generate a random player
    public Player createPlayer(){
        JSONObject player_json = selectRandomPlayer(players);

        String name = json_parser.getSingleAttribute(player_json, "first_name") + " " + json_parser.getSingleAttribute(player_json, "last_name");
        String id = json_parser.getSingleAttribute(player_json, "id");
        String url = json_parser.retrieveUrl(player_json);
        String fppg = json_parser.getSingleAttribute(player_json, "fppg");

        //if fppg value for a player is null, set it to 0
        float fp;
        try {
            fp = Float.parseFloat(fppg);
        } catch(Exception e){
            fp = 0;
        }

        Player player = new Player(main, this, name, url, id, fp);
        return player;
    }

    //return current score
    public int getScore(){
        return score;
    }

    //used to generate a message based on the previous turn
    public String generateMessage(){
        StringBuilder sb = new StringBuilder();

        //get highest
        Player highest = currentGame.get(0);
        for(int i=1;i<currentGame.size();i++){
            if(currentGame.get(i).getFppg() > highest.getFppg()) {
                highest = currentGame.get(i);
            }
        }

        if(selected.getId().equals(highest.getId())){
            String line1 = "You have selected the player " + selected.getName() + " who has the highest FPPG with " + selected.getFppg() + "\n";
            sb.append(line1);

            //output the rest of the players
            for (Player p : currentGame) {
                if(!(p.getId().equals(selected.getId())))
                    sb.append(p.getName() + " has a FPPG of " + p.getFppg() + "\n");
            }
        } else {
            String line1 = "You have selected the player " + selected.getName() + " who has a FPPG of " + selected.getFppg() + "\n";
            String line2 = highest.getName() + " has the highest FPPG, with " + highest.getFppg();
            sb.append(line1);
            sb.append(line2);
        }

        return sb.toString();
    }

    //set up next turn
    public void nextTurn(){
        resetCurrentPlayers();

        //create required number of new players
        for(int i=0;i<player_quantity;i++){

            //ensure that a player cannot be selected more than once
            Boolean in_use = true;
            while(in_use){
                in_use = false;
                Player p = createPlayer();

                //check to see if the created player is already in use
                for(int j=0;j<currentGame.size();j++){
                    if(currentGame.get(j).getId().equals(p.getId())){
                        in_use = true;
                        break;
                    }
                }

                //player is not currently in use
                if(!in_use){
                    currentGame.add(p);
                    il.loadIntoView(p.getImage(), p.getImageUrl());
                }
            }
        }
    }

    //start a new game
    public void newGame(){
        this.score = 0;
        resetCurrentPlayers();
    }

    public ArrayList<Player> getCurrentPlayers(){
        return  this.currentGame;
    }

    public void resetCurrentPlayers(){
        currentGame.clear();
    }

}
