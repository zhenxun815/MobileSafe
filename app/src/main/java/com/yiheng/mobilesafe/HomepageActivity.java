package com.yiheng.mobilesafe;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HomepageActivity extends AppCompatActivity {


    private ImageView iv_homepage_heima;
    private GridView gv_homepage;
    private static final String[] OPTION_TITLE = {
            "手机防盗", "骚扰拦截", "软件管家", "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具"};
    private static final String[] OPTION_DEC = {
            "远程定位手机", "全面拦截骚扰", "管理您的软件", "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全"
    };
    private static final int[] IMAGE_IDS = {
            R.drawable.sjfd, R.drawable.srlj, R.drawable.rjgj, R.drawable.jcgl,
            R.drawable.lltj, R.drawable.sjsd, R.drawable.hcql, R.drawable.cygj
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        iv_homepage_heima = (ImageView) findViewById(R.id.iv_homepage_heima);
        gv_homepage = (GridView) findViewById(R.id.gv_homepage);

        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_homepage_heima, "rotationY", 0f, 360f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000);
        animator.start();

        gv_homepage.setAdapter(new MyAdaptor());

    }

    public void setting(View view) {
        startActivity(new Intent(getApplicationContext(),SettingActivity.class));

    }

    private class MyAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return OPTION_TITLE.length;
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
            HomepageItemViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new HomepageItemViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.item_homeactivity, null);
                viewHolder.iv_homepage_item_icon = (ImageView) convertView.findViewById(R.id.iv_homepage_item_icon);
                viewHolder.tv_homepage_item_title = (TextView) convertView.findViewById(R.id.tv_homepage_item_title);
                viewHolder.tv_homepage_item_desc = (TextView) convertView.findViewById(R.id.tv_homepage_item_desc);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HomepageItemViewHolder) convertView.getTag();
            }
            viewHolder.iv_homepage_item_icon.setImageResource(IMAGE_IDS[position]);
            viewHolder.tv_homepage_item_title.setText(OPTION_TITLE[position]);
            viewHolder.tv_homepage_item_desc.setText(OPTION_DEC[position]);
            return convertView;
        }


    }

    private static class HomepageItemViewHolder {
        ImageView iv_homepage_item_icon;
        TextView tv_homepage_item_title;
        TextView tv_homepage_item_desc;
    }
}
