package com.yiheng.mobilesafe.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yiheng.mobilesafe.SMSListener;
import com.yiheng.mobilesafe.bean.SMSInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Yiheng on 2016/11/7 0007.
 */

public class SMSUtils {


    private static ContentResolver resolver;
    private static Uri uri;

    public static void backup(final Activity context, final SMSListener smsListener) {

        new Thread() {
            @Override
            public void run() {
                //获取短信
                ArrayList<SMSInfo> infos = new ArrayList<>();
                ContentResolver resolver = context.getContentResolver();
                uri = Uri.parse("content://sms");
                String[] projection = new String[]{"address", "date", "read", "type", "body"};
                Cursor cursor = resolver.query(uri, projection, null, null, null);

                if (null != cursor) {
                    int max = cursor.getCount();
                    smsListener.onMax(max);
                    int progress = 0;
                    while (cursor.moveToNext()) {
                        SystemClock.sleep(100);
                        String address = cursor.getString(0);
                        long date = cursor.getLong(1);
                        int read = cursor.getInt(2);
                        int type = cursor.getInt(3);
                        String body = cursor.getString(4);
                        SMSInfo info = new SMSInfo(address, date, read, type, body);
                        infos.add(info);
                        smsListener.onProgress(++progress);
                    }
                    cursor.close();
                }
                //保存短信到文件
                Gson gson = new Gson();
                String json = gson.toJson(infos);
                FileWriter fileWriter = null;
                try {
                    fileWriter =
                            new FileWriter(new File(Environment.getExternalStorageDirectory(), "sms.json"));
                    fileWriter.write(json);
                    fileWriter.flush();
                    smsListener.onSuccess();


                } catch (IOException e) {
                    e.printStackTrace();
                    smsListener.onFailed(e);
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    public static void restore(final Activity context, final SMSListener smsListener) {

        new Thread() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory(), "sms.json");
                FileReader fileReader = null;
                try {
                    //从备份文件中取出短信集合
                    fileReader = new FileReader(file);
                    Type typeOfSrc = new TypeToken<ArrayList<SMSInfo>>() {
                    }.getType();
                    Gson gson = new Gson();
                    ArrayList<SMSInfo> infos = gson.fromJson(fileReader, typeOfSrc);

                    smsListener.onMax(infos.size());
                    uri = Uri.parse("content://sms");
                    resolver = context.getContentResolver();
                    resolver.delete(uri, null, null);

                    int progress = 0;
                    for (SMSInfo info : infos) {
                        ContentValues values = new ContentValues();
                        values.put("address", info.address);
                        values.put("date", info.date);
                        values.put("read", info.read);
                        values.put("type", info.type);
                        values.put("body", info.body);

                        resolver.insert(uri, values);
                        SystemClock.sleep(100);
                        smsListener.onProgress(++progress);

                    }
                    smsListener.onSuccess();

                } catch (IOException e) {
                    smsListener.onFailed(e);
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
