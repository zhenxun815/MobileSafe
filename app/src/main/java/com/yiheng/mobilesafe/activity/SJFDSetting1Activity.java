package com.yiheng.mobilesafe.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yiheng.mobilesafe.R;

public class SJFDSetting1Activity extends SJFDBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfdsetting1);
        Button btn_sjfd_setting1_next = (Button) findViewById(R.id.btn_sjfd_setting1_next);
        //设置箭头图片大小,位置
        Drawable next = getResources().getDrawable(R.drawable.next, null);
        next.setBounds(0,0,40,30);
        btn_sjfd_setting1_next.setCompoundDrawables(null,null,next,null);

        btn_sjfd_setting1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextClick(v);
            }
        });
    }

    @Override
    protected boolean toPrePage() {
        return false;
    }

    @Override
    protected boolean toNextPage() {
        startActivity(new Intent(this,SJFDSetting2Activity.class));
        return true;
    }
}
