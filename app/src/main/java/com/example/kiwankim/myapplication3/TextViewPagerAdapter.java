package com.example.kiwankim.myapplication3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TextViewPagerAdapter extends PagerAdapter {

        // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
        private Context mContext = null ;
        private ArrayList<ArrayList<String>> anime_titles;
        private String Json;
        String WeekDay2[] = {"SUN","MON","TUE","WED","THR","FRI","SAT"};

        // Context를 전달받아 mContext에 저장하는 생성자 추가.
        public TextViewPagerAdapter(Context context,ArrayList<ArrayList<String> > titles,String Json) {
            mContext = context ;
            anime_titles = titles;
            this.Json = Json;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null ;
            ListView listView;
            final int pos = position%7;
            if (mContext != null) {
                // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.view_page, container, false);

                listView = view.findViewById(R.id.listView);
                listView.setAdapter(new MyAdapter(mContext,anime_titles.get(pos)));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(mContext,DetailInfo.class);
                        intent.putExtra("json",Json);
                        intent.putExtra("title",i);
                        intent.putExtra("weekday",pos);
                        mContext.startActivity(intent);
                    }
                });
                TextView weekday = view.findViewById(R.id.weekday);
                weekday.setText(WeekDay2[pos]);
            }

            // 뷰페이저에 추가.
            container.addView(view) ;

            return view ;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 뷰페이저에서 삭제.
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            // 전체 페이지 수는 7개로 고정.
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return (view == (View)object);
        }
}

