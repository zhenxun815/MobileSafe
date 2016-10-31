package com.yiheng.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yiheng on 2016/10/28 0028.
 */

public class SharedPreferenceUtils {
    public static SharedPreferences mSp;

    private static void getSharedPreference(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getSharedPreference(context);

        mSp.edit().putBoolean(key, value).commit();
    }

    public static Boolean getBoolean(Context context, String key, boolean value) {
        getSharedPreference(context);
        return mSp.getBoolean(key, value);
    }

    public static void putString(Context context, String key, String value) {
        getSharedPreference(context);

        mSp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defvalue) {
        getSharedPreference(context);
        return mSp.getString(key, defvalue);
    }

    public static void putInt(Context context, String key, int value) {
        getSharedPreference(context);

        mSp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defvalue) {
        getSharedPreference(context);
        return mSp.getInt(key, defvalue);
    }

    public static void remove(Context context, String key) {
        getSharedPreference(context);
        mSp.edit().remove(key).commit();
    }

}
