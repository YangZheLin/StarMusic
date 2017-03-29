package com.example.administrator.starmusic.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.entity.MusicDao;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by adminwk on 2017/1/24.
 */

public class MusicModel implements MusicModelInterface  {
    private Retrofit retrofit;
    private MusicRetrfitInterface mrf;
    private MyhuiDiao myhuiDiao;

    public MusicModel(MyhuiDiao myhuiDiao) {
       this.myhuiDiao=myhuiDiao;
    }



    @Override
    public InputStream getPicture(String url) {
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null; //写
        InputStream in = null;   //读
        int responseCode = 0;    //远程主机响应的HTTP状态码
        try{
            Log.d("msg","执行没");
            URL sendUrl = new URL(url);
            httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
            //get方式请求
            httpURLConnection.setRequestMethod("GET");
            responseCode = httpURLConnection.getResponseCode();
            if(responseCode==200){
                Log.d("msg","连接成功");
                in=httpURLConnection.getInputStream();
            }else {
                Log.d("msg","没连接成功");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return in;
    }

    @Override
    public List<Music> getMusics(String url) {
        List<Music> listMusic=null;
        InputStream is=getPicture(url);
        if(is!=null){
            String json=convertStreamToString(is);
//        Log.d("数据获取到了",json);
            if(json!=null) {
                Gson gson = new Gson();
                MusicDao md = gson.fromJson(json, MusicDao.class);
                listMusic = md.getListMusic();
                Log.d("数据获取到了", listMusic.size() + "");
            }
        }

        return listMusic;
    }

    @Override
    public void setMyHuiDiao(MyhuiDiao myHuiDiao) {
        this.myhuiDiao=myHuiDiao;
    }

    @Override
    public void getMusiclistByThread(final String url) {
//        synchronized (this) {
//            new AsyncTask<Void, Void, List<Music>>() {
//                @Override
//                protected List<Music> doInBackground(Void... params) {
//                    List<Music> listMusic = getMusics(url);
//                    return listMusic;
//                }
//                @Override
//                protected void onPostExecute(List<Music> musics) {
//                    if (myhuiDiao != null) {
//                        if (musics != null&&musics.size()>0) {
//                            myhuiDiao.setList(musics);
//                            Log.d(" MusicModel", "解析成功");
//                        }
//
//                    }
//                }
//            }.execute();
//        }
        new Thread(){
            @Override
            public void run() {
                List<Music> listMusic=getMusics(url);
                if(myhuiDiao!=null){
                    if(listMusic!=null&&listMusic.size()>0){
                        myhuiDiao.setList(listMusic);
                        Log.d(" MusicModel","解析成功");
                    }

                }
            }
        }.start();
    }

    public String convertStreamToString(InputStream is){
        StringBuffer sb;
        BufferedReader reader;
        if(is!=null){
         reader=new BufferedReader( new InputStreamReader(is));
         sb=new StringBuffer();
        String line=null;
        try {
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return sb.toString();
        }
        return null;
    }



   public  interface MyhuiDiao{
      void  setList(List<Music> listmusic);
    }

}
