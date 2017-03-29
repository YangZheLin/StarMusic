package com.example.administrator.starmusic.model;

import com.example.administrator.starmusic.entity.Music;

import java.io.InputStream;
import java.util.List;

/**
 * Created by adminwk on 2017/1/24.
 */

public interface MusicModelInterface {
    InputStream getPicture(String url);
    List<Music> getMusics(String url);
    void  setMyHuiDiao(MusicModel.MyhuiDiao myHuiDiao);
    void getMusiclistByThread(String url);
}
