package com.yiheng.mobilesafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yiheng.mobilesafe.bean.ForbiddenInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Yiheng on 2016/11/5 0005.
 */

public class ForbiddenDAO {
    private ForbiddenDBHelper mHelper;
    private Context context;

    public ForbiddenDAO(Context context) {
        super();
        this.context = context;
        mHelper = new ForbiddenDBHelper(context);
    }

    /**
     * 添加数据
     *
     * @param number 要屏蔽的号码
     * @param type   屏蔽类型
     * @return boolean 是否添加成功
     */
    public boolean insert(String number, int type) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ForbiddenDBConstants.COLUMN_NUMBER, number);
        values.put(ForbiddenDBConstants.COLUMN_TYPE, type);

        String table = ForbiddenDBConstants.TABLE_NAME;
        //        table     String: the table to insert the row into
        //        nullColumnHack    String: optional; may be null. SQL doesn't allow inserting a completely empty row without naming at least one column name. If your provided values is empty, no column names are known and an empty row can't be inserted. If not set to null, the nullColumnHack parameter provides the name of nullable column name to explicitly insert a NULL into in the case where your values is empty.
        //        values     ContentValues: this map contains the initial column values for the row. The keys should be the column names and the values the column values
        //        return    long  the row ID of the newly inserted row, or -1 if an error occurred
        long id = db.insert(table, null, values);

        db.close();
        return id != -1;
    }

    /**
     * 删除数据
     *
     * @param number 要屏蔽的号码
     * @return boolean 是否删除成功
     */
    public boolean delete(String number) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String table = ForbiddenDBConstants.TABLE_NAME;
        String whereClause = ForbiddenDBConstants.COLUMN_NUMBER + "=?";
        String[] whereArgs = new String[]{number};
        //        Parameters:
        //        table - the table to delete from
        //        whereClause - the optional WHERE clause to apply when deleting. Passing null will delete all rows.
        //        whereArgs - You may include ?s in the where clause, which will be replaced by the values from whereArgs. The values will be bound as Strings.
        //        Returns:
        //        return    int   the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.

        int delete = db.delete(table, whereClause, whereArgs);

        db.close();
        return delete > 0;
    }

    /**
     * 更新数据
     *
     * @param number 要屏蔽的号码
     * @param type   屏蔽类型
     * @return boolean 是否更新成功
     */
    public boolean update(String number, int type) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String table = ForbiddenDBConstants.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(ForbiddenDBConstants.COLUMN_TYPE, type);
        String whereClause = ForbiddenDBConstants.COLUMN_NUMBER + "=?";
        String[] whereArgs = {number};
        //        Parameters:
        //        table - the table to update in
        //        values - a map from column names to new column values. null is a valid value that will be translated to NULL.
        //        whereClause - the optional WHERE clause to apply when updating. Passing null will update all rows.
        //        whereArgs - You may include ?s in the where clause, which will be replaced by the values from whereArgs. The values will be bound as Strings.
        //        Returns:
        //        the number of rows affected
        int update = db.update(table, values, whereClause, whereArgs);
        db.close();

        return update > 0;
    }

    /**
     * 查找全部数据
     *
     * @return ArrayList<ForbiddenInfo>
     */
    public ArrayList<ForbiddenInfo> queryAll() {
        ArrayList<ForbiddenInfo> infos = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        //        String sql = "select number,type from forbidden.db";
        String sql = "select " + ForbiddenDBConstants.COLUMN_NUMBER + ","
                + ForbiddenDBConstants.COLUMN_TYPE + " from "
                + ForbiddenDBConstants.TABLE_NAME;
        //        Parameters
        //                sql
        //        String: the SQL query. The SQL string must not be ; terminated
        //                selectionArgs
        //        String: You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.

        //        Returns
        //                Cursor
        //        A Cursor object, which is positioned before the first entry. Note that Cursors are not synchronized, see the documentation for more details.
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                String number = cursor.getString(0);
                int type = cursor.getInt(1);
                ForbiddenInfo info = new ForbiddenInfo(number, type);
                infos.add(info);
            }
            cursor.close();

        }
        db.close();
        return infos;
    }

    /**
     * 查找部分数据
     *
     * @param size   要查找数据的个数
     * @param offset 查找起始位置
     * @return ArrayList<ForbiddenInfo>
     */
    public ArrayList<ForbiddenInfo> queryPart(int size, int offset) {
        ArrayList<ForbiddenInfo> infos = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //        String sql = "select number,type from forbidedn,db limit size offset offset";
        String sql = "select " + ForbiddenDBConstants.COLUMN_NUMBER + ","
                + ForbiddenDBConstants.COLUMN_TYPE + " from "
                + ForbiddenDBConstants.TABLE_NAME + " limit "
                + size + " offset "
                + offset;

        Cursor cursor = db.rawQuery(sql, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(0);
                int type = cursor.getInt(1);
                ForbiddenInfo info = new ForbiddenInfo(number, type);
                infos.add(info);
            }
            cursor.close();

        }
        db.close();
        return infos;
    }

    public int queryType(String number) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //        String sql = "select type from forbiden.db where number =?";
        String sql = "select "
                + ForbiddenDBConstants.COLUMN_TYPE + " from "
                + ForbiddenDBConstants.TABLE_NAME + " where "
                + ForbiddenDBConstants.COLUMN_NUMBER + " =?";
        String[] selectionArgs = new String[]{number};
        //        Parameters
        //                sql
        //        String: the SQL query. The SQL string must not be ; terminated
        //                selectionArgs
        //        String: You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.

        //        Returns
        //                Cursor
        //        A Cursor object, which is positioned before the first entry. Note that Cursors are not synchronized, see the documentation for more details.
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        int type = ForbiddenInfo.TYPE_NONE;
        if (null != cursor) {
            while (cursor.moveToNext()) {
                type = cursor.getInt(0);
            }
            cursor.close();
        }
        db.close();
        return type;
    }
}
