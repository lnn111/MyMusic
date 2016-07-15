package com.app.mymusic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.mymusic.common.Constants;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SQLDBHlper extends SQLiteOpenHelper {
    private  static SQLDBHlper mSQLDBHlper;

    public static  SQLDBHlper getSQLDBHlper(Context context)
    {
        if(mSQLDBHlper==null)
        {

            mSQLDBHlper=new SQLDBHlper(context);
        }
        return mSQLDBHlper;
    }
    public SQLDBHlper(Context context){

        super(context, Constants.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(SongDB.CREATE_TBL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("drop table if exists " + SongDB.TBL_NAME);

        onCreate(db);
    }
}
