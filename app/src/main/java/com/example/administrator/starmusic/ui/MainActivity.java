package com.example.administrator.starmusic.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.entity.Customer;
import com.example.administrator.starmusic.fragment.Bangdanfragment;
import com.example.administrator.starmusic.fragment.likefragment;
import com.example.administrator.starmusic.fragment.Musicfragment;
import com.example.administrator.starmusic.service.DemoIntentService;
import com.example.administrator.starmusic.service.DemoPushService;
import com.example.administrator.starmusic.service.FloatingWindowService;
import com.example.administrator.starmusic.service.MusicService;
import com.example.administrator.starmusic.util.Consts;
import com.example.administrator.starmusic.util.WindowBroadCastConsts;
import com.example.administrator.starmusic.view.CircleImageView;
import com.example.administrator.starmusic.view.MyViewPager;
import com.example.administrator.starmusic.view.SlidingMenu;
import com.example.administrator.starmusic.view.SwitchButton;
import com.igexin.sdk.PushManager;
import com.tencent.bugly.Bugly;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener,View.OnClickListener,Consts {
    private SlidingMenu mMenu;
    private CircleImageView ivHead;
    private MyViewPager vp;
    private List<Fragment> listFragment;
    private RadioGroup rg;
    private RadioButton rb;;
    private View includContent;
    private View includMenu;

    private TextView tvSetting;
    private TextView tvFanKui;
    private TextView tvMenuWhh;
    private TextView tvMenuBack;
    private TextView tvFaBu;
    private TextView tvSheQu;
    private CircleImageView civMenuHead;

    private TextView tvNiCheng;
    private TextView tvgzsm;
    private TextView tvfssm;
    private TextView tvDj;

    private SwitchButton sbWifiContect;
    private  Intent intent;
    private  Intent intentSetting;
    private  Intent intentRequest;
    private Intent intentFaBu;
    private Intent intentSheQu;
    private Intent intentCustomer;

    private RelativeLayout rlLogin;
    private TextView tvSlidLogin;
    private LinearLayout llSlidemenu;
    private SharedPreferences preferences;
    private boolean isLogin;
    private boolean isOk;
    private BroadcastReceiver broadcastReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main2);
        Bugly.init(getApplicationContext(), "2917a34ac8", true);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

            initView();
            setadapter();
            setlistener();

    }

    private void sendBroad(){
    if(intent==null){
        intent=new Intent();
        intent.setAction(STARTWINDOW);
    }
    sendBroadcast(intent);
}
    @Override
    protected void onResume() {
        isLogin=preferences.getBoolean("isLogin",false);
        if(isLogin&&!isOk){
            tvNiCheng.setText(preferences.getString("usename",""));
            tvMenuWhh.setText("网红号 ："+preferences.getString("UserNumber",""));
            llSlidemenu.setVisibility(View.VISIBLE);
            rlLogin.setVisibility(View.GONE);
            isOk=true;
        }else if(!isLogin){
            llSlidemenu.setVisibility(View.GONE);
            rlLogin.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
//        sendBroad();
        super.onStop();
    }

    private void setlistener() {

        vp.addOnPageChangeListener(this);
        rg.setOnCheckedChangeListener(this);
        ivHead.setOnClickListener(this);

        tvSlidLogin.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tvFanKui.setOnClickListener(this);
        tvFaBu.setOnClickListener(this);
        tvSheQu.setOnClickListener(this);
        civMenuHead.setOnClickListener(this);
        tvMenuBack.setOnClickListener(this);
        mMenu.setChenJIngShi(new SlidingMenu.ChenJingShi() {
            @Override
            public void asOpenMenu() {
                mMenu.setBackgroundColor(Color.parseColor("#FAEAE6"));
            }

            @Override
            public void asCloseMenu() {
                mMenu.setBackgroundColor(Color.parseColor("#e12244"));
            }
        });
    }

    private void setadapter() {
        MyFragmentadapter adapter=new MyFragmentadapter(getSupportFragmentManager());
        vp.setAdapter( adapter);
    }

    private void initView() {
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        rg= (RadioGroup) findViewById(R.id.rg);
        vp= (MyViewPager) findViewById(R.id.myvp);
        includContent=findViewById(R.id.include_content);
        includMenu=findViewById(R.id.include_menu);
//        sbWifiContect= (SwitchButton) includMenu.findViewById(R.id.sb_wifi_lianwang);
//        sbWifiContect.setChecked(true);

        tvSetting= (TextView) includMenu.findViewById(R.id.tv_setting);
        tvFanKui= (TextView) includMenu.findViewById(R.id.tv_fankui);
        tvFaBu= (TextView) includMenu.findViewById(R.id.tv_fa_bu);
        tvSheQu= (TextView) includMenu.findViewById(R.id.tv_she_qu);
        civMenuHead= (CircleImageView) includMenu.findViewById(R.id.iv_menu_head);
        tvNiCheng= (TextView) includMenu.findViewById(R.id.tv_ni_cheng);
        tvgzsm= (TextView) includMenu.findViewById(R.id.tv_gzsm);
        tvfssm= (TextView) includMenu.findViewById(R.id.tv_fssm);
        tvMenuWhh= (TextView) includMenu.findViewById(R.id.tv_menu_whh);
        tvMenuBack= (TextView) includMenu.findViewById(R.id.tv_menu_back);
        tvDj= (TextView) includMenu.findViewById(R.id.tv_dj);
        ivHead= (CircleImageView) includContent.findViewById(R.id.iv_head);

        rlLogin= (RelativeLayout) findViewById(R.id.rl_login);
        tvSlidLogin= (TextView) findViewById(R.id.tv_slidemenu_login);
        llSlidemenu= (LinearLayout) findViewById(R.id.ll_slidemenu);
        preferences=getSharedPreferences("loginuse", Context.MODE_PRIVATE);

        listFragment=new ArrayList();
        listFragment.add(new likefragment(this));
        listFragment.add(new Musicfragment(this));
        listFragment.add(new Bangdanfragment(this));






        IntentFilter filter = new IntentFilter();
        filter.addAction("customer");
        broadcastReceiver=new MainReceiver();
        registerReceiver(broadcastReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        if(broadcastReceiver!=null){
            unregisterReceiver(broadcastReceiver);
        }

        super.onDestroy();
    }

    class MainReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if("customer".equals(action)){
            Customer customer= (Customer) intent.getSerializableExtra("cust");
            tvDj.setText(customer.getDengJi());
            tvfssm.setText(customer.getFengSi());
            tvgzsm.setText(customer.getGuanZhu());
            tvNiCheng.setText(customer.getPetName());
            String picPath=customer.getPicPath();
            if(picPath!=null){
                civMenuHead.setImageBitmap(BitmapFactory.decodeFile(picPath));
            }

        }
    }
}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_setting:
                if(intentSetting==null){
                    intentSetting=new Intent(this,SettingActivity.class);
                }
                startActivity(intentSetting);
                break;
            case R.id.iv_head:
                mMenu.toggle();
                break;
            case R.id.tv_fankui:
                if(intentRequest==null){
                    intentRequest=new Intent(this,RequestAndRsFeedbackActivity.class);
                }

                startActivity(intentRequest);
                break;
            case R.id.tv_fa_bu:
                if(intentFaBu==null){
                    intentFaBu=new Intent(this,ReleaseMusicActivity.class);
                }
                startActivity(intentFaBu);
                break;
            case R.id.tv_she_qu:
