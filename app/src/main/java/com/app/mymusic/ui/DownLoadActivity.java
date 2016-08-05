package com.app.mymusic.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.app.mymusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownLoadActivity extends AppCompatActivity {

    @BindView(R.id.down_tab)
    TabLayout downTab;
    @BindView(R.id.down_viewpager)
    ViewPager downViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        ButterKnife.bind(this);
    }
}
