package com.example.administrator.starmusic.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.adapter.MusicFragmentAdapter;
import com.example.administrator.starmusic.app.AppMusicInterface;
import com.example.administrator.starmusic.app.MusicPlayerApplication;
import com.example.administrator.starmusic.app.MyAppInterface;
import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.presenter.MusicPresenter;
import com.example.administrator.starmusic.presenter.MusicPresenterInterface;
import com.example.administrator.starmusic.ui.MusicActivity;
import com.example.administrator.starmusic.util.Consts;
import com.example.administrator.starmusic.util.ImageUtils;
import com.example.administrator.starmusic.view.MyViewPager2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class Musicfragment extends Fragment implements ViewPager.OnPageChangeListener,MuiscfragmentInterface,AdapterView.OnItemClickListener,Consts,AppMusicInterface {
    private Context mcontext;
    private  List<Music> listMusic;
    private static  final  int LIST=1;
    public static final String URL="http://1p6438u002.51mypc.cn/DreamMusic/getDataServlet?length=0";
    public Musicfragment(Context context){
        mcontext=context;
    }
    public Musicfragment(){

    }
    private LinearLayout ll_points;
    private List<ImageView> listImage;
    private MyViewPager2 mViewPager;

    private ListView lv_today;
    private ListView lv_yesterday;

    private MusicPresenterInterface mpi;
    private MusicFragmentAdapter adapter;
    private View view;
    MusicPlayerApplication app;
    private boolean isHave;
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST:
                adapter = new MusicFragmentAdapter(listMusic, mcontext);
                lv_today.setAdapter(adapter);
                Log.d("zhixing", "执行了没" + listMusic.size());
                super.handleMessage(msg);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.music_fragment,null);

        ll_points= (LinearLayout) view.findViewById(R.id.ll_points);
        mViewPager= (MyViewPager2) view.findViewById(R.id.vp_image);
        lv_today= (ListView) view.findViewById(R.id.lv_today1);

        listImage=new ArrayList<>();
        ImageUtils.addPointView(ll_points ,mcontext,listImage);
        mViewPager.setAdapter(new Mypageadapter());
        mViewPager.addOnPageChangeListener(this);

        lv_today.setOnItemClickListener(this);
        app= (MusicPlayerApplication) mcontext.getApplicationContext();
        app.setAppMusicInterface(this);

        listMusic=app.getListMusic();
        if(listMusic!=null&&listMusic.size()>0){
            if(adapter==null){
                adapter = new MusicFragmentAdapter(listMusic, mcontext);
            }
            lv_today.setAdapter(adapter);
            isHave=true;
        }
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<ll_points.getChildCount();i++){
            ll_points.getChildAt(i).setEnabled(i==position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setMusicAdapter(List<Music> musics) {
        if(musics.size()>0){
          listMusic=musics;
            mhandler.sendEmptyMessage(LIST);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent play=new Intent(mcontext, MusicActivity.class);
//        Intent intenMusic=new Intent();
//        intenMusic.setAction(ACTION_PLAY_POSITION);
//        play.putExtra(ARRAYLIST, (Serializable) listMusic);
        play.putExtra("position",position);
//        mcontext.sendBroadcast(intenMusic);
        mcontext.startActivity(play);
    }

    @Override
    public void setlvAdapter(List<Music> musics) {
        if(musics!=null&&musics.size()>0){
            if(!isHave){
                adapter=new MusicFragmentAdapter(musics,mcontext);
                lv_today.setAdapter(adapter);

            }

        }
    }


    public class Mypageadapter extends PagerAdapter {
        @Override
        public int getCount() {
//			System.out.println(listImage.size()+"-------22323");
            return listImage.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv1=listImage.get(position);
            iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(iv1);
            return iv1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }
}
