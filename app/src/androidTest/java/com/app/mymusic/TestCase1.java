package com.app.mymusic;

import android.test.AndroidTestCase;
import android.util.Log;

import com.app.mymusic.utils.LogUtil;

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

    public void testAdd()
    {
//        UserDao dao=new UserDao(getContext());
//        User u1=new User();
//        u1.setName("qqqq");
//        User u2=new User();
//        u2.setName("wwww");
//        User u3=new User();
//        u3.setName("eee");
//        dao.add(u1);
//        dao.add(u2);
//        dao.add(u3);
    }
    public void testQuery()
    {
//        UserDao dao=new UserDao(getContext());
//        List<User> users= dao.getAllUser();
//        for(User user:users)
//        {
//            Log.d("CCC",user.getName()+user.getId());
//        }
    }
    public void deleteUser()
    {
//        UserDao dao=new UserDao(getContext());

    }
    public void testlll()
    {
        String str1="http://song.music.response.itmf.cn/ed5cee2e7a56c2350d3577ef67ed0400/57a988e0/G039/M03/06/1B/Zw0DAFZHZ42APirdABBH1Nk2EtE248.m4a";
        String key="http://song.music.response.itmf.cn/";
        str1=str1.replace(key,"");
        LogUtil.showLog(str1);
    }
}
