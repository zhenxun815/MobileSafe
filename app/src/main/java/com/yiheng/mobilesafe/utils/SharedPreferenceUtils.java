package com.yiheng.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yiheng on 2016/10/28 0028.
 */

public class SharedPreferenceUtils {
    public static SharedPreferences mSp;

    private static void getSharedPreference(Context context){
        if (mSp == null) {
            mSp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
    }
}