//               showToast("还没有开通此服务");
                if(intentSheQu==null){
                    intentSheQu=new Intent(this,SheQuActivity.class);
                }
                startActivity(intentSheQu);
                break;
            case R.id.iv_menu_head:
                if(intentCustomer==null){
                    intentCustomer=new Intent(this,CustomerActivity.class);
                }
                startActivity(intentCustomer);
                break;
            case R.id.tv_slidemenu_login:
                Intent intent=new Intent(this,LoginActivity.class);
                mMenu.toggle();
                startActivity(intent);
                break;
            case R.id.tv_menu_back:
               preferences.edit().putBoolean("isLogin",false);
                llSlidemenu.setVisibility(View.GONE);
                rlLogin.setVisibility(View.VISIBLE);
                break;
        }

    }
private void showToast(String text){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show();
}
    class MyFragmentadapter extends FragmentPagerAdapter {

        public MyFragmentadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return listFragment.get(arg0);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

    }
    private void initSlidingMenu() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        rb=(RadioButton) rg.getChildAt(position);
        rb.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index=0;
        switch (checkedId) {
            case R.id.rb_like:
                index=0;
                break;
            case R.id.rb_music:
                index=1;
                break;
            case R.id.rb_bangdan:
                index=2;
                break;
        }
        vp.setCurrentItem(index);
    }
}
