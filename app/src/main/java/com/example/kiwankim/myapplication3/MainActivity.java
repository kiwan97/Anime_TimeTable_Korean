package com.example.kiwankim.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewpager;
    Context context;
    ArrayList< ArrayList<String> > Msg = new ArrayList<>();
    private TextViewPagerAdapter pagerAdapter;
    private String Json;
    String WeekDay2[] = {"SUN","MON","TUE","WED","THR","FRI","SAT"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
        context=this;
        for(int i=0;i<7;i++){
            Msg.add(new ArrayList<String>());
        }
        try{
            Json = new Task().execute().get();
            getAnimeTitle(Json);
        }
        catch (InterruptedException e) {
            e.printStackTrace(); }
        catch (ExecutionException e) {
            e.printStackTrace();

        }
        mViewpager = findViewById(R.id.viewPager);
        pagerAdapter = new TextViewPagerAdapter(this,Msg,Json);
        mViewpager.setAdapter(pagerAdapter);
        mViewpager.setCurrentItem(35,false);
    }

    public void getAnimeTitle(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray;
            for(int j=0;j<7;j++) {
                jsonArray = jsonObject.getJSONArray(WeekDay2[j]);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject subObject = jsonArray.getJSONObject(i);
                    Msg.get(j).add(subObject.getString("s"));
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

