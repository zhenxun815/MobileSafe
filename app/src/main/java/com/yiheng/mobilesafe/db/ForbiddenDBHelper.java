package com.yiheng.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yiheng on 2016/11/5 0005.
 */

public class ForbiddenDBHelper extends SQLiteOpenHelper {

    public ForbiddenDBHelper(Context context) {
        super(context, ForbiddenDBConstants.DB_NAME, null, ForbiddenDBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ForbiddenDBConstants.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
