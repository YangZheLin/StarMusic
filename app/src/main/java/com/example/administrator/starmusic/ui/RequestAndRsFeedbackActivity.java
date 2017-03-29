package com.example.administrator.starmusic.ui;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.util.ScreenUtils;

public class RequestAndRsFeedbackActivity extends AppCompatActivity implements View.OnLayoutChangeListener,View.OnClickListener{
private ScrollView scrollView;
    private View activityRootView;
    private Handler handler;
    private int keyHeight;
    private int screenHeight;
    private LinearLayout llRequst;
    private ImageButton ibCommit;
    private ImageButton ibBack;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_request_and_rs_feedback);

        initView();
        setlistener();
    }

    private void initView() {
        scrollView= (ScrollView) findViewById(R.id.sv_request);
        llRequst= (LinearLayout) findViewById(R.id.activity_request_and_rs_feedback);
        ibCommit= (ImageButton) findViewById(R.id.ib_commit);
        imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ibBack= (ImageButton) findViewById(R.id.ib_back);

        handler=new Handler();
        screenHeight= ScreenUtils.getScreenHeight(this);
        keyHeight=screenHeight/2;
    }

    private void setlistener() {
        scrollView.addOnLayoutChangeListener(this);
        ibCommit.setOnClickListener(this);
        ibBack.setOnClickListener(this);
    }

    private void scrollToBottom(){
        int offset=keyHeight;
//  int offset=  llRequst.getMeasuredHeight()-scrollView.getHeight();
//    if(offset<0){
//        offset=0;
//    }
        scrollView.smoothScrollTo(0,offset);
        Log.d("request","执行没有offset:"+offset);
}
    private boolean istanchu=true;
    private boolean isFirst=true;
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        Log.d("request","执行没有left:"+left+"top:"+top+"right:"+right+"oldBottom: "+oldBottom+"bottom: "+bottom+"oldleft:"+oldLeft+"oldtop:"+oldTop+"oldright:"+oldRight);
                            scrollToBottom();
//
//                        if(oldBottom!=0&&bottom!=0&&oldBottom-bottom>keyHeight){
//                            Log.d("request","执行没有");
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    scrollToBottom();
////                                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                                }
//                            });
//                        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) istanchu=true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_commit:
                imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
