package com.app.mymusic.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.app.mymusic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownLoadActivity extends AppCompatActivity {

    @BindView(R.id.down_tab)
    TabLayout downTab;
    @BindView(R.id.down_viewpager)
    ViewPager downViewpager;

    private String[] mTitle = new String[2];
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        ButterKnife.bind(this);
        mTitle[0] = "已下载";
        mTitle[1] = "正在下载";
        DownLoadOverFragment fragment1 = new DownLoadOverFragment();
        DownLoadingFragment fragment2= new DownLoadingFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragmentManager = getSupportFragmentManager();
        downViewpager.setAdapter(mAdapter);
        downTab.setupWithViewPager(downViewpager);
    }

    private PagerAdapter mAdapter = new PagerAdapter() {


        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            Fragment fragment = fragments.get(position);
            if (!fragment.isAdded()) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                fragmentManager.executePendingTransactions();
            }

            if (fragment.getView().getParent() == null) {

                ((ViewPager) container).addView(fragment.getView());
            }
            return fragment.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(fragments.get(position).getView());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };
}

