package com.app.mymusic.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.adapter.MyAdapter;
import com.app.mymusic.db.SongDB;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.utils.MediaPlayerUtil;
import com.app.mymusic.view.SlideBar;
import com.app.mymusic.widget.PopWindowLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanMusicActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    @BindView(R.id.scan_card)
    CardView scanCard;
    @BindView(R.id.scan_num)
    TextView scanNum;
    private String ALL_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private String[] sections = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private Button scanBtn;
    private SlideBar mSlideBar;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private TextView floatletter;
    private SongDB songDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_music);
        ButterKnife.bind(this);
        scanBtn = (Button) findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(this);
        mSlideBar = (SlideBar) findViewById(R.id.scan_bar);
        mSlideBar.setOnTouchLetterListenner(new TouchLetterListenner());
        mRecyclerView = (RecyclerView) findViewById(R.id.scan_list);
        floatletter = (TextView) findViewById(R.id.floatletter);
        songDB = new SongDB(ScanMusicActivity.this);
        lists = songDB.getListSortByCategory();
        if (lists != null && lists.size() > 0) {
            maps = songDB.getMapsByCategory();
            setDatas();
        }
    }
    Map<String, Integer> maps;
    private Dialog mDialog;
    List<Mp3Info> lists;

    @Override
    public void onClick(View v) {
//        mDialog= DialogUtil.showDialog(this,null);
//        mDialog.show();
        songDB.getOriginalDatas();
        List<Mp3Info> datas = songDB.getListSortByCategory();
        lists.clear();
        lists.addAll(datas);
        maps = songDB.getMapsByCategory();
        if (myAdapter == null) {
            setDatas();
        } else {
            myAdapter.notifyDataSetChanged();
            scanNum.setText(lists.size()+"首歌曲");
        }
        scanBtn.setVisibility(View.GONE);
    }

    public void setDatas() {
        scanNum.setText(lists.size()+"首歌曲");
        myAdapter = new MyAdapter(R.layout.item_scan, lists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//          linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (myAdapter != null) {
            mRecyclerView.setAdapter(myAdapter);
            myAdapter.setOnRecyclerViewItemClickListener(new RecyclerViewItemClickListener());
        }
        if (mDialog != null) {
            mDialog.cancel();
        }
    }

    @Override
    public void update(Observable observable, Object data) {

    }


    class TouchLetterListenner implements SlideBar.OnTouchLetterListenner {
        @Override
        public void OnTouchLetterChange(MotionEvent event, String str) {
            floatletter.setVisibility(View.VISIBLE);
            floatletter.setText(str);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                floatletter.setVisibility(View.GONE);
            }
            int position = 0;
            if (maps != null && str != null) {
                position = maps.get(str);

            }
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    class RecyclerViewItemClickListener implements BaseQuickAdapter.OnRecyclerViewItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            MediaPlayerUtil util = new MediaPlayerUtil(ScanMusicActivity.this, lists, position);
            util.play();
            scanCard.setVisibility(View.VISIBLE);
            PlayControlFragment playControlFragment = new PlayControlFragment();
            playControlFragment.setOnPopWindowInter(new PlayControlFragment.PopWindowInter() {
                @Override
                public void onWindow() {
                    PopWindowLayout pwLyout = new PopWindowLayout(lists);
                    PopupWindow pw = pwLyout.getPopWindow(ScanMusicActivity.this);
//                            scanCard.setVisibility(View.GONE);
                    pw.showAsDropDown(mRecyclerView, -60, -360, Gravity.BOTTOM);
                }
            });
        }
    }

}
