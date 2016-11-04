package com.yiheng.mobilesafe.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.receiver.AdminReceiver;

public class SJFDSetting4Activity extends SJFDBaseActivity {

    private ImageView iv_sjfd_acti_admin;
    private RelativeLayout rl_sjbd_setting4_acti_admin;
    private DevicePolicyManager mManager;
    private ComponentName mSample;
    private static final int REQUEST_ACTI_ADMIN = 40001;
    private Button btn_next;
    private Button btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfdsetting4);
        rl_sjbd_setting4_acti_admin = (RelativeLayout) findViewById(R.id.rl_sjbd_setting4_acti_admin);
        iv_sjfd_acti_admin = (ImageView) findViewById(R.id.iv_sjfd_acti_admin);
        mManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mSample = new ComponentName(getApplicationContext(), AdminReceiver.class);


        if (mManager.isAdminActive(mSample)) {
            iv_sjfd_acti_admin.setImageResource(R.drawable.admin_activated);
        } else {
            iv_sjfd_acti_admin.setImageResource(R.drawable.admin_inactivated);
        }

        rl_sjbd_setting4_acti_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mManager.isAdminActive(mSample)) {
                    mManager.removeActiveAdmin(mSample);
                    iv_sjfd_acti_admin.setImageResource(R.drawable.admin_inactivated);
                } else {
                    actiAdmin();
                }
            }
        });
        btn_next = (Button) findViewById(R.id.btn_sjfd_setting4_next);
        btn_pre = (Button) findViewById(R.id.btn_sjfd_setting4_pre);

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
    }

    private void actiAdmin() {
             Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
               intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mSample);
               intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "描述");
               startActivityForResult(intent, REQUEST_ACTI_ADMIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACTI_ADMIN) {
            if (resultCode == Activity.RESULT_OK) {
                iv_sjfd_acti_admin.setImageResource(R.drawable.admin_activated);
            }
        }
    }

    @Override
    protected boolean toPrePage() {
        startActivity(new Intent(getApplicationContext(), SJFDSetting3Activity.class));
        return true;
    }

    @Override
    protected boolean toNextPage() {
        if (!mManager.isAdminActive(mSample)) {
            Toast.makeText(getApplicationContext(), "必须激活设备管理器", Toast.LENGTH_LONG).show();
            return false;
        }
        startActivity(new Intent(getApplicationContext(), SJFDSetting5Activity.class));

        return true;
    }
}
