package com.videolib.video.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by yue on 2016/11/18.
 */

public class Video implements Serializable {
    private int id;
    private String title;
    private String album;
    private String artist;
    private String displayName;
    private String mimeType;
    private String path;
    private long size;
    private long duration;
    private Bitmap thumbnail;

    private boolean isSelect;

    public Video(int id, String title, String album, String artist,
                 String displayName, String mimeType, String path, long size,
                 long duration) {
        super();
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.duration = duration;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
