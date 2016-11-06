package com.yiheng.mobilesafe.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

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
        gv_homepage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sjfd();//手机防盗item
                        break;
                    case 1://骚扰拦截
                        startActivity(new Intent(getApplicationContext(), SRLJActivity.class));
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                }
            }
        });

    }

    private void sjfd() {
        final String psw = SharedPreferenceUtils
                .getString(getApplicationContext(), ConstantUtils.SJFD_PSW, null);

        //未设置防盗密码,设置密码,并进入手机防盗设置一界面
        if (TextUtils.isEmpty(psw)) {
            // 设置手机防盗密码
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.dialog_initpsw_layout, null);
            builder.setView(view);
            final AlertDialog dialog = builder.create();

            //findViewById前记得写成view.findViewById
            final EditText et_dialog_initpsw_psw1 = (EditText) view.findViewById(R.id.et_dialog_initpsw_psw1);
            final EditText et_dialog_initpsw_psw2 = (EditText) view.findViewById(R.id.et_dialog_initpsw_psw2);
            final Button btn_dialog_initpsw_confirm =
                    (Button) view.findViewById(R.id.btn_dialog_initpsw_confirm);
            final Button btn_dialog_initpsw_concel =
                    (Button) view.findViewById(R.id.btn_dialog_initpsw_concel);


            btn_dialog_initpsw_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String psw1 = et_dialog_initpsw_psw1.getText().toString().trim();
                    String psw2 = et_dialog_initpsw_psw2.getText().toString().trim();

                    if (TextUtils.equals(psw1, psw2) && !TextUtils.isEmpty(psw1)) {
                        SharedPreferenceUtils.
                                putString(getApplicationContext(), ConstantUtils.SJFD_PSW, psw1);
                        //进入手机防盗设置页面内一
                        startActivity(new Intent(getApplicationContext(), SJFDSetting1Activity.class));
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "两次密码不一致或密码为空", Toast.LENGTH_LONG).show();

                    }
                }
            });

            btn_dialog_initpsw_concel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            //已设置防盗密码,输入密码后检测是否完成手机防盗设置,如果完成设置,直接进入手机防盗主页面,否则进入手机防盗设置一界面
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.dialog_enterpsw_layout, null);
            builder.setView(view);
            final AlertDialog dialog = builder.create();

            final EditText et_dialog_enterpsw = (EditText) view.findViewById(R.id.et_dialog_enterpsw);
            final Button btn_dialog_confirm = (Button) view.findViewById(R.id.btn_dialog_enterpsw_confirm);
            final Button btn_dialog_concel = (Button) view.findViewById(R.id.btn_dialog_enterpsw_concel);

            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String psw0 = et_dialog_enterpsw.getText().toString().trim();
                    if (TextUtils.equals(psw, psw0)) {
                        Boolean isSJFDSetting = SharedPreferenceUtils
                                .getBoolean(getApplicationContext(), ConstantUtils.IS_SJFD_SETTING, false);
                        if (isSJFDSetting) {
                            startActivity(new Intent(getApplicationContext(), SJFDActivity.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), SJFDSetting1Activity.class));
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
                    }
                }
            });

            btn_dialog_concel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    public void setting(View view) {
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));

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
