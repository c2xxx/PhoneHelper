package com.chen.phonehelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chen.phonehelper.bean.RunningTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenHui on 2017/9/12.
 */

public class RunningTimeDao {
    //        private int id;
    //        private String time;
    private Context mContext;

    public RunningTimeDao(Context mContext) {
        this.mContext = mContext;
    }

    public void insert(RunningTime item) {
        DBHelper helper = new DBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", item.getTime());
        db.delete(DBHelper.TABLE_RunningTime, "time=?", new String[]{item.getTime()});
        db.insert(DBHelper.TABLE_RunningTime, null, values);
        db.close();
    }

    /**
     * @param begin
     * @param end
     * @return
     */
    public List<RunningTime> selectBetweenList(String begin, String end) {
        List<RunningTime> list = new ArrayList<>();
        DBHelper helper = new DBHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        /*
        * db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        * 第一个参数：表名
        * 第二个参数：返回的列 null表示返回所有列
        * 第三个参数：选择条件，没有选择条件写null
        * 第四个参数：选择条件的参数，没有选择条件参数写null
        * 第五个参数：分组条件，没有写null
        * 第六个参数：分组条件，没有写null
        * 第七个参数:分组条件，没有写null
        * 返回值：查询结果的记录的条数
        */
        String[] condition = {begin, end};
        Cursor cursor = db.query(DBHelper.TABLE_RunningTime,
                null, " time > ? and time < ? ", condition, null, null, null, null);
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            RunningTime item = new RunningTime();
            item.setId(id);
            item.setTime(time);
            list.add(item);
//            Logger.object("结果：", item);
        }
        //释放资源
        cursor.close();
        db.close();
        return list;
    }

    /**
     * @param count
     * @param isDesc 是否倒序
     * @return
     */
    public List<RunningTime> selectList(int count, boolean isDesc) {
        List<RunningTime> list = new ArrayList<>();
        DBHelper helper = new DBHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        /*
        * db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        * 第一个参数：表名
        * 第二个参数：返回的列 null表示返回所有列
        * 第三个参数：选择条件，没有选择条件写null
        * 第四个参数：选择条件的参数，没有选择条件参数写null
        * 第五个参数：分组条件，没有写null
        * 第六个参数：分组条件，没有写null
        * 第七个参数:分组条件，没有写null
        * 返回值：查询结果的记录的条数
        */
        String[] condition = {};
        Cursor cursor = db.query(DBHelper.TABLE_RunningTime,
                null, null, null, null, null, isDesc ? "_id desc" : null, "" + count);
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            RunningTime item = new RunningTime();
            item.setId(id);
            item.setTime(time);
            list.add(item);
//            Logger.object("结果：", item);
        }
        //释放资源
        cursor.close();
        db.close();
        return list;
    }
}
