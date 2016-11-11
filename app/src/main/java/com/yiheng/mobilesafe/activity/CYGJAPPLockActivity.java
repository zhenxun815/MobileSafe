package com.yiheng.mobilesafe.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.bean.AppInfo;
import com.yiheng.mobilesafe.db.ApplockDAO;
import com.yiheng.mobilesafe.engine.AppInfoProvider;

import java.util.ArrayList;

public class CYGJAPPLockActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_cygj_applock_left;
    private Button btn_cygj_applock_right;
    private TextView tv_cygj_applock;
    private LinearLayout pbr_cygj_applock;
    private ListView lv_cygj_applock_unlocked;
    private ListView lv_cygj_applock_locked;
    private ApplockDAO applockDAO;
    private ArrayList<AppInfo> lockedAppInfos;
    private ArrayList<AppInfo> unlockedAppInfos;
    private ArrayList<AppInfo> appInfos;
    private ApplockListAdapter lockedAdapter;
    private ApplockListAdapter unlockedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cygjapplock);
        applockDAO = new ApplockDAO(getApplicationContext());

        initView();

        initData();
    }

    private void initData() {
        new Thread() {

            @Override
            public void run() {
                SystemClock.sleep(600);
                unlockedAppInfos = new ArrayList<>();
                lockedAppInfos = new ArrayList<>();

                appInfos = AppInfoProvider.getAppInfo(getApplicationContext());
                for (AppInfo info : appInfos) {
                    if (applockDAO.isLocked(info.packageName)) {
                        lockedAppInfos.add(info);
                    } else {
                        unlockedAppInfos.add(info);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lockedAdapter = new ApplockListAdapter(true);
                        lv_cygj_applock_locked.setAdapter(lockedAdapter);
                        unlockedAdapter = new ApplockListAdapter(false);
                        lv_cygj_applock_unlocked.setAdapter(unlockedAdapter);

                        pbr_cygj_applock.setVisibility(View.INVISIBLE);
                        tv_cygj_applock.setText("未加锁:" + unlockedAppInfos.size());
                    }
                });


            }
        }.start();
    }

    private void initView() {
        btn_cygj_applock_left = (Button) findViewById(R.id.btn_cygj_applock_left);
        btn_cygj_applock_right = (Button) findViewById(R.id.btn_cygj_applock_right);
        tv_cygj_applock = (TextView) findViewById(R.id.tv_cygj_applock);
        pbr_cygj_applock = (LinearLayout) findViewById(R.id.pbr_cygj_applock);

        lv_cygj_applock_unlocked = (ListView) findViewById(R.id.lv_cygj_applock_unlocked);
        lv_cygj_applock_unlocked.setVisibility(View.VISIBLE);
        lv_cygj_applock_locked = (ListView) findViewById(R.id.lv_cygj_applock_locked);
        lv_cygj_applock_locked.setVisibility(View.INVISIBLE);

        btn_cygj_applock_left.setOnClickListener(this);
        btn_cygj_applock_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cygj_applock_left:
                btn_cygj_applock_left.setBackgroundResource(R.drawable.btn_applock_left_blue);
                btn_cygj_applock_left.setTextColor(Color.WHITE);
                btn_cygj_applock_right.setBackgroundResource(R.drawable.btn_applock_right_white);
                btn_cygj_applock_right.setTextColor(Color.parseColor("#0075FF"));
                tv_cygj_applock.setText("未加锁:" + unlockedAppInfos.size());

                lv_cygj_applock_unlocked.setVisibility(View.VISIBLE);
                lv_cygj_applock_locked.setVisibility(View.INVISIBLE);

                break;
            case R.id.btn_cygj_applock_right:
                btn_cygj_applock_left.setBackgroundResource(R.drawable.btn_applock_left_white);
                btn_cygj_applock_left.setTextColor(Color.parseColor("#0075FF"));
                btn_cygj_applock_right.setBackgroundResource(R.drawable.btn_applock_right_blue);
                btn_cygj_applock_right.setTextColor(Color.WHITE);
                tv_cygj_applock.setText("已加锁:" + lockedAppInfos.size());

                lv_cygj_applock_locked.setVisibility(View.VISIBLE);
                lv_cygj_applock_unlocked.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private class ApplockListAdapter extends BaseAdapter {
        boolean isLockedList;
        boolean isAnimating;
        ArrayList<AppInfo> infos;
        private TranslateAnimation toLeft;
        private TranslateAnimation toRight;


        public ApplockListAdapter(boolean isLockedList) {
            this.isLockedList = isLockedList;
            if (isLockedList) {
                infos = lockedAppInfos;
            } else {
                infos = unlockedAppInfos;
            }
            /*移动动画
            public TranslateAnimation(  int fromXType, float fromXValue,
                                        int toXType, float toXValue,
                                        int fromYType, float fromYValue,
                                        int toYType, float toYValue)
            */
            toLeft = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            toLeft.setDuration(600);

            toRight = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            toRight.setDuration(600);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (null == convertView) {

                convertView = View.inflate(getApplicationContext(), R.layout.item_cygj_applock, null);

                viewHolder = new ViewHolder();
                viewHolder.iv_cygj_applock_appicon =
                        (ImageView) convertView.findViewById(R.id.iv_cygj_applock_appicon);
                viewHolder.tv_cygj_applock_appname =
                        (TextView) convertView.findViewById(R.id.tv_cygj_applock_appname);
                viewHolder.iv_cygj_applock_lock =
                        (ImageView) convertView.findViewById(R.id.iv_cygj_applock_lock);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final AppInfo info = getItem(position);
            viewHolder.iv_cygj_applock_appicon.setImageDrawable(info.icon);
            viewHolder.tv_cygj_applock_appname.setText(info.name);
            viewHolder.iv_cygj_applock_lock.setImageResource(
                    isLockedList ? R.drawable.applock_unlock_selector : R.drawable.applock_lock_selector);

            final View tempView = convertView;
            viewHolder.iv_cygj_applock_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAnimating) {
                        return;
                    }
                    if (isLockedList) {//加锁界面
                        boolean delete = applockDAO.delete(info.packageName);
                        if (delete) {
                            toLeft.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    isAnimating = true;
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    lockedAppInfos.remove(info);
                                    unlockedAppInfos.add(info);
                                    lockedAdapter.notifyDataSetChanged();
                                    unlockedAdapter.notifyDataSetChanged();
                                    tv_cygj_applock.setText("已加锁:" + lockedAppInfos.size());
                                    isAnimating = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            tempView.startAnimation(toLeft);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "解锁失败", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } else {//未加锁界面
                        boolean insert = applockDAO.insert(info.packageName);
                        if (insert) {
                            toRight.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    isAnimating = true;
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    unlockedAppInfos.remove(info);
                                    lockedAppInfos.add(info);
                                    lockedAdapter.notifyDataSetChanged();
                                    unlockedAdapter.notifyDataSetChanged();
                                    tv_cygj_applock.setText("未加锁:" + unlockedAppInfos.size());
                                    isAnimating = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            tempView.startAnimation(toRight);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "加锁失败", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_cygj_applock_appicon;
        TextView tv_cygj_applock_appname;
        ImageView iv_cygj_applock_lock;
    }
}