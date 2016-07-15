package com.app.mymusic.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.app.mymusic.R;

/**
 * Created by Administrator on 2016/7/15.
 */
public class DialogUtil {

    public  static Dialog showDialog(Context context, String str) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View mView = mInflater.inflate(R.layout.progress, null, false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.progress);
        mView.setAnimation(animation);
        Dialog mDialog = new Dialog(context, R.style.Dialog_Progress);
        mDialog.setContentView(mView);
        return mDialog;
    }
}
