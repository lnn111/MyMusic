package com.app.mymusic;

import android.test.AndroidTestCase;
import android.util.Log;

import com.app.mymusic.model.User;
import com.app.mymusic.model.UserDao;

import java.util.List;

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
        UserDao dao=new UserDao(getContext());
        User u1=new User();
        u1.setName("qqqq");
        User u2=new User();
        u2.setName("wwww");
        User u3=new User();
        u3.setName("eee");
        dao.add(u1);
        dao.add(u2);
        dao.add(u3);
    }
    public void testQuery()
    {
        UserDao dao=new UserDao(getContext());
        List<User> users= dao.getAllUser();
        for(User user:users)
        {
            Log.d("CCC",user.getName()+user.getId());
        }
    }
    public void deleteUser()
    {
        UserDao dao=new UserDao(getContext());

    }
}
