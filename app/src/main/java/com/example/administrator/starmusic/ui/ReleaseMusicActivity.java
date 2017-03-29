package com.example.administrator.starmusic.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.administrator.starmusic.R;

public class ReleaseMusicActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_release_music);
        initView();
        setListener();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
    }

    private void initView() {
        ivBack= (ImageView) findViewById(R.id.ib_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
