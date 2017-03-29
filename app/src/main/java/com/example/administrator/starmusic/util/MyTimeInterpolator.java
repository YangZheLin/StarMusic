package com.example.administrator.starmusic.util;

import android.animation.TimeInterpolator;

/**
 * Created by Administrator on 2016/11/13.
 */

public class MyTimeInterpolator implements TimeInterpolator {
    public float fraction;
    private volatile static MyTimeInterpolator instance;
    private MyTimeInterpolator(){
    }
    public static MyTimeInterpolator getInstance() {
        if(instance==null){
            synchronized (MyTimeInterpolator.class) {
                        if (instance == null) {
                                instance = new MyTimeInterpolator();
                            }
                         }
        }
         return instance;
    }
    @Override
    public float getInterpolation(float input) {
        return fraction;
    }
}
