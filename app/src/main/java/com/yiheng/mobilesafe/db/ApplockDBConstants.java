package com.yiheng.mobilesafe.db;

/**
 * Created by Yiheng on 2016/11/4 0004.
 */

public interface ApplockDBConstants {
    String DB_NAME = "applock.db";
    int DB_VERSION = 1;

    String TABLE_NAME = "locklist";
    String COLUMN_PACKAGENAME = "packagename";
    /**
     * Applock表的创建语句
     */
    String TABLE_CREATE = "create table " + TABLE_NAME
            + "(_id integer primary key autoincrement," + COLUMN_PACKAGENAME
            + " varchar unique)";
}
