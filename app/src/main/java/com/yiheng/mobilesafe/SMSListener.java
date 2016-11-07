package com.yiheng.mobilesafe;

/**
 * 用于回调获取短信信息
 * Created by Yiheng on 2016/11/7 0007.
 */

public interface SMSListener {
    void onMax(int max);
    void onSuccess();
    void onProgress(int progerss);
    void onFailed(Exception e);
}
