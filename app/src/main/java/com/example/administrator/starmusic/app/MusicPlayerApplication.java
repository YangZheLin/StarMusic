package com.example.administrator.starmusic.app;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.model.MusicModel;
import com.example.administrator.starmusic.model.MusicModelInterface;
import com.example.administrator.starmusic.presenter.MusicPresenter;
import com.example.administrator.starmusic.service.DemoIntentService;
import com.example.administrator.starmusic.service.DemoPushService;
import com.example.administrator.starmusic.service.FloatingWindowService;
import com.example.administrator.starmusic.service.MusicService;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.igexin.sdk.PushManager;
import com.tencent.bugly.Bugly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MusicPlayerApplication extends Application{

    public  List<Music> listMusic=new ArrayList<>();
    private MyAppInterface appInterface;
    private  AppMusicInterface appMusicInterface;

    public void setAppMusicInterface(AppMusicInterface appMusicInterface) {
        this.appMusicInterface = appMusicInterface;
    }

    public void setAppInterface(MyAppInterface appInterface) {
        this.appInterface = appInterface;
    }

    private MusicModelInterface musicModelInterface;

    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Intent intent1=new Intent(this, MusicService.class);
        startService(intent1);

    }

    public void setListMusic(List<Music> listMusic) {
       this.listMusic.addAll((Collection<? extends Music>) listMusic);
        if(appInterface!=null){
            appInterface.getListMusic(listMusic);
        }
        if(appMusicInterface!=null){
            appMusicInterface.setlvAdapter(listMusic);
        }
    }

    public List<Music> getListMusic() {
        return listMusic;
    }
}
