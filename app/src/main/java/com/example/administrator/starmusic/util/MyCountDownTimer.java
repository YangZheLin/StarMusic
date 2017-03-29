package com.example.administrator.starmusic.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.CountDownTimer;

/**
 * Created by Administrator on 2016/11/13.
 */

public class MyCountDownTimer extends CountDownTimer {
    private volatile static MyCountDownTimer instance;
    public ObjectAnimator objAnim;
    public long mCurrentPlayTime;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    private  MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public static MyCountDownTimer getInstance() {
        if(instance==null){
            synchronized (MyTimeInterpolator.class) {
                if (instance == null) {
                    instance = new MyCountDownTimer(ValueAnimator.getFrameDelay(), ValueAnimator.getFrameDelay());
                }
            }
        }
        return instance;
    }
    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        if(objAnim!=null){
            objAnim.setCurrentPlayTime(mCurrentPlayTime);
        }

    }
}
