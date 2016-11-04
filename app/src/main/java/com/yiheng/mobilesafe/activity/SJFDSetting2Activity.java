package com.yiheng.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class SJFDSetting2Activity extends SJFDBaseActivity {

    private Button btn_next;
    private Button btn_pre;
    private RelativeLayout rl_sjbd_setting2_bindsim;
    private ImageView iv_sjfd_simbind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfdsetting2);

        btn_next = (Button) findViewById(R.id.btn_sjfd_setting2_next);
        btn_pre = (Button) findViewById(R.id.btn_sjfd_setting2_pre);

        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextClick(v);
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                preClick(v);
            }
        });

        String simNUm = SharedPreferenceUtils
                .getString(getApplicationContext(), ConstantUtils.SIM_NUM, null);

        rl_sjbd_setting2_bindsim = (RelativeLayout) findViewById(R.id.rl_sjbd_setting2_bindsim);
        iv_sjfd_simbind = (ImageView) findViewById(R.id.iv_sjfd_simbind);

        if (TextUtils.isEmpty(simNUm)) {
            iv_sjfd_simbind.setImageResource(R.drawable.unlock);
        } else if (!TextUtils.isEmpty(simNUm)){
            iv_sjfd_simbind.setImageResource(R.drawable.lock);
        }



        rl_sjbd_setting2_bindsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 绑定SIM卡,获取SIM卡序列号
                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String simNUm = SharedPreferenceUtils
                        .getString(getApplicationContext(), ConstantUtils.SIM_NUM, null);
                if (TextUtils.isEmpty(simNUm)) {
                    SharedPreferenceUtils.
                            putString(getApplicationContext(),ConstantUtils.SIM_NUM,tm.getSimSerialNumber());
                    iv_sjfd_simbind.setImageResource(R.drawable.lock);
                } else if (!TextUtils.isEmpty(simNUm)){
                    SharedPreferenceUtils
                            .putString(getApplicationContext(),ConstantUtils.SIM_NUM,"");
                    iv_sjfd_simbind.setImageResource(R.drawable.unlock);
                }

            }
        });

    }

    @Override
    protected boolean toPrePage() {
        startActivity(new Intent(getApplicationContext(),SJFDSetting1Activity.class));
        return true;
    }

    @Override
    protected boolean toNextPage() {
        //判断是否已绑定SIM卡
        String simNum = SharedPreferenceUtils
                .getString(getApplicationContext(), ConstantUtils.SIM_NUM, null);
        if (!TextUtils.isEmpty(simNum)){
            startActivity(new Intent(getApplicationContext(),SJFDSetting3Activity.class));
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"必须绑定SIM卡",Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
