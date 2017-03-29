package com.example.administrator.starmusic.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.starmusic.app.MyAppInterface;
import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.util.Consts;
import com.example.administrator.starmusic.app.MusicPlayerApplication;

import java.io.IOException;
import java.util.List;

public class MusicService extends Service implements Consts ,MyAppInterface{
    /**
     * 广播接收者
     */
    private BroadcastReceiver receiver;
    /**
     * MediaPlayer
     */
    private MediaPlayer player;
    /**
     * 歌曲数据的List集合
     */
    private List<Music> musics;
    /**
     * Application
     */
    private MusicPlayerApplication app;
    /**
     * 当前播放的歌曲的索引
     */
    private int currentMusicIndex;
    /**
     * 暂停时的播放位置
     */
    private int  pausePosition ;
    /**
     * Intent：用于表示广播中发送的信息
     */
    private Intent broadcastIntent = new Intent();
    /**
     * 更新进度的线程
     */
    private UpdateProgressThread updateProgressThread;

    private Intent aimIntent;

    @Override
    public void onCreate() {

        aimIntent=new Intent();
        // 获取歌曲数
        app = (MusicPlayerApplication) getApplication();
        app.setAppInterface(this);
        // 初始化播放器
        player = new MediaPlayer();

        // 为MediaPlayer配置监听器
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });

        // 注册广播接收者
        receiver = new InnerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_PAUSE);

        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PLAY_POSITION);
        filter.addAction(ACTION_PLAY_FROM_PROGRESS);

        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 设置当前Service为非粘性的
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // 解除注册广播接收者
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        // 停止线程
        stopUpdateProgressThread();
        // 释放播放器资源
        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**
     * 开启更新进度(发送广播让Activity更新)的线程
     */
    private void startUpdateProgressThread() {
        if (updateProgressThread == null) {
            updateProgressThread = new UpdateProgressThread();
            updateProgressThread.setRunning(true);
            updateProgressThread.start();
        }
    }

    /**
     * 停止更新进度(发送广播让Activity更新)的线程
     */
    private void stopUpdateProgressThread() {
        if (updateProgressThread != null) {
            updateProgressThread.setRunning(false);
            updateProgressThread = null;
        }
    }

    @Override
    public void getListMusic(List<Music> musics) {
        this.musics=musics;
        Log.d("musicservice","music"+musics.size());
    }

    private class UpdateProgressThread extends Thread {
        private boolean isRunning;

        public void setRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        @Override
        public void run() {
            while (isRunning) {
                if (player.isPlaying()) {
                    // 发出广播：使Activity更新播放进度
                    broadcastIntent.setAction(ACTION_UPDATE_PROGRESS);
                    broadcastIntent.putExtra(EXTRA_CURRENT_POSITION, player.getCurrentPosition());
                    Log.d("muaicaerviceprogress","progress="+player.getCurrentPosition());
                    sendBroadcast(broadcastIntent);
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取广播的Action
            String action = intent.getAction();
            // 判断Action
            if (ACTION_PLAY.equals(action)) {
                    if(musics!=null){
                        if(musics.size()>0){
                            // -- 播放或暂停 --
                            // 判断播放状态

                                currentMusicIndex=intent.getIntExtra("position",0);
                                // 正在播放，则暂停
                                play(currentMusicIndex);

                        }else {
                            Toast.makeText(MusicService.this,"没有获取muisc",Toast.LENGTH_SHORT).show();
                        }

                    }

            } else if(ACTION_PAUSE.equals(action)){
                    pause();
            }else if (ACTION_PREVIOUS.equals(action)) {
                // 播放上一首
                previous();
            } else if (ACTION_NEXT.equals(action)) {
                // 播放下一首
                next();
            } else if (ACTION_PLAY_POSITION.equals(action)) {
                // 获取歌曲的position
                int position = intent.getIntExtra(EXTRA_MUSIC_INDEX, 0);
                // 播放指定的歌曲
                play(position);
            } else if(ACTION_PLAY_FROM_PROGRESS.equals(action)) {
                // 获取进度
                int progress = intent.getIntExtra(EXTRA_PROGRESS, 0);
                // 从指定的进度开始播放
                playFromProgress(progress);
            }
        }
    }
private String musicUrl(String id){
    return "http://1p6438u002.51mypc.cn/DreamMusic/musicDataServlet?id="+id;
}
    /**
     * 播放歌曲
     */
    private void play() {
//        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.reset();
            String path=musicUrl(musics.get(currentMusicIndex).getId());
            player.setDataSource(path);
            player.prepareAsync();
            player.setOnPreparedListener(new MyMusicPrepared(pausePosition));

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean mVideoSizeIsSet = false;
    boolean mMediaPlayerIsPrepared = false;

    class  MyMusicPrepared implements MediaPlayer.OnPreparedListener{
    private int pauseposition;
    public MyMusicPrepared( int pauseposition) {
        this.pauseposition=pauseposition;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(pauseposition>0){
//            Log.d("musicservice","pauseposition="+pauseposition);
//            Log.d("musicservice","duration="+mp.getDuration());
            player.seekTo(pausePosition);
        }
            player.start();
            Log.d("musicservice2","duration2="+mp.getDuration());
            aimIntent.setAction(STARTANIM);
            sendBroadcast(aimIntent);
            // 开启线程
            startUpdateProgressThread();



    }

}
    /**
     * 播放指定的歌曲
     *
     * @param position
     *            歌曲的索引位置
     */
    private void play(int position) {
        currentMusicIndex = position;
        pausePosition = 0;
        play();
    }
    private int getDuration(String duration){
        String[] chars=duration.split(":");
        int f=Integer.parseInt(chars[0])*60*1000;
        int m=Integer.parseInt(chars[1])*1000;
        int mm=Integer.parseInt(chars[2]);
        return f+m+mm;
    }
    /**
     * 从指定的进度开始播放歌曲
     * @param progress 进度值
     */
    private void playFromProgress(int progress) {
        // 设置播放的起点位置
        pausePosition =getDuration( musics.get(currentMusicIndex).getDuration()) * progress / 100;
        // 播放
//        player.seekTo(pausePosition);
//        player.start();
        play();
    }

    /**
     * 暂停
     */
    private void pause() {
        Log.d("TGA","总时长"+player.getDuration());
        player.pause();
        aimIntent.setAction(PAUSEANIM);
        sendBroadcast(aimIntent);
        pausePosition = player.getCurrentPosition();
//        // 停止线程
        stopUpdateProgressThread();
    }

    /**
     * 播放上一首
     */
    private void previous() {
        currentMusicIndex--;
        if (currentMusicIndex < 0) {
            currentMusicIndex = musics.size() - 1;
        }
        pausePosition = 0;
        broadcastIntent.setAction(TI_HUAN);
        broadcastIntent.putExtra("position",currentMusicIndex);
        sendBroadcast(broadcastIntent);
        play();
    }

    /**
     * 播放下一首
     */
    private void next() {
        currentMusicIndex++;
        if (currentMusicIndex >= musics.size()) {
            currentMusicIndex = 0;
        }
        pausePosition = 0;
        broadcastIntent.setAction(TI_HUAN);
        broadcastIntent.putExtra("position",currentMusicIndex);
        sendBroadcast(broadcastIntent);
        play();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
