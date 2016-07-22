package com.app.mymusic.adapter;

import com.app.mymusic.R;
import com.app.mymusic.model.Mp3Info;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class PopAdapter extends BaseQuickAdapter<Mp3Info> {

    public PopAdapter(int layoutResId, List<Mp3Info> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Mp3Info mp3Info) {
        baseViewHolder.setText(R.id.name_tv,mp3Info.getTitle())
                      .setOnClickListener(R.id.delete_btn,new OnItemChildClickListener());
    }
}
