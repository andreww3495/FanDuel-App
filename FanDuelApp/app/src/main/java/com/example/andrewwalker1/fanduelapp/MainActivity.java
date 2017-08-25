package com.example.andrewwalker1.fanduelapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements AsyncParent{

    public DataRetriever dr;
    private String data_path = "https://cdn.rawgit.com/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/raw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dr = new DataRetriever(data_path, this);
        dr.execute();

    }

    @Override
    public void processResults(String results){
        TextView tv = (TextView)findViewById(R.id.testlabel);
        tv.setText(results);

        JsonParser js = new JsonParser(results);
        String name = js.getSingleAttribute(js.retrivePlayer(0), "first_name");
        String fppg = js.getSingleAttribute(js.retrivePlayer(0), "fppg");
        tv.setText(name + " : " + fppg);

        ImageView image_view = (ImageView)findViewById(R.id.testimage);
        ImageLoader image_loader = new ImageLoader(this);
        image_loader.loadIntoView(image_view, "https://d17odppiik753x.cloudfront.net/playerimages/nba/15860.png");
    }
}
