package com.yiheng.mobilesafe;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class HomepageActivity extends AppCompatActivity {


    private ImageView iv_homepage_heima;
    private GridView gv_homepage;
    private static final String[] OPTION_TITLE = {
            "手机防盗","骚扰拦截","软件管家","进程管理","流量统计","手机杀毒","缓存清理","常用工具"};
    private static final int[] IMAGE_IDS={
            R.drawable.sjfd,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        iv_homepage_heima = (ImageView) findViewById(R.id.iv_homepage_heima);
        gv_homepage = (GridView) findViewById(R.id.gv_homepage);

        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_homepage_heima,"rotationY",0f,360f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000);
        animator.start();

        gv_homepage.setAdapter(new MyAdaptor());


    }

    public void setting(View view) {
        // TODO: 2016/10/27 0027 设置按钮

    }

    private class MyAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
