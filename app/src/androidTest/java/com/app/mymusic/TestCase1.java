package com.app.mymusic;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/14.
 */
public class TestCase1 extends AndroidTestCase {
    public void  testIO()
    {
        String name="1f1sdffsdf.mp3";
        String str=name.split(".")[0];
        Log.d("CCC",str);
    }


}
