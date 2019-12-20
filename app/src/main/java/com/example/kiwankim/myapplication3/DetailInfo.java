package com.example.kiwankim.myapplication3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    LinearLayout Layout;
    private String Json;
    private int No;
    private int weekday;
    private int Serial;
    String WeekDay2[] = {"SUN","MON","TUE","WED","THR","FRI","SAT"};
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info);
        Layout = findViewById(R.id.Linear);
        context = this;
        Detail_title = findViewById(R.id.detail_title);
        Detail_time = findViewById(R.id.detail_time);
        Detail_genre = findViewById(R.id.detail_genre);
        Detail_site = findViewById(R.id.detail_site);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);


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
            Detail_title.setText("제목 : " + subObject.getString("s"));
            Detail_time.setText("시간 : " + fixDetail_time(subObject.getString("t")));
            Detail_genre.setText("장르 : " + subObject.getString("g"));
            Detail_site.setText("공식 사이트 : " + subObject.getString("l"));
            start_date.setText("시작날짜 : " + fixDetail_date(subObject.getString("sd")));
            end_date.setText("종료날짜 : " + fixDetail_date(subObject.getString("ed")));
//            TextView tt = new TextView(this);
//            tt.setText("!!!!!!!!!!!!!!!");
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.gravity = Gravity.CENTER;
//            tt.setLayoutParams(lp);
//            Layout.addView(tt);
            jsonArray = new JSONArray(new subTask().execute(Integer.toString(Serial)).get());
            for(int i=0;i<jsonArray.length();i++) {
                jsonObject = jsonArray.getJSONObject(i);
                make_New_textView();
                episode.setText(fixEpisode(jsonObject.getString("s")));
                release_date.setText(fixUpdateDate(jsonObject.getString("d")));
                address.setText(jsonObject.getString("a"));
                writer.setText(jsonObject.getString("n"));
                addTolayout();
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
    public void make_New_textView(){
        episode = new TextView(this);
        episode.setTextSize(40);
        release_date = new TextView(this);
        release_date.setTextSize(20);
        address = new TextView(this);
        address.setTextSize(20);
        writer = new TextView(this);
        writer.setTextSize(30);
    }
    public void addTolayout(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        episode.setLayoutParams(lp);
        release_date.setLayoutParams(lp);
        address.setLayoutParams(lp);
        writer.setLayoutParams(lp);

        address.setMovementMethod(LinkMovementMethod.getInstance());

        Layout.addView(writer);
        Layout.addView(episode);
        Layout.addView(release_date);
        Layout.addView(address);

    }
    public String fixEpisode(String s){
        if(s=="BD" || s=="EX"|| s=="PIC"|| s=="OVA"|| s=="OAD") return s;
        double Raw = Double.parseDouble(s);
        if(Raw%10 == 0)
            return Integer.toString((int)Raw/10)+"화";
        else
            return Double.toString( Raw * 0.1)+"화";
    }
    public String fixUpdateDate(String d){
        return d.substring(4,6)+"월 "+d.substring(6,8)+"일 "+
                d.substring(8,10)+"시 "+d.substring(10,12)+"분";
    }
    public String fixDetail_time(String t){
        return t.substring(0,2)+"시 "+t.substring(2,4)+"분";
    }
    public String fixDetail_date(String d){
        return d.substring(0,4)+"년 "+d.substring(4,6)+"월 "+d.substring(6,8)+"일";
    }
}
