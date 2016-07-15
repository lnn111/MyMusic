package com.app.mymusic;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.mymusic.adapter.MyAdapter;
import com.app.mymusic.db.SQLDBHlper;
import com.app.mymusic.db.SongDB;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.utils.DialogUtil;
import com.app.mymusic.utils.MediaUtil;
import com.app.mymusic.view.SlideBar;

import java.util.List;
import java.util.Map;

public class ScanMusicActivity extends AppCompatActivity implements View.OnClickListener{

    private String ALL_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private String[] sections = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private  Button scanBtn;
    private SlideBar mSlideBar;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private TextView floatletter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_music);
        scanBtn= (Button) findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(this);
        mSlideBar= (SlideBar) findViewById(R.id.scan_bar);
        mSlideBar.setOnTouchLetterListenner(new TouchLetterListenner());
        mRecyclerView= (RecyclerView) findViewById(R.id.scan_list);
        floatletter= (TextView) findViewById(R.id.floatletter);

    }
    Map<String,Integer> maps;
    private Dialog mDialog;
    @Override
    public void onClick(View v) {
        mDialog= DialogUtil.showDialog(this,null);
        SQLDBHlper helper=new SQLDBHlper(ScanMusicActivity.this);
        SongDB songDB=new SongDB(ScanMusicActivity.this);
        maps=songDB.getMapsByCategory();
        List<Mp3Info> mp3Infos= MediaUtil.getMp3Infos(this);
        helper.getWritableDatabase().execSQL("DELETE FROM "+SongDB.TBL_NAME);
        for (Mp3Info  mp3:mp3Infos)
        {
            songDB.add(mp3 );
        }
        List<Mp3Info> lists= songDB.getListSortByCategory();
        myAdapter=new MyAdapter(R.layout.item_scan,lists);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myAdapter);
        mDialog.cancel();
    }

    class  TouchLetterListenner implements SlideBar.OnTouchLetterListenner
    {
        @Override
        public void OnTouchLetterChange(MotionEvent event, String str) {
            floatletter.setVisibility(View.VISIBLE);
            floatletter.setText(str);
            if(event.getAction()==MotionEvent.ACTION_UP)
            {
                floatletter.setVisibility(View.GONE);
            }
            int i= maps.get(str);
            mRecyclerView.smoothScrollToPosition(i);
        }
    }
}
