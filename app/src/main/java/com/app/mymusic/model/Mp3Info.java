package com.app.mymusic.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Administrator on 2016/7/13.
 */
public class Mp3Info {

    @DatabaseField(generatedId=true)
    private long id; // 歌曲ID 3
    @DatabaseField(columnName="title")
    private String title; // 歌曲名称 0
    @DatabaseField(columnName="album")
    private String album; // 专辑 7
    @DatabaseField(columnName="albumId")
    private long albumId;// 专辑ID 6
    @DatabaseField(columnName="displayName")
    private String displayName; // 显示名称 4
    @DatabaseField(columnName="artist")
    private String artist; // 歌手名称 2
    @DatabaseField(columnName="duration")
    private long duration; // 歌曲时长 1
    @DatabaseField(columnName="size")
    private long size; // 歌曲大小 8
    @DatabaseField(columnName="downsize")
    private  long downsize;
    @DatabaseField(columnName="path")
    private String path; // 歌曲路径 5
    @DatabaseField(columnName="category")
    private String category;// 分类
    @DatabaseField(columnName="childCategory")
    private String childCategory;// 子分类
    @DatabaseField(columnName="httpUrl")
    private String httpUrl;
    @DatabaseField(columnName="isDone")
    private boolean isDone;
    @DatabaseField(unique = true)
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getDownsize() {
        return downsize;
    }

    public void setDownsize(long downsize) {
        this.downsize = downsize;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(String childCategory) {
        this.childCategory = childCategory;
    }

    public Mp3Info( ) {
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
