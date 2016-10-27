package com.yiheng.mobilesafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;

public class HomepageActivity extends AppCompatActivity {


    private ImageView iv_homepage_heima;
    private GridView gv_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        iv_homepage_heima = (ImageView) findViewById(R.id.iv_homepage_heima);
        gv_homepage = (GridView) findViewById(R.id.gv_homepage);


    }

    public void setting(View view) {
        // TODO: 2016/10/27 0027 设置按钮

    }
}
