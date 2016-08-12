package com.app.mymusic.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.common.Constants;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.model.SongMessage;
import com.app.mymusic.utils.ObserverUtil;
import com.app.mymusic.widget.PopWindowLayout;

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
    @BindView(R.id.full_layout)
    RelativeLayout fullLayout;
    private SongMessage songMessage;
    private Mp3Info mp3Info;
    private long duration;

    private int[] mode_imgs=new int[]{R.drawable.ic_player_mode_all_default,R.drawable.ic_player_mode_random_default
            ,R.drawable.ic_player_mode_single_default};


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
        fullSeekbar.setOnSeekBarChangeListener(new PlaySeekBarListener());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        全屏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        fullLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    @OnClick({R.id.full_fragment, R.id.full_prev_btn, R.id.full_play_btn, R.id.full_next_btn, R.id.full_favorite_btn, R.id.full_mode_btn, R.id.full_download_btn, R.id.full_playlist_btn})
    public void onClick(View view) {
        int currentIndex;
        switch (view.getId()) {
            case R.id.full_fragment:
                break;
            case R.id.full_prev_btn:
                currentIndex = songMessage.getCurrentIndex();
                currentIndex = currentIndex ==0 ? songMessage.getMp3InfoList().size() - 1:currentIndex - 1;
                songMessage.setCurrentIndex(currentIndex);
                songMessage.setType(SongMessage.next);
                songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                ObserverUtil.getObservable().setMessage(songMessage);
                break;
            case R.id.full_play_btn:
                switch (songMessage.getType()) {
                    case SongMessage.pause:
                        fullPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.kg_ic_playing_bar_pause_default));
                        songMessage.setType(SongMessage.play);
                        songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                        ObserverUtil.getObservable().setMessage(songMessage);
                        break;
                    case SongMessage.play:
                        fullPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.kg_ic_playing_bar_play_default));
                        songMessage.setType(SongMessage.pause);
                        songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                        ObserverUtil.getObservable().setMessage(songMessage);
                        break;
                }
                break;
            case R.id.full_next_btn:
                currentIndex = songMessage.getCurrentIndex();
                currentIndex = currentIndex < songMessage.getMp3InfoList().size() - 1 ? currentIndex + 1 : 0;
                songMessage.setCurrentIndex(currentIndex);
                songMessage.setType(SongMessage.next);
                songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
                ObserverUtil.getObservable().setMessage(songMessage);
                break;
            case R.id.full_favorite_btn:
                break;
            case R.id.full_mode_btn:
                switch (Constants.PLAY_MODE)
                {
                    case 0:
                        Constants.PLAY_MODE=1;
                        fullModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
                        Snackbar.make(view, "随机播放", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Constants.PLAY_MODE=2;
                        fullModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
                        Snackbar.make(view, "单曲循环", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Constants.PLAY_MODE=0;
                        fullModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
                        Snackbar.make(view, "顺序播放", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.full_download_btn:
                break;
            case R.id.full_playlist_btn:
                PopWindowLayout pwLyout=new PopWindowLayout(songMessage.getMp3InfoList());
                PopupWindow pw=pwLyout.getPopWindow(FullSreenActivity.this);
//                            scanCard.setVisibility(View.GONE);
                pw.showAsDropDown(fullLin,-60,-360, Gravity.BOTTOM);
                break;
        }
    }
    @Override
    public void update(Observable observable, Object data) {
        songMessage = (SongMessage) data;
        mp3Info = songMessage.getMp3Info();
        fullSeekbar.setMax((int) mp3Info.getDuration());
        duration = mp3Info.getDuration();
        fullSeekbar.setProgress((int) songMessage.getProgress());
        Message msg = mHandler.obtainMessage();
        msg.what = 0;
        mHandler.sendMessage(msg);
    }

    private void setDatas() {
        fullTitleTv.setText(mp3Info.getTitle());
        fullArtistTv.setText(mp3Info.getArtist());
        double d = duration / (float) 60000;
        DecimalFormat df = new DecimalFormat("0.00");
        fullEndtimeTv.setText(df.format(d));
        fullStarttimeTv.setText(df.format(songMessage.getProgress() / (float) 60000));
    }

    private class PlaySeekBarListener implements SeekBar.OnSeekBarChangeListener {
        int changeProgress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            changeProgress=progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            songMessage.setToProgress(changeProgress);
            songMessage.setType(SongMessage.next);
            songMessage.setProgress_type(SongMessage.PAUSE_PROGRESS);
            ObserverUtil.getObservable().setMessage(songMessage);

        }
    }
}
