package com.app.mymusic.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.model.NetMusic;
import com.app.mymusic.utils.LogUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SingerInfoActivity extends AppCompatActivity {

    @BindView(R.id.singer_image)
    ImageView singerImage;
    @BindView(R.id.singer_name_tv)
    TextView singerNameTv;

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_info);
        ButterKnife.bind(this);
        keyword=getIntent().getStringExtra("artist");
        loadSingerInfo();

    }

    public interface SingerInfoInter
    {
        @Headers("apikey:13e982978120a8e0218bd279e0632036")
        @GET("singer")
        Observable<NetMusic> getSingerInfo(@Query("name") String name);
    }

    private void loadSingerInfo() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://apis.baidu.com/geekery/music/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SingerInfoInter inter=retrofit.create(SingerInfoInter.class);
        inter.getSingerInfo(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetMusic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        LogUtil.showLog(e.toString());
                    }

                    @Override
                    public void onNext(NetMusic netMusic) {
                        String name=netMusic.getData().getSingername();
                        String url= netMusic.getData().getImage();
                        singerNameTv.setText(name);
                        Picasso.with(SingerInfoActivity.this).load(url).into(singerImage);
                    }
                });
    }

    @OnClick(R.id.singer_image)
    public void onClick() {
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        singerImage.setLayoutParams(params);
    }
}
