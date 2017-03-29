package com.example.administrator.starmusic.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.service.FloatingWindowService;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private ViewPager vpSplash;
    private LinearLayout llPoints;
    private List<ImageView> listImage;
    private MySplashViewPagerAdapter adapter;
    private int splashpositon;
//    private Handler mhandler=new Handler();
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 100);
            } else {
                startWindowService();
            }
        }else {
            startWindowService();
        }
        initView();
        setSplashadapter();
        setListeren();

    }
    private void startWindowService(){
        Intent intent=new Intent(this, FloatingWindowService.class);
        startService(intent);
//        mhandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                splashpositon++;
//                if(splashpositon<listImage.size()){
//                    vpSplash.setCurrentItem( splashpositon);
//                    mhandler.postDelayed(this,2000);
//                }else {
//                    startActivity(mIntent);
//                    finish();
//                }
//            }
//        },2000);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100){
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 100);
                } else {
                    startWindowService();
                }
//            this.startWindowService();

//            Log.d("运行了吗","sdsdasdsadsadsa");
//            initView();
//            setadapter();
//            setlistener();
            }
        }
    }
    private void setListeren() {
        vpSplash.addOnPageChangeListener(this);
    }

    private void setSplashadapter() {
        vpSplash.setAdapter(adapter);
    }

    public   void addPointView(LinearLayout ll_points, List listImage){
        int a[]={R.mipmap.first,R.mipmap.second,R.mipmap.third,R.mipmap.four};

        ImageView imageALL;
        View v;
        LinearLayout.LayoutParams params;
        for(int i=0;i<a.length;i++){
            imageALL=new ImageView(this);
            imageALL.setImageResource(a[i]);
            imageALL.setScaleType(ImageView.ScaleType.FIT_XY);
            if(i==a.length-1){
                imageALL.setOnClickListener(this);
            }
            listImage.add(imageALL);
            v=new View(this);
            v.setBackgroundResource(R.drawable.selsect_point);
            params = new LinearLayout.LayoutParams(20, 20);
            if(i!=0){
                params.leftMargin=10;
            }
            ll_points.addView(v, params);
            if(i!=0){
                ll_points.getChildAt(i).setEnabled(false);
            }else{
                ll_points.getChildAt(i).setEnabled(true);
            }
        }
    }
    private void initView() {
        vpSplash= (ViewPager) findViewById(R.id.vp_splash);
        llPoints= (LinearLayout) findViewById(R.id.ll_splash_points);
        listImage=new ArrayList<>();
        addPointView(llPoints,listImage);
        adapter=new MySplashViewPagerAdapter();
        mIntent=new Intent(this,MainActivity.class);
        count=llPoints.getChildCount();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    private int count;
    @Override
    public void onPageSelected(int position) {
        splashpositon=position;
        for(int i=0;i<count;i++){
            llPoints.getChildAt(i).setEnabled(i==position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        startActivity(mIntent);
        finish();
    }
    ImageView iv1;
    class MySplashViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return listImage.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            iv1=listImage.get(position);
//            iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(iv1);
            return iv1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
