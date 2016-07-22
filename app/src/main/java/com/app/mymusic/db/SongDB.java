package com.app.mymusic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.mymusic.model.Mp3Info;
import com.app.mymusic.utils.MediaUtil;
import com.app.mymusic.utils.PingYinUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SongDB {

    public static String TBL_NAME="playlistTbl";
    public static String CREATE_TBL="create table "+TBL_NAME+" ("
            + "sid text," + "id int," + "title text," + "album text,"
            + "albumId long," + "displayName text," + "artist text,"
            + "duration long," + "size long," + "sizeStr text," + "path text,"
            + "type int," + "albumUrl text," + "downUrl text,"
            + "downSize long," + "playProgress long," + "category text,"
            + "valid int," + "createTime text," + "childCategory text" + ")";
    private SQLiteDatabase db;
    private SQLDBHlper mDBHlper;
    private Context context;
    public SongDB(Context context) {
        this.context = context;
        mDBHlper= SQLDBHlper.getSQLDBHlper(context);
    }
    public void getOriginalDatas()
    {
        List<Mp3Info> Originallists=MediaUtil.getMp3Infos(context);
        mDBHlper.getWritableDatabase().execSQL("delete from " + SongDB.TBL_NAME);

        for(Mp3Info mp3:Originallists)
        {
            add(mp3);
        }
    }
    public void add(Mp3Info songInfo )
    {
        ContentValues values=new ContentValues();
        values.put("id", songInfo.getId());
        values.put("displayName", songInfo.getDisplayName());
        values.put("title", songInfo.getTitle());
        values.put("artist", songInfo.getArtist());
        values.put("duration", songInfo.getDuration());
        values.put("size", songInfo.getSize());
        values.put("path", songInfo.getPath());
        values.put("albumId", songInfo.getAlbumId());
        values.put("album", songInfo.getAlbum());
        String category=PingYinUtil.getPingYin(songInfo.getTitle()).toUpperCase();
        char cat=category.charAt(0);
        if (cat <= 'Z' && cat >= 'A') {
            values.put("category", cat + "");
            songInfo.setCategory(cat + "");
            values.put("childCategory", category);
            songInfo.setChildCategory(category);
        } else {
            values.put("category", "^");
            songInfo.setCategory("^");
            values.put("childCategory", category);
            songInfo.setChildCategory(category);
        }
        insert(values, songInfo);
    }

    private void insert(ContentValues values, Mp3Info songInfo) {

             db= mDBHlper.getWritableDatabase();
             db.insert(TBL_NAME,null,values);
    }

    public   List<Mp3Info> getListSortByCategory( )
    {
        List<Mp3Info> lists=new ArrayList<Mp3Info>();
        Cursor cursor=query();
        int i=0;
        while(cursor.moveToNext())
        {
            Mp3Info mp3= getMp3Info(cursor);
            mp3.setTitle(i+":"+mp3.getTitle());
            lists.add(mp3);
            i++;
        }
        return lists;
    }

    public Cursor query() {
        db = mDBHlper.getReadableDatabase();
        Cursor c = db.query(TBL_NAME, null, null, null, null, null,
                "category asc ");
        return c;
    }

    private Mp3Info getMp3Info(Cursor cursor) {

        Mp3Info info=new Mp3Info();
        info.setId((cursor.getInt(cursor.getColumnIndex("id"))));
        info.setDisplayName(cursor.getString(cursor.getColumnIndex("displayName")));
        info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        info.setArtist(cursor.getString(cursor.getColumnIndex("artist")));
        info.setDuration(cursor.getLong(cursor.getColumnIndex("duration")));
        info.setSize(cursor.getLong(cursor.getColumnIndex("size")));
        info.setPath(cursor.getString(cursor.getColumnIndex("path")));
        info.setAlbum(cursor.getString(cursor.getColumnIndex("album")));
        info.setAlbumId(cursor.getLong(cursor.getColumnIndex("albumId")));
        info.setCategory(cursor.getString(cursor.getColumnIndex("category")));
        info.setChildCategory(cursor.getString(cursor.getColumnIndex("childCategory")));
        return info;
    }
    //    根据字母索引获取，当前位置
    public  Map<String,Integer> getMapsByCategory()
    {
        Map<String,Integer> mp=new HashMap<String, Integer>();
        if(db==null)
        {
            db = mDBHlper.getReadableDatabase();
        }
        String[] colums=new String[]{"category","COUNT(category)"};
        String selection=null;
        String[] selectionArgs = null;
        String groupBy = "category";
        String having = "";
        String orderBy = "category asc";
        Cursor c = db.query(TBL_NAME, colums, selection, selectionArgs, groupBy, having, orderBy);
        int i=0;
        StringBuffer buffer=new StringBuffer();
        String[] sections = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "#" };
        while(c.moveToNext())
        {
            String category= c.getString(c.getColumnIndex("category"));
            buffer.append(category);
            String counts= c.getString(c.getColumnIndex("COUNT(category)"));
            Integer count= Integer.valueOf(counts);
            mp.put(category,i);
            i+=count;
        }

        for(int j=0;j<sections.length;j++)
        {
           if(!(buffer.toString().contains(sections[j])))
           {
               int p=(j-1)<0?0:j-1;
             try{
//                 get报空指针
                 int i1= mp.get(sections[p]);
                 mp.put(sections[j],i1);
             }catch (Exception e)
             {
                 mp.put(sections[j],0);
             }
           }
        }
        return mp;
    }
}
