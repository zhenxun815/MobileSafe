package com.yiheng.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断手机防盗是否开启
        Boolean isSJFDSetOn = SharedPreferenceUtils
                .getBoolean(context, ConstantUtils.IS_SJFD_SETTING, false);
        if (!isSJFDSetOn) {
            return;
        }
        //获取sim卡信息
        String simNum = SharedPreferenceUtils
                .getString(context, ConstantUtils.SIM_NUM, "");
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        //如果sim不匹配,向安全号码发送短信
        if (!TextUtils.equals(simNum, simSerialNumber)) {
            String safeNum = SharedPreferenceUtils
                    .getString(context, ConstantUtils.SJFD_SAFENUM, "");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(safeNum, null, "sim卡被换掉了", null, null);
        }
    }
}
