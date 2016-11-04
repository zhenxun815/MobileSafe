package com.yiheng.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yiheng.mobilesafe.R;

public class SRLJEditActivity extends AppCompatActivity {
    private ImageView iv_srlj_add_forbidden;
    private ListView lv_srlj_forbiddons;
    private ImageView iv_srlj_empty;
    private ProgressBar pbr_srlj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srlj_edit);
        iv_srlj_add_forbidden = (ImageView) findViewById(R.id.iv_srlj_add_forbidden);
        lv_srlj_forbiddons = (ListView) findViewById(R.id.lv_srlj_forbiddons);
        iv_srlj_empty = (ImageView) findViewById(R.id.iv_srlj_empty);
        pbr_srlj = (ProgressBar) findViewById(R.id.pbr_srlj);
        // TODO: 2016/11/4 0004
    }
}
