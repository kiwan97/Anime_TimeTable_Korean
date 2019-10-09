package com.example.kiwankim.myapplication3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DetailInfo extends AppCompatActivity {
    TextView Detail_title;
    TextView Detail_time;
    TextView Detail_genre;
    TextView Detail_site;
    TextView start_date;
    TextView end_date;
    TextView episode;
    TextView release_date;
    TextView address;
    TextView writer;
    Context context;
    private String Json;
    private int No;
    private int weekday;
    private int Serial;
    String WeekDay2[] = {"SUN","MON","TUE","WED","THR","FRI","SAT"};
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info);
        context = this;
        Detail_title = findViewById(R.id.detail_title);
        Detail_time = findViewById(R.id.detail_time);
        Detail_genre = findViewById(R.id.detail_genre);
        Detail_site = findViewById(R.id.detail_site);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);

        episode = findViewById(R.id.episode);
        release_date = findViewById(R.id.release_date);
        address = findViewById(R.id.address);
        writer = findViewById(R.id.address);

        Intent intent = getIntent();

        Json = intent.getExtras().getString("json");
        No = intent.getExtras().getInt("title");
        weekday = intent.getExtras().getInt("weekday");
        try{
            JSONObject jsonObject = new JSONObject(Json);
            JSONArray jsonArray;

            jsonArray = jsonObject.getJSONArray(WeekDay2[weekday]);
            JSONObject subObject = jsonArray.getJSONObject(No);
            Serial = subObject.getInt("i");
            Detail_title.setText(subObject.getString("s"));
            Detail_time.setText(subObject.getString("t"));
            Detail_genre.setText(subObject.getString("g"));
            Detail_site.setText(subObject.getString("l"));
            start_date.setText(subObject.getString("sd"));
            end_date.setText(subObject.getString("ed"));

            jsonArray = new JSONArray(new subTask().execute(Integer.toString(Serial)).get());
            for(int i=0;i<jsonArray.length();i++) {
                jsonObject = jsonArray.getJSONObject(i);

                episode.setText(jsonObject.getString("s"));
                release_date.setText(jsonObject.getString("d"));
                address.setText(jsonObject.getString("a"));
                writer.setText(jsonObject.getString("n"));
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
