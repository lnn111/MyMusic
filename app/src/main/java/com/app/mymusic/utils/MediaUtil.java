package com.app.mymusic.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.app.mymusic.model.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MediaUtil {
//专辑封面uri
    private  static Uri albumArtUri=Uri.parse("content://media/external/audio/albumart");

    public static Cursor getMediaCursor(Context context)
    {

        Cursor cursor=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null
                ,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        return cursor;
    }

    public static List<Mp3Info> getMp3Infos(Context context)
    {
        Cursor cursor=getMediaCursor(context);

        List<Mp3Info> lists=new ArrayList<Mp3Info>();
        for(int i=0;i<cursor.getCount();i++)
            {
                Mp3Info mp3Info=getMp3InfoByCursor(cursor);
                if(mp3Info!=null)
                {
                    lists.add(mp3Info);
                }
    }
        cursor.close();
        return lists;
    }

    public  static Mp3Info getMp3InfoByCursor(Cursor cursor)
    {
        String artist = "";
        String title = "";
        Mp3Info mp3Info =new Mp3Info();
        cursor.moveToNext();
        long id=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        String tmpTitle=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        String displayName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
        if(displayName.contains(".mp3"))
        {
            String fileName=displayName.split("\\.")[0];
            if(fileName.contains("-"))
            {
                String[] strs=fileName.split("-");
                artist=strs[0];
                title=strs[1];
            }
            else
            {
                title=fileName;
            }
        }
        long duration = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.DURATION));
        long size = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.SIZE));
        String album = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.ALBUM)); // 专辑
        String url = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.DATA));
        int isMusic = cursor.getInt(cursor
                .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
        long albumid = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        if(isMusic!=0)
        {
            if(displayName.contains(".mp3"))
            {
                mp3Info.setId(id);
                mp3Info.setTitle(title);
                mp3Info.setArtist(artist);
                mp3Info.setDuration(duration);
                mp3Info.setDisplayName(displayName);
                mp3Info.setSize(size);
                mp3Info.setPath(url);
                mp3Info.setAlbumId(albumid);
                mp3Info.setAlbum(album);
                return  mp3Info;
            }
            return null;
        }
        return null;
    }
}
