package com.example.andrewwalker1.fanduelapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andrewwalker1 on 24/08/2017.
 */


public class DataRetriever extends AsyncTask<Void, Void, Void> {

    private String data_path;
    private String json;

    private String TAG = "DateRetriever";

    //empty constructor
    public DataRetriever(){}

    //take in data path as an argument
    public DataRetriever(String path){
        this.data_path = path;
        json = "";
    }

    //retrieve data from the url
    public void retrieveData(){
        try{
            //create url from the given path
            URL url = new URL(data_path);

            //retrieve data
            HttpURLConnection url_connection = (HttpURLConnection) url.openConnection();
            url_connection.setRequestMethod("GET");

            InputStream input_stream = url_connection.getInputStream();
            BufferedReader buffered_reader = new BufferedReader(new InputStreamReader(input_stream));

            //used for reading result
            StringBuilder string_builder = new StringBuilder();
            String line;

            //read result
            while((line=buffered_reader.readLine()) !=null){
                string_builder.append(line);
            }

            //store JSON result as string
            json = string_builder.toString();
            Log.i(TAG, json);

        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public String getJson(){
        return json;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        retrieveData();
        return null;
    }
}
