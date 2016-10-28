package com.yiheng.mobilesafe;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Yiheng on 2016/10/28 0028.
 */

public class SettingItemView extends RelativeLayout {
    TextView tv_setting_title;
    ImageView iv_setting_image;

    boolean isToogleOn;//开关的状态

    //获取开关状态
    public boolean isToogleOn() {
        return isToogleOn;
    }

    //设置开关状态
    public void setToogleOn(boolean toogleOn) {
        isToogleOn = toogleOn;

        if (toogleOn){
            iv_setting_image.setImageResource(R.drawable.on);
        }else{
            iv_setting_image.setImageResource(R.drawable.off);
        }
    }

    private  void toogle(){
        setToogleOn(!isToogleOn);
    }

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        // 取出从布局文件里设置的属性值 都在attrs对象里
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        String setting_title = typedArray.getString(R.styleable.SettingItemView_tv_setting_title);
        boolean setting_enable = typedArray.getBoolean(R.styleable.SettingItemView_tv_setting_enable, true);
        int setting_background = typedArray.getInt(R.styleable.SettingItemView_tv_setting_background, 0);
        //使用完毕释放一下
        typedArray.recycle();
        //给控件赋值
        tv_setting_title.setText(setting_title);
        iv_setting_image.setVisibility(setting_enable ? VISIBLE : INVISIBLE);

        switch (setting_background) {
            case 0:
                setBackgroundResource(R.drawable.setting_background_top_selector);
                break;
            case 1:
                setBackgroundResource(R.drawable.setting_background_middle_selector);
                break;
            case 2:
                setBackgroundResource(R.drawable.setting_background_bottom_selector);
                break;
        }
    }

    private void initView() {
        View child = View.inflate(getContext(), R.layout.item_setting, null);
        addView(child);

        tv_setting_title = (TextView) findViewById(R.id.tv_setting_title);
        iv_setting_image = (ImageView) findViewById(R.id.iv_setting_image);
    }
}
