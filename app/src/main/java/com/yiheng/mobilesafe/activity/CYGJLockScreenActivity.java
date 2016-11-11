package com.yiheng.mobilesafe.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.utils.ConstantUtils;

public class CYGJLockScreenActivity extends AppCompatActivity {

    private ImageView iv_cygj_screenlock_appicon;
    private TextView tv_cygj_screenlock_appname;
    private EditText et_cygj_screenlock_psw;
    private Button btn_cygj_screenlock_confirm;
    private PackageManager packageManager;
    private String packageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cygjlock_screen);

        iv_cygj_screenlock_appicon = (ImageView) findViewById(R.id.iv_cygj_screenlock_appicon);
        tv_cygj_screenlock_appname = (TextView) findViewById(R.id.tv_cygj_screenlock_appname);
        et_cygj_screenlock_psw = (EditText) findViewById(R.id.et_cygj_screenlock_psw);
        btn_cygj_screenlock_confirm = (Button) findViewById(R.id.btn_cygj_screenlock_confirm);
        Intent intent = getIntent();
        packageName = intent.getStringExtra("packageName");
        packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            Drawable appIcon = applicationInfo.loadIcon(packageManager);
            String appName = applicationInfo.loadLabel(packageManager).toString();

            iv_cygj_screenlock_appicon.setImageDrawable(appIcon);
            tv_cygj_screenlock_appname.setText(appName);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getApplicationContext(), "..", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        btn_cygj_screenlock_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psw = et_cygj_screenlock_psw.getText().toString().trim();
                if (TextUtils.equals(psw, "123")) {
                    Intent intent1 = new Intent();
                    intent1.setAction(ConstantUtils.ACTION_APPUNLOCK);
                    intent1.putExtra("packageName", packageName);
                    sendBroadcast(intent1);
                } else {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
                    return;
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //按返回键跳转到Home页面
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
