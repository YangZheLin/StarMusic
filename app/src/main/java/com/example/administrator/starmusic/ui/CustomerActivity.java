package com.example.administrator.starmusic.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.entity.Customer;
import com.example.administrator.starmusic.util.ImageUtils;
import com.example.administrator.starmusic.view.CircleImageView;

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibBack;
    private CircleImageView ivHead;
    private static final int IMAGE = 1;
    private Intent intentPic;
    private Intent intentCustomer;
    private  Bitmap bm;
    private Customer customer;
    private ImageButton ibCommit;
    private EditText etPetName;
    private EditText etDengJi;
    private EditText etGuanZhu;
    private EditText etFengSi;
    private EditText etqq;
    private EditText etName;
    private EditText etAge;
    private EditText etSex;
    private EditText etPhoneNumber;
    private EditText etAddrest;
    private EditText etJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_customer);
        initView();
        setListener();
    }

    private void initView() {
        ibBack= (ImageButton) findViewById(R.id.ib_back);
        ibCommit= (ImageButton) findViewById(R.id.ib_commit);
        ivHead= (CircleImageView) findViewById(R.id.iv_head);

        etPetName= (EditText) findViewById(R.id.et_petname);
        etDengJi= (EditText) findViewById(R.id.et_deng_ji);
        etGuanZhu= (EditText) findViewById(R.id.et_guan_zhu);
        etFengSi= (EditText) findViewById(R.id.et_feng_si);
        etqq= (EditText) findViewById(R.id.et_qq);
        etName= (EditText) findViewById(R.id.et_name);
        etAge= (EditText) findViewById(R.id.et_age);
        etSex= (EditText) findViewById(R.id.et_sex);
        etPhoneNumber= (EditText) findViewById(R.id.et_phone_number);
        etAddrest= (EditText) findViewById(R.id.et_addrest);
        etJob= (EditText) findViewById(R.id.et_job);

        customer=new Customer();
        //调用相册
        intentPic = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentCustomer=new Intent();
        intentCustomer.setAction("customer");
    }

    private void setListener() {
        ibBack.setOnClickListener(this);
        ibCommit.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }
private  String imagePath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imagePath = ImageUtils.getImagePath(data,this);
            customer.setPicPath(imagePath);
            showImage(imagePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //加载图片
    private void showImage(String imaePath){
        bm = BitmapFactory.decodeFile(imaePath);
        ivHead.setImageBitmap(bm);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_head:
                if(intentPic!=null){
                    startActivityForResult(intentPic, IMAGE);
                }
                break;
            case R.id.ib_commit:
                customer.setPetName(getEtText(etPetName));
                customer.setDengJi(getEtText(etDengJi));
                customer.setGuanZhu(getEtText(etGuanZhu));
                customer.setFengSi(getEtText(etFengSi));
                customer.setQq(getEtText(etqq));
                customer.setName(getEtText(etName));
                customer.setAge(getEtText(etAge));
                customer.setSex(getEtText(etSex));
                customer.setPhoneNumber(getEtText(etPhoneNumber));
                customer.setAddress(getEtText(etAddrest));
                customer.setJob(getEtText(etJob));
                intentCustomer.putExtra("cust",customer);
                sendBroadcast(intentCustomer);
                finish();
                break;
        }
    }
    public String getEtText(EditText et){
        return et.getText().toString().trim();
    }
}
