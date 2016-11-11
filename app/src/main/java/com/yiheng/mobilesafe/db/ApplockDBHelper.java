package com.yiheng.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yiheng on 2016/11/5 0005.
 */

public class ApplockDBHelper extends SQLiteOpenHelper {

    public ApplockDBHelper(Context context) {
        super(context, ApplockDBConstants.DB_NAME, null, ApplockDBConstants.DB_VERSION);
        System.out.println("----------------- ApplockDBHelper -----------------");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ApplockDBConstants.TABLE_CREATE);
        System.out.println("----------------- ApplockDBConstants.TABLE_CREATE -----------------");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
