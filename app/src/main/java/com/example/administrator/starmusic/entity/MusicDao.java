package com.example.administrator.starmusic.entity;

import java.util.List;

/**
 * Created by adminwk on 2017/1/25.
 */

public class MusicDao {
    private List<Music> musics;

    public List<Music> getListMusic() {
        return musics;
    }

    public void setListMusic(List<Music> listMusic) {
        this.musics = listMusic;
    }
}
