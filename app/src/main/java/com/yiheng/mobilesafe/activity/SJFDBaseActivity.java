package com.yiheng.mobilesafe.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.yiheng.mobilesafe.R;

public abstract class SJFDBaseActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //向右滑,跳转上一个页面
        //向左滑,跳转到下一个页面
        gestureDetector = new GestureDetector(getApplicationContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                        float e1RawX = e1.getRawX();
                        float e2RawX = e2.getRawX();
                        float e1RawY = e1.getRawY();
                        float e2RawY = e2.getRawY();

                        if (Math.abs(e1RawX - e2RawX) < Math.abs(e1RawY - e2RawY)) {
                            return false;
                        }
                        //向右滑,跳转上一个页面
                        if (e2RawX - e1RawX > 100) {

                            pre();
                            return true;
                        }
                        //向左滑,跳转到下一个页面
                        if (e1RawX - e2RawX > 100) {

                            next();
                            return true;
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

    }

    //交给子类实现的跳转方法
    protected abstract boolean toPrePage();

    protected abstract boolean toNextPage();

    // 跳转到下一个页面
    protected void next() {
        if (toNextPage()) {
            finish();
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }

    // 跳转上一个页面
    protected void pre() {
        if (toPrePage()) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

    public void nextClick(View view) {
        next();
    }

    public void preClick(View view) {
        pre();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
