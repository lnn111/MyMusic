package com.app.mymusic.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.service.FloatMusicService;
import com.app.mymusic.utils.LogUtil;
import com.app.mymusic.widget.SearchEditText;

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


    BroadcastReceiver re=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction())
            {
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
                LogUtil.showLog("search");
            }
        });
        IntentFilter filter=new IntentFilter();
        filter.addAction("Previous");
        filter.addAction("Pause");
        filter.addAction("Next");
        filter.addAction("hello");
        registerReceiver(re,filter);

    }

    @OnClick({R.id.scan_tv, R.id.mian_download_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_tv:
                startActivity(new Intent(MainActivity.this, ScanMusicActivity.class));
                startService(new Intent(MainActivity.this, FloatMusicService.class));
                break;
            case R.id.mian_download_tv:
                       test();
                break;
        }
    }

    private void test() {
        int playPauseButtonPosition=0;
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews view=new RemoteViews(this.getPackageName(),R.layout.notification_play);
        view.setTextViewText(R.id.pc_name_tv,"gequ mingzi ");
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
        notification1.flags=Notification.FLAG_AUTO_CANCEL;
        notification1.defaults=Notification.DEFAULT_SOUND;
        // 通知的时间
        notification1.when = System.currentTimeMillis();
        PendingIntent pi= PendingIntent.getActivity(this,1,new Intent(this,MainActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);
        notification1.contentIntent=pi;

        Intent i2=new Intent();
        i2.setAction("hello");
        PendingIntent p2=PendingIntent.getBroadcast(this,2,i2,PendingIntent.FLAG_CANCEL_CURRENT);
        view.setOnClickPendingIntent(R.id.pc_play_btn,p2);
        manager.notify(0,notification1);
    }

    public void testRealm()
    {

    }

}
