package com.example.administrator.starmusic.ui;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.util.Consts;
import com.example.administrator.starmusic.util.WindowBroadCastConsts;
import com.example.administrator.starmusic.view.SwitchButton;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,Consts {
   private  SwitchButton sbWifiContect;
    private  SwitchButton sbAuto;
    private  SwitchButton sbFloatingWindow;
    private TextView ibBack;
    private Intent intentWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        initView();
       setListener();
    }

    private void setListener() {
        ibBack.setOnClickListener(this);
        sbFloatingWindow.setOnCheckedChangeListener(this);
        sbAuto.setOnCheckedChangeListener(this);
        sbWifiContect.setOnCheckedChangeListener(this);
    }

    private void initView() {
        sbWifiContect= (SwitchButton) findViewById(R.id.sb_wifi_contect);
        sbAuto= (SwitchButton) findViewById(R.id.sb_wifi_auto_huancun);
        sbFloatingWindow= (SwitchButton) findViewById(R.id.sb_floating_windown);
        ibBack= (TextView) findViewById(R.id.ib_back);
        sbWifiContect.setChecked(true);
        sbAuto.setChecked(true);
        sbFloatingWindow.setChecked(true);
        intentWindow=new Intent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.sb_floating_windown:
                if(!isChecked){
                    intentWindow.setAction(OFFWINDOW);
                }else{
                    intentWindow.setAction(STARTWINDOW);
                }
               sendBroadcast(intentWindow);
                break;
            case R.id.sb_wifi_auto_huancun:
                break;
            case R.id.sb_wifi_contect:
                break;
        }
    }
}
