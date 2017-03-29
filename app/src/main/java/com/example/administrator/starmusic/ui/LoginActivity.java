package com.example.administrator.starmusic.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.entity.LoginPeople;
import com.example.administrator.starmusic.entity.MusicDao;
import com.example.administrator.starmusic.model.MusicModel;
import com.example.administrator.starmusic.model.MusicRetrfitInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.R.attr.id;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private  LinearLayout llLogin;
    private EditText etLoginuseName;
    private EditText etLoginpwd;
    private TextView tvRebuildPwd;
    private TextView tvLoginRegister;
    private ImageButton ibLogin;

    private LinearLayout llRegister;
    private EditText etRegisterUsename;
    private EditText etRegisterWhh;
    private EditText etRegisterPwd;
    private EditText etRegisterAgainPwd;
    private ImageButton ibRegister;
    private TextView tvRegisterZhejie;

    private TextView tvTitle;
    private TextView tvLoginBack;

    private RelativeLayout rlRegsterSuceed;
    private TextView tvSuceedWhh;
    private TextView tvSuceedName;
    private TextView tvSuceedWhh2;
    private TextView tvSuceedPwd;
    private ImageButton ibSuceedLogin;

    private SharedPreferences preferences;
    private String name;
    private String pwd;
    MusicRetrfitInterface mrf;
    SharedPreferences.Editor editor;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://101.37.33.214:8080/DreamMusic/")
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        preferences=getSharedPreferences("loginuse", Context.MODE_PRIVATE);
//        String usename=  preferences.getString("usename",null);
//        String password=preferences.getString("password",null);
//        if(usename!=null&&password!=null){
//            login(usename,password);
//        }
        editor=preferences.edit();

        initview();
        setListener();
        mrf=retrofit.create(MusicRetrfitInterface.class);
    }

    private void login(final String usename, final String password) {
        Log.d("login","usename="+usename+"password="+password);
        Call<ResponseBody> call=mrf.getLoginMessage(usename,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String rsp=response.body().string();
                    System.out.println(rsp+"=ssssssssssssssssssssssssssssssssssss");
                    if("no".equalsIgnoreCase(rsp)){
                        Toast.makeText(LoginActivity.this,"请输入正确密码和账号",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        if (rsp != null) {
                            Gson gson = new Gson();
                            LoginPeople people = gson.fromJson(rsp, LoginPeople.class);
                            editor.putString("usename", people.getUserName());
                            editor.putString("password", people.getUserPass());
                            editor.putInt("VipGrade", people.getVipGrade());
                            editor.putInt("UserAge", people.getUserAge());
                            editor.putInt("UserSex", people.getUserSex());
                            editor.putString("UserNumber", people.getUserNumber());
                            editor.putBoolean("isLogin", true);
                            editor.commit();

                            finish();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }

        });
    }


    private void setListener() {
        tvLoginBack.setOnClickListener(this);
        ibLogin.setOnClickListener(this);
        ibRegister.setOnClickListener(this);
        tvRebuildPwd.setOnClickListener(this);
        tvLoginRegister.setOnClickListener(this);
        tvRegisterZhejie.setOnClickListener(this);
        ibSuceedLogin.setOnClickListener(this);
    }

    private void initview() {
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvLoginBack= (TextView) findViewById(R.id.tv_login_back);

        llLogin= (LinearLayout) findViewById(R.id.ll_login);
        etLoginuseName= (EditText) findViewById(R.id.et_login_usename);
        etLoginpwd=(EditText) findViewById(R.id.et_login_pwd);
        tvRebuildPwd= (TextView) findViewById(R.id.tv_login_rebuild_pwd);
        tvLoginRegister= (TextView) findViewById(R.id.tv_login_register);
        ibLogin= (ImageButton) findViewById(R.id.ib_login_login);

        llRegister= (LinearLayout) findViewById(R.id.ll_register);
        etRegisterUsename= (EditText) findViewById(R.id.et_register_usename);
        etRegisterWhh= (EditText) findViewById(R.id.et_register_whh);
        etRegisterPwd= (EditText) findViewById(R.id.et_register_pwd);
        etRegisterAgainPwd= (EditText) findViewById(R.id.et_register_again_pwd);
        ibRegister= (ImageButton) findViewById(R.id.ib_register_register);
        tvRegisterZhejie= (TextView) findViewById(R.id.tv_regoster_zhijie);

        rlRegsterSuceed= (RelativeLayout) findViewById(R.id.rl_regster_suceed);
        tvSuceedWhh= (TextView) findViewById(R.id.tv_suceed_whh);
        tvSuceedName= (TextView) findViewById(R.id.tv_suceed_name);
        tvSuceedWhh2= (TextView) findViewById(R.id.tv_suceed_whh2);
        tvSuceedPwd= (TextView) findViewById(R.id.tv_suceed_pwd);
        ibSuceedLogin= (ImageButton) findViewById(R.id.ib_suceed_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login_back:
                if("注册界面".equals(tvTitle.getText().toString())){
                    tvTitle.setText("用户登入");
                    llRegister.setVisibility(View.GONE);
                    llLogin.setVisibility(View.VISIBLE);
            }else{
                    finish();
                }
                break;
            case R.id.ib_login_login:
                String usename=etLoginuseName.getText().toString().trim();
                String password=etLoginpwd.getText().toString().trim();
                if(TextUtils.isEmpty(usename)){
                    Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
                  return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    login(usename,password);
                }

                break;
            case R.id.ib_register_register:
                String registerusename=etRegisterUsename.getText().toString().trim();
                String registerWhh=etRegisterWhh.getText().toString().trim();
                String registerPwd=etRegisterPwd.getText().toString().trim();
                String registeragainPwd=etRegisterAgainPwd.getText().toString().trim();
                if(registerWhh.length()!=6){
                    Toast.makeText(this,"网红号必须为6为",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!TextUtils.isEmpty(registerusename)&&!TextUtils.isEmpty(registerWhh)&&!TextUtils.isEmpty(registerPwd)&&!TextUtils.isEmpty(registeragainPwd)){
                    if(registerPwd!=registeragainPwd){
                        register(registerusename,registerWhh,registerPwd);
                    }else{
                        Toast.makeText(this,"两次密码输入的不一致",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this,"所有项不能有一个为空",Toast.LENGTH_LONG).show();
                }




                break;
            case R.id.tv_login_rebuild_pwd:
                break;
            case R.id.tv_login_register:
                llLogin.setVisibility(View.GONE);
                tvTitle.setText("注册界面");
                llRegister.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_regoster_zhijie:
                tvTitle.setText("用户登入");
                llRegister.setVisibility(View.GONE);
                llLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.ib_suceed_login:
                if(name!=null&pwd!=null){
                    login(name,pwd);
                }

                break;
        }
    }

    private void register(final String registerusename, final String registerWhh, final String registerPwd) {
        Call<ResponseBody> call=mrf.getRegisterMessage(registerWhh,registerPwd,registerusename,"20","1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String rsp=response.body().string();
                    System.out.println(rsp+"==ssssssssssssssssssssssssssssssssssss");
                    if("ok".equalsIgnoreCase(rsp)){
                        name=registerWhh;
                        pwd=registerPwd;
                        tvSuceedWhh.setText("恭喜  "+registerWhh+" 网红号注册成功！");
                        tvSuceedName.setText("昵称 "+registerusename);
                        tvSuceedWhh2.setText("网红号 "+registerWhh);
                        tvSuceedPwd.setText("密码 "+registerPwd);
                        llRegister.setVisibility(View.GONE);
                        rlRegsterSuceed.setVisibility(View.VISIBLE);

                    }else if("no".equalsIgnoreCase(rsp)){
                        Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
