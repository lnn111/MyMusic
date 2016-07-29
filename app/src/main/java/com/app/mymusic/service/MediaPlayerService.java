package com.app.mymusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.app.mymusic.common.Constants;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.model.SongMessage;
import com.app.mymusic.utils.LogUtil;
import com.app.mymusic.utils.MediaPlayerUtil;
import com.app.mymusic.utils.ObserverUtil;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/7/18.
 */
public class MediaPlayerService extends Service implements Observer{
    /**
     * 音频管理
     */
    private AudioManager audioManager;
    private Boolean isFirstStart = false;
    private MediaPlayer mPlayer;
    private Mp3Info currentMp3Info;
    private String path;
    private SongMessage songMessage;

    public static Boolean isServiceRunning = false;
    public static Boolean isPlaying = true;
    private ExecutorService executorService;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogUtil.showLog("service---onCreat");
        Context context=MediaPlayerService.this.getBaseContext();
        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ObserverUtil.getObservable().addObserver(this);
        executorService= Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isServiceRunning=true;
        if(!isFirstStart)
        {
            isFirstStart=false;
            play();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    private void play() {
        if(mPlayer==null)
        {
            mPlayer=new MediaPlayer();
        }

        songMessage=MediaPlayerUtil.getSongMessage();
        currentMp3Info=songMessage.getMp3Info();
        try {
            mPlayer.reset();
            mPlayer.setDataSource(currentMp3Info.getPath());
            mPlayer.prepare();
            if(songMessage.getToProgress()!=0)
            {
                mPlayer.seekTo(songMessage.getToProgress());
            }
            mPlayer.start();
            isPlaying=true;
            songMessage.setType(SongMessage.play);

            startProgressThread();
        } catch (IOException e) {
            isPlaying=false;
            e.printStackTrace();
        }
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int currentIndex=songMessage.getCurrentIndex();
                switch (Constants.PLAY_MODE)
                {
                    case 0:
                        currentIndex=currentIndex<songMessage.getMp3InfoList().size()-1?currentIndex+1:0;
                        break;
                    case 1:
                        Random r=new Random();
                        currentIndex=r.nextInt(songMessage.getMp3InfoList().size());
                        break;
                }
                songMessage.setCurrentIndex(currentIndex);
                songMessage.setType(SongMessage.next);
                songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                ObserverUtil.getObservable().setMessage(songMessage);
            }
        });
    }

    public void startProgressThread()
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(isPlaying)
                {
                    try {
                        Thread.sleep(500);
                        int position= mPlayer.getCurrentPosition();
                        songMessage.setProgress(position);
                        songMessage.setProgress_type(SongMessage.UPDATE_PROGRESS);
                        ObserverUtil.getObservable().setMessage(songMessage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void pause()
    {
        if(mPlayer==null)
        {
            mPlayer=new MediaPlayer();
        }
        if(mPlayer.isPlaying())
        {
            isPlaying=false;
            mPlayer.pause();
            LogUtil.showLog("pause");
            songMessage.setType(SongMessage.pause);
        }
    }
    private void pauseToPlay() {
        try {
            if(!isPlaying)
            {
                mPlayer.start();
                isPlaying=true;
                startProgressThread();
                LogUtil.showLog("toPlaying");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if(songMessage.getProgress_type()!=SongMessage.UPDATE_PROGRESS)
        {
            songMessage= (SongMessage)data;
            currentMp3Info= songMessage.getMp3Info();
            switch (songMessage.getType())
            {
                case SongMessage.play:
                        pauseToPlay();
                    break;
                case SongMessage.pause:
                    pause();
                    break;
                case SongMessage.next:
                        play();
                    break;
            }
        }

    }
    @Override
    public void onDestroy() {
        LogUtil.showLog("service---onDestroy");
        mPlayer.release();
        isServiceRunning=false;
        super.onDestroy();
    }


}
