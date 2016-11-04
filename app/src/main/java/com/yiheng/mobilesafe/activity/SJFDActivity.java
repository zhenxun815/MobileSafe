package com.yiheng.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class SJFDActivity extends AppCompatActivity {
private TextView tv_sjfd_saffenum;
   private ImageView iv_sjfd_islock;

    private TextView tv_sjfd_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfd);
        tv_sjfd_saffenum = (TextView) findViewById(R.id.tv_sjfd_saffenum);
        String safeNum = SharedPreferenceUtils
                .getString(getApplicationContext(), ConstantUtils.SJFD_SAFENUM,"");
        tv_sjfd_saffenum.setText(safeNum);

        iv_sjfd_islock = (ImageView) findViewById(R.id.iv_sjfd_islock);
        Boolean isSetOn = SharedPreferenceUtils
                .getBoolean(getApplicationContext(), ConstantUtils.SJFD_SET_ON, true);
        if (isSetOn){
            iv_sjfd_islock.setImageResource(R.drawable.lock);
        }else {
            iv_sjfd_islock.setImageResource(R.drawable.unlock);
        }

        tv_sjfd_reset = (TextView) findViewById(R.id.tv_sjfd_reset);
        tv_sjfd_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SJFDSetting1Activity.class));
            }
        });
    }
}
