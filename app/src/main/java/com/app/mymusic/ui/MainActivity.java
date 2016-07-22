package com.app.mymusic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.mymusic.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.scan_tv)
    TextView scanTv;
    @BindView(R.id.main_local_tv)
    TextView mainLocalTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.scan_tv, R.id.main_local_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_tv:
                startActivity(new Intent(MainActivity.this, ScanMusicActivity.class));
                break;
            case R.id.main_local_tv:
                startActivity(new Intent(MainActivity.this, ScanMusicActivity.class));
                break;
        }
    }
}
