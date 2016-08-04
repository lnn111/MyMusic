package com.app.mymusic.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.mymusic.R;
import com.app.mymusic.utils.LogUtil;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String keyword=getIntent().getStringExtra("keyword");
        LogUtil.showLog("SearchActivity-onCreate"+keyword);
    }
}
