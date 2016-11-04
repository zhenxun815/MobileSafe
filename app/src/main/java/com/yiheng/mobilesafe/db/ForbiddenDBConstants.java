package com.yiheng.mobilesafe.db;

/**
 * Created by Yiheng on 2016/11/4 0004.
 */

public interface ForbiddenDBConstants {
    String DB_NAME = "forbidden.db";
    int DB_VERSION = 1;

    String TABLE_NAME = "forbiddenlist";
    String COLUMN_NUMBER = "number";
    String COLUMN_TYPE = "type";
    /**
     * 黑名单表的创建语句
     */
    String TABLE_CREATE = "create table " + TABLE_NAME
            + "(_id integer primary key autoincrement," + COLUMN_NUMBER
            + " varchar unique," + COLUMN_TYPE + " integer)";
}
