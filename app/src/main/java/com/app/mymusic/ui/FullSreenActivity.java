package com.app.mymusic.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.model.SongMessage;
import com.app.mymusic.utils.LogUtil;
import com.app.mymusic.utils.ObserverUtil;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FullSreenActivity extends AppCompatActivity implements Observer {
    @BindView(R.id.full_bg_img)
    ImageView fullBgImg;
    @BindView(R.id.full_title_tv)
    TextView fullTitleTv;
    @BindView(R.id.full_artist_tv)
    TextView fullArtistTv;
    @BindView(R.id.full_starttime_tv)
    TextView fullStarttimeTv;
    @BindView(R.id.full_endtime_tv)
    TextView fullEndtimeTv;
    @BindView(R.id.full_seekbar)
    SeekBar fullSeekbar;
    @BindView(R.id.full_prev_btn)
    ImageButton fullPrevBtn;
    @BindView(R.id.full_play_btn)
    ImageButton fullPlayBtn;
    @BindView(R.id.full_next_btn)
    ImageButton fullNextBtn;
    @BindView(R.id.full_favorite_btn)
    ImageButton fullFavoriteBtn;
    @BindView(R.id.full_mode_btn)
    ImageButton fullModeBtn;
    @BindView(R.id.full_download_btn)
    ImageButton fullDownloadBtn;
    @BindView(R.id.full_playlist_btn)
    ImageButton fullPlaylistBtn;
    @BindView(R.id.full_lin)
    LinearLayout fullLin;
    private SongMessage songMessage;
    private Mp3Info mp3Info;
    private long duration;

    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    setDatas();

                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_sreen);
        ButterKnife.bind(this);
        ObserverUtil.getObservable().addObserver(this);
    }


    @OnClick({R.id.full_fragment, R.id.full_prev_btn, R.id.full_play_btn, R.id.full_next_btn, R.id.full_favorite_btn, R.id.full_mode_btn, R.id.full_download_btn, R.id.full_playlist_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.full_fragment:
                break;
            case R.id.full_prev_btn:
                break;
            case R.id.full_play_btn:
                break;
            case R.id.full_next_btn:
                break;
            case R.id.full_favorite_btn:
                break;
            case R.id.full_mode_btn:
                break;
            case R.id.full_download_btn:
                break;
            case R.id.full_playlist_btn:
                break;
        }
    }
    @Override
    public void update(Observable observable, Object data) {
        songMessage = (SongMessage) data;
        mp3Info = songMessage.getMp3Info();
        fullSeekbar.setMax((int) mp3Info.getDuration());
        duration=mp3Info.getDuration();
        fullSeekbar.setProgress((int) songMessage.getProgress());
        Message msg=mHandler.obtainMessage();
        msg.what=0;
        mHandler.sendMessage(msg);
    }

    private void setDatas() {
        fullTitleTv.setText(mp3Info.getTitle());
        fullArtistTv.setText(mp3Info.getArtist());
        double d=duration/(float)60000;
        DecimalFormat df = new DecimalFormat("0.00");
        fullEndtimeTv.setText(df.format(d));
        fullStarttimeTv.setText(df.format(songMessage.getProgress()/(float)60000));
    }
}
