package com.example.administrator.starmusic.presenter;

import android.content.Context;

import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.fragment.MuiscfragmentInterface;
import com.example.administrator.starmusic.fragment.Musicfragment;
import com.example.administrator.starmusic.model.MusicModel;
import com.example.administrator.starmusic.model.MusicModelInterface;

import java.util.List;

/**
 * Created by adminwk on 2017/1/25.
 */

public class MusicPresenter implements MusicPresenterInterface {
    private MusicModelInterface musicModelInterface;

    public  String url;
    public MusicPresenter(final Context context, final MuiscfragmentInterface muiscfragmentInterface ,String url) {
        this.url=url;
        musicModelInterface=new MusicModel(new MusicModel.MyhuiDiao() {
            @Override
            public void setList(List<Music> listmusic) {
                muiscfragmentInterface.setMusicAdapter(listmusic);
            }
        });

    }

    @Override
    public void makeListToUi() {
        musicModelInterface.getMusiclistByThread(url);
    }

}
