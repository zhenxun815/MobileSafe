package com.yiheng.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.internal.telephony.ITelephony;
import com.yiheng.mobilesafe.bean.ForbiddenInfo;
import com.yiheng.mobilesafe.db.ForbiddenDAO;

import java.lang.reflect.Method;

public class CallSMSService extends Service {
    private ForbiddenDAO forbiddenDAO;
    private TelephonyManager manager;

    public CallSMSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        forbiddenDAO = new ForbiddenDAO(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");// 短信广播的action
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(mBroadcastReceiver, filter);

        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        manager.listen(listener, PhoneStateListener.LISTEN_NONE);
    }

    //拦截短信
    //通过SMS_RECEIVED_ACTION intent获取短信,通过短信识别发短信的号码,对比屏蔽号码数据库,确定是否拦截

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
            String format = intent.getStringExtra("format");

            for (Object message : messages) {
                byte[] pdu = (byte[]) message;
                SmsMessage sms;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    // Create an SmsMessage from a raw PDU with the specified message format.
                    // The message format is passed in the SMS_RECEIVED_ACTION as the format String extra,
                    // and will be either "3gpp" for GSM/UMTS/LTE messages in 3GPP format or "3gpp2" for CDMA/LTE messages in 3GPP2 format.
                    //
                    // Parameters
                    //      pdu
                    // byte: the message PDU from the SMS_RECEIVED_ACTION intent
                    //      format
                    // String: the format extra from the SMS_RECEIVED_ACTION intent
                    // Returns
                    //      SmsMessage

                    sms = SmsMessage.createFromPdu(pdu, format);
                } else {
                    sms = SmsMessage.createFromPdu(pdu);
                }

                String originatingAddress = sms.getOriginatingAddress();
                int type = forbiddenDAO.queryType(originatingAddress);
                if (ForbiddenInfo.TYPE_SMS == type || ForbiddenInfo.TYPE_ALL == type) {
                    abortBroadcast();
                }
            }
        }
    };

    //拦截电话
    //通过PhoneStateListener监听电话状态,在响铃阶段获取来电号码,匹配屏蔽数据库,自定义方法endCall()拦截电话
    //通过ContentResolver注册ContentObserver,删除来电记录
    private PhoneStateListener listener= new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            if (TelephonyManager.CALL_STATE_IDLE==state){
                int type = forbiddenDAO.queryType(incomingNumber);
                if (ForbiddenInfo.TYPE_ALL == type || ForbiddenInfo.TYPE_CALL == type){
                    endcall();

                    final ContentResolver resolver = getContentResolver();
                    final Uri uri = Uri.parse("content://call_log/calls");
                    resolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
                        @Override
                        public void onChange(boolean selfChange) {
                            super.onChange(selfChange);

                            /*Parameters
                                    url
                            Uri: The URL of the row to delete.
                                    where
                            String: A filter to apply to rows before deleting, formatted as an SQL WHERE clause (excluding the WHERE itself).
                                    selectionArgs
                            String
                                    Returns
                            int
                            The number of rows deleted.*/
                            String where = CallLog.Calls.NUMBER + "=?";
                            String[] selectionArgs = new String[]{incomingNumber};
                            resolver.delete(uri,where,selectionArgs);

                            resolver.unregisterContentObserver(this);
                        }
                    });
                }
            }
        }
    };

    private void endcall() {
        try {
            // 参1 方法名字 参2 方法里参数的类型
            Method declaredMethod = manager.getClass().getDeclaredMethod(
                    "getITelephony", (Class<?>) null);
            declaredMethod.setAccessible(true);// 设置私有方法可以访问
            // 参1调用的方法的对象

           ITelephony telephony = (ITelephony) declaredMethod.invoke(manager, null);
            // 挂断电话 <uses-permission
            // android:name="android.permission.CALL_PHONE" />
            telephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
