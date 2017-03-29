package com.example.administrator.starmusic.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.administrator.starmusic.ui.MusicActivity;

/**
 * Created by Administrator on 2016/11/14.
 */

public class RotatoAnimation {
    private boolean isRunning;
    private MyAnimatorUpdateListener updateListener;
    private LinearInterpolator linearInterpolator;
    private ObjectAnimator objAnim;
    private View civPic;
    private float currentValue;

    public boolean isRunning() {
        return isRunning;
    }

    public RotatoAnimation (View civPic){
        this.civPic=civPic;
        linearInterpolator=new LinearInterpolator();
        updateListener=new MyAnimatorUpdateListener();
    }

    public void startAnimation(){
        objAnim = ObjectAnimator.ofFloat(civPic, "Rotation", currentValue-360,currentValue);
        objAnim.setRepeatCount(ValueAnimator.INFINITE);
        objAnim.setDuration(10000);
        objAnim.setInterpolator(linearInterpolator);
        //为了增加
        objAnim.addUpdateListener(updateListener);
        objAnim.start();
        isRunning=true;
    }
    public void stopAnimation(){
        //如果点击停止，那么我们还需要将暂停的动画重新设置一下
        if(objAnim!=null){
            objAnim.end();
            civPic.clearAnimation();
            currentValue=0;
//            objAnim=null;
            isRunning=false;
        }

    }
    public void pauseAnimation(){
        objAnim.cancel();
        civPic.clearAnimation();
        isRunning=false;

    }
    private  class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            currentValue= (float) animation.getAnimatedValue();
        }

    }
}
