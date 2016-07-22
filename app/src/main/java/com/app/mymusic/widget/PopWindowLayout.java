package com.app.mymusic.widget;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.mymusic.R;
import com.app.mymusic.adapter.PopAdapter;
import com.app.mymusic.common.Constants;
import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.utils.MediaPlayerUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/21.
 */
public class PopWindowLayout {
    @BindView(R.id.pop_mode_btn)
    ImageView popModeBtn;
    @BindView(R.id.pop_num_tv)
    TextView popNumTv;
    @BindView(R.id.pop_clear_btn)
    ImageButton popClearBtn;
    @BindView(R.id.pop_title_layout)
    RelativeLayout popTitleLayout;
    @BindView(R.id.pop_close_tv)
    Button popCloseTv;
    @BindView(R.id.pop_list)
    RecyclerView popList;

    private PopupWindow pw;
    private View view;
    private List<Mp3Info> datas;
    private  PopAdapter mAdapter;

    public PopWindowLayout(List<Mp3Info> datas) {
        this.datas=datas;
    }
    private int[] mode_imgs=new int[]{R.drawable.ic_player_mode_all_default,R.drawable.ic_player_mode_random_default
    ,R.drawable.ic_player_mode_single_default};
    private int mode_id=0;

    public PopupWindow getPopWindow(final Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.popup_playlist, null);
        pw = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pop_background));
//        pw.setFocusable(true); //设置PopupWindow可获得焦点
//        pw.setTouchable(true); //设置PopupWindow可触摸
        ButterKnife.bind(this, view);
        popNumTv.setText("播放队列("+datas.size()+"首)");
        mAdapter=new PopAdapter(R.layout.item_poplist,datas);
        popList.setLayoutManager(new LinearLayoutManager(context));
        popList.setAdapter(mAdapter);
        popModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                MediaPlayerUtil util=new MediaPlayerUtil(context,datas,i);
                util.play();
                pw.dismiss();
            }
        });
        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId())
                {
                    case R.id.delete_btn:
                        datas.remove(i);
                        mAdapter.notifyDataSetChanged();
                }
            }
        });
        return pw;
    }


    @OnClick({R.id.pop_mode_btn, R.id.pop_clear_btn, R.id.pop_close_tv, R.id.pop_num_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_mode_btn:
                switch (Constants.PLAY_MODE)
                {
                    case 0:
                        Constants.PLAY_MODE=1;
                        popModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
                        Snackbar.make(view, "随机播放", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Constants.PLAY_MODE=2;
                        popModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
                        Snackbar.make(view, "单曲循环", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Constants.PLAY_MODE=0;
                        popModeBtn.setImageResource(mode_imgs[Constants.PLAY_MODE]);
                        Snackbar.make(view, "顺序播放", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.pop_clear_btn:
//                datas.clear();
//                mAdapter.notifyDataSetChanged();
                Snackbar.make(view, "清空播放列表", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.pop_close_tv:
                pw.dismiss();
                break;
        }
    }
}
