package com.chen.phonehelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.broadin.libutils.Logger;

/**
 * Created by ChenHui on 2016/10/26.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_RunningTime = "RunningTime";//运行时间

    public DBHelper(Context context) {
        super(context, "phoneHelper.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        private int id;
//        private String title;
//        private String poster;
//        private int itemCount;//收藏的个数
//        private String type;//Constants.GeDanType.name()

        String sql = "CREATE  TABLE " + TABLE_RunningTime +
                " (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "time VARCHAR)";
        Logger.d("创建表格SQL:" + sql);
        db.execSQL(sql);

//        //GeDanItem
//        String sql2 = "CREATE  TABLE " + TABLE_GeDanItem +
//                " (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
//                "geDanID VARCHAR, " +
//                "poster VARCHAR, " +
//                "title VARCHAR, " +
//                "contentId VARCHAR, " +
//                "artistName VARCHAR, " +
//                "artistID VARCHAR, " +
//                "url VARCHAR, " +
//                "albumID VARCHAR, " +
//                "albumName VARCHAR, " +
//                "source VARCHAR, " +
//                "describe VARCHAR, " +
//                "typeCode VARCHAR, " +
//                "playCount INT)";
//        Logger.d("创建表格SQL:" + sql2);
//        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
