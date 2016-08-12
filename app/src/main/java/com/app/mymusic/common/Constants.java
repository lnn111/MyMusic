package com.app.mymusic.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/7/14.
 */
public class Constants {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "happy_player.db";
    public static final String DB_DOWNLOAD= "download.db";
    public static int PLAY_MODE=0;
    public static String DIR= Environment.getExternalStorageDirectory()+ File.separator+"360"+File.separator;

}
