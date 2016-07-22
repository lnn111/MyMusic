package com.app.mymusic.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.model.SongMessage;
import com.app.mymusic.utils.LogUtil;
import com.app.mymusic.utils.ObserverUtil;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/18.
 */
public class PlayControlFragment extends Fragment implements Observer  {

    @BindView(R.id.pc_album_img)
    ImageView pcAlbumImg;
    @BindView(R.id.pc_seekbar)
    SeekBar pcSeekbar;
    @BindView(R.id.pc_name_tv)
    TextView pcNameTv;
    @BindView(R.id.pc_artiste_tv)
    TextView pcArtisteTv;
    @BindView(R.id.pc_play_btn)
    ImageButton pcPlayBtn;
    @BindView(R.id.pc_next_btn)
    ImageButton pcNextBtn;
    @BindView(R.id.pc_list_btn)
    ImageButton pcListBtn;

    private Mp3Info mp3Info;
    private SongMessage songMessage;
    private Context context;

    private  static  PopWindowInter popWindowInter=null;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    setSongInfo();
                    break;
                default:
                    break;
            }
        }
    };

    public PlayControlFragment() {
        super();
    }

    public void setOnPopWindowInter(PopWindowInter pWindowInter)
    {
        this.popWindowInter=pWindowInter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playcontrolfragment, container, false);
        context=getActivity();
        ButterKnife.bind(this, view);
        ObserverUtil.getObservable().addObserver(this);
        return view;
    }

    @OnClick({R.id.pc_play_btn, R.id.pc_next_btn, R.id.pc_list_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pc_play_btn:
                switch (songMessage.getType())
                {
                    case SongMessage.pause:
                        pcPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.kg_ic_playing_bar_pause_default));
                        songMessage.setType(SongMessage.play);
                        songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                        ObserverUtil.getObservable().setMessage(songMessage);
                        break;
                    case SongMessage.play:
                        pcPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.kg_ic_playing_bar_play_default));
                        songMessage.setType(SongMessage.pause);
                        songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                        ObserverUtil.getObservable().setMessage(songMessage);
                        break;
                }
                break;
            case R.id.pc_next_btn:
                int currentIndex=songMessage.getCurrentIndex();
                currentIndex=currentIndex<songMessage.getMp3InfoList().size()-1?currentIndex+1:0;
                songMessage.setCurrentIndex(currentIndex);
                songMessage.setType(SongMessage.next);
                songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                ObserverUtil.getObservable().setMessage(songMessage);

                break;
            case R.id.pc_list_btn:
                popWindowInter.onWindow();
                break;
        }
    }




    @Override
    public void update(Observable observable, Object data) {
        songMessage= (SongMessage) data;
        mp3Info=songMessage.getMp3Info();
        LogUtil.showLog(mp3Info.getDuration()+"****"+songMessage.getProgress());
        pcSeekbar.setMax((int)mp3Info.getDuration());
        pcSeekbar.setProgress((int)songMessage.getProgress());
        Message msg=mHandler.obtainMessage();
        msg.what=0;
        mHandler.sendMessage(msg);

    }

    private void setSongInfo() {
        pcNameTv.setText(mp3Info.getTitle());
        pcArtisteTv.setText(mp3Info.getArtist());
    }

    public  interface PopWindowInter
    {
        void onWindow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.showLog("destroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.showLog("start");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.showLog("stop");
    }
}
