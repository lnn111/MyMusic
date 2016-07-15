package com.app.mymusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SlideBar extends View {

    public static String[] letters = { "#", "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" };
    private  int choose=-1;
    private Paint mPaint=new Paint();
    private boolean showbg=false;
    private  int singleHight=0;
    private OnTouchLetterListenner listenner;

    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getWidth();
        int hight=getHeight();

         singleHight=hight/letters.length;
        if(showbg)
        {
            // 画出背景
            canvas.drawColor(Color.parseColor("#55000000"));
        }

        for(int i=0;i<letters.length;i++)
        {
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(18);
            mPaint.setColor(Color.BLACK);
            if(i==choose)
            {
                mPaint.setColor(Color.YELLOW);
                mPaint.setFakeBoldText(true);
            }
            float x=width/2-mPaint.measureText(letters[i])/2;
            float y=singleHight*i+singleHight;
            canvas.drawText(letters[i],x,y,mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y=event.getY();
        int index=(int)y/singleHight+1;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                showbg=true;
                if(listenner!=null&&index>0&&index<letters.length)
                {
                    listenner.OnTouchLetterChange(event,letters[index]);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(listenner!=null&&index>0&&index<letters.length)
                {
                    listenner.OnTouchLetterChange(event,letters[index]);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(listenner!=null&&index>0&&index<letters.length)
                {
                    listenner.OnTouchLetterChange(event,letters[index]);
                    invalidate();
                }
                break;
            default:
                showbg=false;
                choose=-1;
                if(listenner!=null&&index>0&&index<letters.length)
                {
                    listenner.OnTouchLetterChange(event,letters[index]);
                    invalidate();
                }
                break;
        }
        return true;
    }

    public void setOnTouchLetterListenner(OnTouchLetterListenner mListenner)
    {
        this.listenner=mListenner;
    }

    public interface OnTouchLetterListenner
    {
        void OnTouchLetterChange(MotionEvent event,String str);
    }
}
