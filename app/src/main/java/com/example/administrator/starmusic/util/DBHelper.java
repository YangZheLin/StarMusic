package com.example.administrator.starmusic.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/4.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql=
                "create table if not exists like_musics("+
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                        "musicname VARCHAR(255),"+
                        "musicpic VARCHAR(255),"+
                        "time INTEGER DEFAULT 0"+
                        "islike INTEGER DEFAULT 0"+
                        ")";//如果初次运行，建立一张t_user表，建表的时候注意，自增是AUTOINCREMENT，而不是mysql的AUTO_INCREMENT
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
