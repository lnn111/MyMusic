package com.app.mymusic.utils;

import java.util.Observable;


/**
 * Created by Administrator on 2016/7/19.
 */
public class ObserverUtil extends Observable {

    private static ObserverUtil myObservable;
    public static ObserverUtil getObservable()
    {
        if(myObservable==null)
        {
            myObservable=new ObserverUtil();
        }
        return myObservable;
    }
    public void setMessage(Object data)
    {
        myObservable.setChanged();
        myObservable.notifyObservers(data);
    }
}
