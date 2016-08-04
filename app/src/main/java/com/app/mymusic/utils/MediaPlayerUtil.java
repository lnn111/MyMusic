package com.app.mymusic.utils;

import android.content.Context;
import android.content.Intent;

import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.model.SongMessage;
import com.app.mymusic.service.MediaPlayerService;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MediaPlayerUtil implements Observer{

    private List<Mp3Info> lists;
    private static SongMessage songMessage;
    private Context context;
    private static Mp3Info currentMp3Info;
    private  int position;
    public MediaPlayerUtil(Context context,List<Mp3Info> lists,int position) {
        this.context=context;
        this.lists=lists;
        this.position=position;
        songMessage= new SongMessage();
        songMessage.setMp3InfoList(lists);
        songMessage.setCurrentIndex(position);
        currentMp3Info=songMessage.getMp3Info();

    }

    public static SongMessage getSongMessage() {
        return songMessage;
    }

    public static void setSongMessage(SongMessage songMessage) {
        MediaPlayerUtil.songMessage = songMessage;
    }

    public List<Mp3Info> getLists() {
        return lists;
    }

    public static Mp3Info getCurrentMp3Info() {
        return currentMp3Info;
    }


    @Override
    public void update(Observable observable, Object data) {
    }

    public void play()
    {
        songMessage.setMp3Info(currentMp3Info);
//      ObserverUtil.getObservable().setMessage(songMessage);
        Intent i=new Intent(context, MediaPlayerService.class);
        i.putExtra("path",currentMp3Info.getPath());
        context.startService(i);
    }

    private void pause() {
        songMessage.setMp3Info(currentMp3Info);
        ObserverUtil.getObservable().setMessage(songMessage);
    }
    private void playNext() {
        position=position<lists.size()-1?position+1:0;
        currentMp3Info=lists.get(position);
        ObserverUtil.getObservable().setMessage(songMessage);
    }
}
