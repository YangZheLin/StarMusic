package com.example.administrator.starmusic.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.view.MyViewPager2;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

@SuppressLint("ValidFragment")
public class headmusicfragment extends Fragment{
    private Context mcontext;
    public headmusicfragment(Context context){
        mcontext=context;
    }
    private LinearLayout ll_points;
    private List<ImageView> listImage;
    private MyViewPager2 mViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.head_music_fragment,null);
       return  view;
    }


}
