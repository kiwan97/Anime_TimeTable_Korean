package com.example.kiwankim.myapplication3;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Task extends AsyncTask<String, Void, String> {
    private String str,receiveMsg;
    String WeekDay[] = {"\"SUN\":","\"MON\":","\"TUE\":","\"WED\":","\"THR\":","\"FRI\":","\"SAT\":"};
    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        receiveMsg = "{";
        for(int i=0;i<7;i++) {
            try {
                receiveMsg+=  WeekDay[i];
                url = new URL("https://www.anissia.net/anitime/list?w="+Integer.toString(i));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg += buffer.toString();
                    if(i!=6) receiveMsg+=',';
                    else receiveMsg+='}';
                    Log.i("receiveMsg : ", receiveMsg);

                    reader.close();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return receiveMsg;
    }
}
