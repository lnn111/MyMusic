package com.app.mymusic.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.model.NetMusic;
import com.app.mymusic.service.FloatMusicService;
import com.app.mymusic.utils.DownLoadUtil;
import com.app.mymusic.utils.LogUtil;
import com.app.mymusic.widget.SearchEditText;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.scan_tv)
    TextView scanTv;
    @BindView(R.id.mian_serch_edt)
    SearchEditText mianSerchEdt;
    @BindView(R.id.mian_download_tv)
    TextView mianDownloadTv;
    @BindView(R.id.main_test1)
    TextView mainTest1;
    @BindView(R.id.main_test2)
    TextView mainTest2;
    @BindView(R.id.main_test3)
    TextView mainTest3;
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.main_all_tv)
    TextView mainAllTv;
    @BindView(R.id.main_drawerlayout)
    DrawerLayout mainDrawerlayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String str = (String) msg.obj;
            switch (msg.what) {
                case 110:
                    LogUtil.showLog(str);
                    jsonUtil(str);
                    break;
                default:
                    break;
            }
        }
    };

    BroadcastReceiver re = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "Previous":
                    break;
                case "Pause":
                    break;
                case "Next":
                    break;
            }
            LogUtil.showLog(intent.getAction());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mianSerchEdt.setOnEditTextListener(new SearchEditText.OnEditTextListener() {
            @Override
            public void OnSearch() {
                String keyword = mianSerchEdt.getText().toString().trim();
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("keyword", keyword);
                startActivity(i);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("Previous");
        filter.addAction("Pause");
        filter.addAction("Next");
        filter.addAction("hello");
        registerReceiver(re, filter);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle=new ActionBarDrawerToggle(this,mainDrawerlayout,mainToolbar,R.string.app_name,R.string.app_name)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        mainDrawerlayout.addDrawerListener(mDrawerToggle);
    }

    @OnClick({R.id.scan_tv, R.id.mian_download_tv, R.id.main_test1, R.id.main_test2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_tv:
                startActivity(new Intent(MainActivity.this, ScanMusicActivity.class));
                startService(new Intent(MainActivity.this, FloatMusicService.class));
                break;
            case R.id.mian_download_tv:
                startActivity(new Intent(MainActivity.this, DownLoadActivity.class));
                break;
            case R.id.main_test1:
//                startActivity(new Intent(MainActivity.this, Fullscreen1Activity.class));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String httpUrl = "http://apis.baidu.com/geekery/music/query";
                        String s = "十年";
                        try {
                            s = URLEncoder.encode(s, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String httpArg = "s=love&size=10&page=1";
//                        String httpArg = "s="+s+"&size=10&page=1";
                        LogUtil.showLog(httpArg);
                        DownLoadUtil.request(httpUrl, httpArg, mHandler);
                    }
                }).start();

                break;
            case R.id.main_test2:
                startActivity(new Intent(MainActivity.this, DownLoadActivity.class));
                break;
        }
    }

    private void test() {
        int playPauseButtonPosition = 0;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews view = new RemoteViews(this.getPackageName(), R.layout.notification_play);
        view.setTextViewText(R.id.pc_name_tv, "gequ mingzi ");
        Notification notification1 = new NotificationCompat.Builder(this)
//                .setStyle(new NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(
//                                new int[]{playPauseButtonPosition}))
                .setContent(view)
                .setSmallIcon(R.drawable.flag)
                .setTicker("ticker")
                .build();

        //定义Notification的各种属性
//        notification1.contentView = view;
        notification1.flags = Notification.FLAG_AUTO_CANCEL;
        notification1.defaults = Notification.DEFAULT_SOUND;
        // 通知的时间
        notification1.when = System.currentTimeMillis();
        PendingIntent pi = PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        notification1.contentIntent = pi;

        Intent i2 = new Intent();
        i2.setAction("hello");
        PendingIntent p2 = PendingIntent.getBroadcast(this, 2, i2, PendingIntent.FLAG_CANCEL_CURRENT);
        view.setOnClickPendingIntent(R.id.pc_play_btn, p2);
        manager.notify(0, notification1);
    }

    public void testRealm() {

    }

    public void jsonUtil(String str) {
        NetMusic n = new Gson().fromJson(str, NetMusic.class);
        LogUtil.showLog(n.getCode() + "***");
        LogUtil.showLog(n.getData().getKeyword() + "***");
        LogUtil.showLog(n.getData().getData().get(0).getAlbum_name() + "***");
        LogUtil.showLog(n.getData().getData().get(0).getFilesize() + "***");
        LogUtil.showLog(n.getData().getData().get(0).getDuration() + "***");
        LogUtil.showLog(n.getData().getData().get(0).getExtname() + "***");
    }
}
