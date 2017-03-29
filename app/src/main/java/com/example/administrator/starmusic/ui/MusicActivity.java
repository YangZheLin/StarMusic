package com.example.administrator.starmusic.ui;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.app.MusicPlayerApplication;
import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.service.MusicService;
import com.example.administrator.starmusic.util.CommonUtils;
import com.example.administrator.starmusic.util.Consts;
import com.example.administrator.starmusic.util.RotatoAnimation;
import com.example.administrator.starmusic.view.CircleImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener,Consts,SeekBar.OnSeekBarChangeListener{
    private RotatoAnimation rotatoAnimation;
    private float currentValue;
    private ObjectAnimator objAnim;
//    private  MyAnimatorUpdateListener updateListener;
//    private CircleImageView civPic;
    private SimpleDraweeView civPic;
    private CheckBox cbStart;
    private BroadcastReceiver receiver;
    private MusicPlayerApplication app;
    /**
     * TextView：当前播放的歌曲的标题
     */
    private TextView tvMusicTitle;
    /**
     * TextView：当前播放的歌曲的时长
     */
    private TextView tvMusicDuration;
    /**
     * 演唱者
     */
    private TextView tvSinger;
    /**
     * SeekBar：当前播放进度
     */
    private SeekBar sbMusicProgress;
    /**
     * 判断当前是否在推拽
     */
    private boolean isTrackingTouch;
    /**
     * TextView：当前播放到的时间
     */
    private TextView tvMusicCurrentPosition;
    private Intent windowIntentstart;
    private Intent windowIntentpause;
    private Intent muiscServiceIntent;

    private ImageButton ibBack;
    private ImageButton ibPrevious;
    private ImageButton ibNext;

    private Intent intent;
    private Boolean isRunning;
    private List<Music> listmusc;
    private Music music;
    private int position;
    private String duration;
    private int muiscDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       intent= getIntent();
       isRunning= intent.getBooleanExtra("isRunning",false);
//        music= (Music) intent.getSerializableExtra("music");
        position=intent.getIntExtra("position",0);
//        listmusc= (List<Music>) intent.getSerializableExtra(ARRAYLIST);
//        Log.d("position","position="+position);
        app= (MusicPlayerApplication) getApplication();
        listmusc=app.getListMusic();

//        Intent intent1=new Intent(this, MusicService.class);
//        intent1.putExtra(ARRAYLIST, (Serializable)  listmusc);
//        startService(intent1);
        initView();
        setListener();
        if(isRunning){
            rotatoAnimation.startAnimation();
        }
        cbStart.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setListener() {
        civPic.setOnClickListener(this);
        cbStart.setOnCheckedChangeListener(this);

        ibPrevious.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        ibNext.setOnClickListener(this);

        sbMusicProgress.setOnSeekBarChangeListener(this);
    }
    private int getDuration(String duration){
        String[] chars=duration.split(":");
        int f=Integer.parseInt(chars[0])*60*1000;
        int m=Integer.parseInt(chars[1])*1000;
        int mm=Integer.parseInt(chars[2]);
        return f+m+mm;
    }
    private void initView() {
        tvMusicTitle= (TextView) findViewById(R.id.tv_musicName);
        tvSinger = (TextView) findViewById(R.id.tv_singer);
        tvMusicDuration= (TextView) findViewById(R.id.tv_music_duration);
        sbMusicProgress= (SeekBar) findViewById(R.id.sb_music_progress);
        tvMusicCurrentPosition= (TextView) findViewById(R.id.tv_music_current);

        cbStart= (CheckBox) findViewById(R.id.ib_start);

//        civPic= (CircleImageView) findViewById(R.id.civ_pic);
        civPic= (SimpleDraweeView) findViewById(R.id.civ_pic);

        ibBack= (ImageButton) findViewById(R.id.ib_back);
        ibPrevious= (ImageButton) findViewById(R.id.ib_previous);
        ibNext= (ImageButton) findViewById(R.id.ib_next);

        rotatoAnimation= new RotatoAnimation(civPic);

        windowIntentstart=new Intent();

        windowIntentpause=new Intent();

        muiscServiceIntent=new Intent();

        regBroadcastReceiver();
        if(listmusc!=null){
            if(listmusc.size()>0){
                editText(position);
            }
        }


    }
    private String musicPath="http://1p6438u002.51mypc.cn/DreamMusic/pic.do?id=";
    private Uri uri;
    private String parurl(String path){

        return musicPath+path;
    }
private void editText(int position){

     music= listmusc.get(position);

    if(music!=null){
         uri = Uri.parse(parurl(music.getId()));
        civPic.setImageURI(uri);
        tvMusicTitle.setText(music.getMusicName());
        String duration=music.getDuration();
        tvMusicDuration.setText(duration.substring(0,duration.lastIndexOf(":")));
        tvSinger.setText(music.getSinger());
    }
}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.civ_pic:

                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_previous:
                muiscServiceIntent.setAction(ACTION_PREVIOUS);
                sendBroadcast(muiscServiceIntent);
                break;
            case R.id.ib_next:
                muiscServiceIntent.setAction(ACTION_NEXT);
                sendBroadcast(muiscServiceIntent);
                break;
        }
    }
    /**
     * 注册广播接收者
     */
    private void regBroadcastReceiver() {
        receiver = new InnerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SET_AS_PAUSE_STATE);
        filter.addAction(ACTION_SET_AS_PLAY_STATE);
        filter.addAction(ACTION_UPDATE_PROGRESS);
        filter.addAction(TI_HUAN);
        filter.addAction(PAUSEANIM);
        filter.addAction(STARTANIM);
        registerReceiver(receiver, filter);
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.ib_start:
                if(isChecked==true){
                    muiscServiceIntent.setAction(ACTION_PLAY);
                    muiscServiceIntent.putExtra("position",position);
                    sendBroadcast(muiscServiceIntent);
                }else{
                    muiscServiceIntent.setAction(ACTION_PAUSE);
                    sendBroadcast(muiscServiceIntent);
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
//        rotatoAnimation.stopAnimation();
        unregisterReceiver(receiver);
//        stopAnimation();
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser==true){
            isTrackingTouch=true;
        }else {
            isTrackingTouch=false;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
           int sbprogress= seekBar.getProgress();
        Log.d("sbprogress","progresss="+sbprogress);
        muiscServiceIntent.setAction(ACTION_PLAY_FROM_PROGRESS);
        muiscServiceIntent.putExtra(EXTRA_PROGRESS,sbprogress);
//        sendBroadcast(muiscServiceIntent);
    }

    private class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取广播中的Action
            String action = intent.getAction();
            // 判断Action
            if (ACTION_SET_AS_PAUSE_STATE.equals(action)) {
                // -- 操作：设置为暂停状态 --
                // 将按钮设置为“播放”
//                ibPlayOrPause.setImageResource(android.R.drawable.ic_media_play);
            } else if (ACTION_SET_AS_PLAY_STATE.equals(action)) {
                // -- 操作：设置为播放状态 --
                // 将按钮设置为“暂停”
//                ibPlayOrPause.setImageResource(android.R.drawable.ic_media_pause);
                // 获取当前正在播放的歌曲的索引
//                muiscDuration = intent.getIntExtra(EXTRA_MUSIC_INDEX, 1);
                // 设置显示歌曲的标题
//                tvMusicTitle.setText("正在播放：" + musics.get(currentMusicIndex).getName());
                // 设置显示歌曲的时长
//                tvMusicDuration.setText(CommonUtils.getFormattedTime(musics.get(currentMusicIndex).getDuration()));
            } else if (ACTION_UPDATE_PROGRESS.equals(action)) {
                // -- 更新进度 --
                // 获取当前播放到的位置
                int currentPosition = intent.getIntExtra(EXTRA_CURRENT_POSITION, 0);
                Log.d("progress","progress="+currentPosition);
                // 需要更新到的进度值
                muiscDuration=getDuration(music.getDuration());
                Log.d("progress","progress="+muiscDuration);
                int progress = currentPosition * 100 /muiscDuration;
               //  判断用户是否正在拖进度条，如果在拖，则不更新SeekBar
                Log.d("progress","progress="+progress);
                if (!isTrackingTouch) {
                    // 更新SeekBar：当前播放到的进度
                    sbMusicProgress.setProgress(progress);
                }
                // 更新TextView：当前播放到的时间
                tvMusicCurrentPosition.setText(CommonUtils.getFormattedTime(currentPosition));
            }else if(TI_HUAN.equals(action)){
                 position=intent.getIntExtra("position",0);
                editText(position);
            }else if(PAUSEANIM.equals(action)){
                if(rotatoAnimation.isRunning()) {
                    rotatoAnimation.pauseAnimation();
                    cbStart.setChecked(false);
                }
            }else if(STARTANIM.equals(action)){
                if(!rotatoAnimation.isRunning()){
                    rotatoAnimation.startAnimation();
                    cbStart.setChecked(true);
                }
            }
        }
    }

//    private  class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
//
//        @Override
//        public void onAnimationUpdate(ValueAnimator animation) {
//            currentValue= (float) animation.getAnimatedValue();
//        }
//
//    }
}
