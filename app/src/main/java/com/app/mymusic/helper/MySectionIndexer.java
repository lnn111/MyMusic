package com.app.mymusic.helper;

import android.widget.SectionIndexer;

import com.app.mymusic.model.Mp3Info;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MySectionIndexer implements SectionIndexer {
    private String[] letters;
    private int mCount;
    private  int[] positions;
    private List<Mp3Info>  lists;

    public MySectionIndexer(String[] letters,int[] counts,List<Mp3Info>  lists) {
        if(letters==null||counts==null)
        {
            throw new NullPointerException();
        }
        this.lists=lists;
        this.letters=letters;
        positions=new int[counts.length];
        int position=0;
        for(int i=0;i<counts.length;i++)
        {
            position=+counts[i];
            positions[i]=position;
        }

        mCount=position;
    }

    @Override
    public Object[] getSections() {
        return letters;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {

        for(int i=0;i<lists.size();i++ )
        {
            char c=lists.get(i).getTitle().toCharArray()[0];
            if(c==sectionIndex)
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < 0 || position >= mCount) {
            return -1;
        }
        //注意这个方法的返回值，它就是index<0时，返回-index-2的原因
        //解释Arrays.binarySearch，如果搜索结果在数组中，刚返回它在数组中的索引，如果不在，刚返回第一个比它大的索引的负数-1
        //如果没弄明白，请自己想查看api
        int index = Arrays.binarySearch(positions, position);
        return index >= 0 ? index : -index - 2; //当index小于0时，返回-index-2，
    }
}
