package com.example.administrator.starmusic.entity;

/**
 * Created by adminwk on 2017/1/28.
 */

public class MusicPeople {
    private String peopleName;
    private String musicName;

    public String getPeopleName() {
        return peopleName;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public MusicPeople(String peopleName, String musicName) {
        this.peopleName = peopleName;
        this.musicName = musicName;
    }
}
