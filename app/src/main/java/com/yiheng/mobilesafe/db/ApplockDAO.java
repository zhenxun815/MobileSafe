package com.yiheng.mobilesafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.yiheng.mobilesafe.utils.ConstantUtils;

import java.util.ArrayList;

/**
 * Created by Yiheng on 2016/11/5 0005.
 */

public class ApplockDAO {
    private ApplockDBHelper mHelper;
    private Context context;

    public ApplockDAO(Context context) {
        super();
        this.context = context;
        mHelper = new ApplockDBHelper(context);
    }

    /**
     * 添加数据
     *
     * @param packageName
     *         app名字
     *
     * @return boolean 是否添加成功
     */
    public boolean insert(String packageName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ApplockDBConstants.COLUMN_PACKAGENAME, packageName);

        String table = ApplockDBConstants.TABLE_NAME;
        //        table     String: the table to insert the row into
        //        nullColumnHack    String: optional; may be null. SQL doesn't allow inserting a completely empty row without naming at least one column name. If your provided values is empty, no column names are known and an empty row can't be inserted. If not set to null, the nullColumnHack parameter provides the name of nullable column name to explicitly insert a NULL into in the case where your values is empty.
        //        values     ContentValues: this map contains the initial column values for the row. The keys should be the column names and the values the column values
        //        return    long  the row ID of the newly inserted row, or -1 if an error occurred
        long id = db.insert(table, null, values);

        db.close();

        Uri uri = Uri.parse(ConstantUtils.APPLOCK_DB_CHANGED);
        context.getContentResolver().notifyChange(uri, null);

        return id != -1;
    }

    /**
     * 删除数据
     *
     * @param packageName
     *         app名字
     *
     * @return boolean 是否删除成功
     */
    public boolean delete(String packageName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String table = ApplockDBConstants.TABLE_NAME;
        String whereClause = ApplockDBConstants.COLUMN_PACKAGENAME + "=?";
        String[] whereArgs = new String[]{packageName};
        //        Parameters:
        //        table - the table to delete from
        //        whereClause - the optional WHERE clause to apply when deleting. Passing null will delete all rows.
        //        whereArgs - You may include ?s in the where clause, which will be replaced by the values from whereArgs. The values will be bound as Strings.
        //        Returns:
        //        return    int   the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.

        int delete = db.delete(table, whereClause, whereArgs);

        db.close();

        Uri uri = Uri.parse(ConstantUtils.APPLOCK_DB_CHANGED);
        context.getContentResolver().notifyChange(uri, null);

        return delete > 0;
    }


    /**
     * 查找加锁应用
     *
     * @return ArrayList<AppInfo>
     */
    public ArrayList<String> queryLockedList() {
        ArrayList<String> packageNames = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        //        String sql = "select packagename from applocklist.db";
        String sql = "select " + ApplockDBConstants.COLUMN_PACKAGENAME + " from "
                + ApplockDBConstants.TABLE_NAME;
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
                String packageName = cursor.getString(0);

                packageNames.add(packageName);
            }
            cursor.close();

        }
        db.close();
        return packageNames;
    }

    public boolean isLocked(String packageName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
       // System.out.println("-------------------db创建成功---------------");
        // String sql = "select * from applock.db where packagename =?";
        String sql = "select * from "
                + ApplockDBConstants.TABLE_NAME
                + " where "
                + ApplockDBConstants.COLUMN_PACKAGENAME
                + "=?";
        String[] selectionArgs = new String[]{ packageName };
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        boolean isLocked = false;
        if (null != cursor) {
            if (cursor.moveToNext()) {
                //有数据说明被加锁
                isLocked = true;
            }
            cursor.close();
        }
        db.close();
        return isLocked;
    }

}
