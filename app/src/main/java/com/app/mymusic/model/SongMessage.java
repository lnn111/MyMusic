package com.app.mymusic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class SongMessage {

    public static final int play = 11;
    public static final int pause = 12;
    public static final int next = 13;
    public static final int UPDATE_PROGRESS = 14;
    public static final int PAUSE_PROGRESS = 15;

    private int progress_type;

    private int type;

    private int num;// 歌曲数目

    private int progress; // 快进进度

    private Mp3Info mp3Info;// 歌曲数据

    private int toProgress=0;

    private List<Mp3Info> mp3InfoList=new ArrayList<Mp3Info>();//播放列表

    private int currentIndex;

    public SongMessage() {
    }

    public int getToProgress() {
        return toProgress;
    }

    public void setToProgress(int toProgress) {
        this.toProgress = toProgress;
    }

    public int getProgress_type() {
        return progress_type;
    }

    public void setProgress_type(int progress_type) {
        this.progress_type = progress_type;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Mp3Info getMp3Info() {
        return  mp3InfoList.get(currentIndex);
    }

    public List<Mp3Info> getMp3InfoList() {
        return mp3InfoList;
    }

    public void setMp3InfoList(List<Mp3Info> mp3InfoList) {
            this.mp3InfoList.clear();
            this.mp3InfoList.addAll(mp3InfoList);
    }

    public void setMp3Info(Mp3Info mp3Info) {
        this.mp3Info = mp3Info;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
