package com.example.administrator.starmusic.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.model.MusicModel;
import com.example.administrator.starmusic.model.MusicModelInterface;
import com.example.administrator.starmusic.util.ImageUtils;
import com.example.administrator.starmusic.view.CircleImageDrawable;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SheQuActivity extends AppCompatActivity {
    private ImageView iv;
    private Bitmap oldBitmap;
    private Bitmap newBitmap;
    private ArrayList<Bitmap> mList;
    private CircleImageDrawable drawable;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mList= (ArrayList) msg.obj;
//            drawable=new CircleImageDrawable(mList.get(0),mList.get(1));
//            iv.setBackground(drawable);
            Bitmap bt = (Bitmap) msg.obj;
            iv.setImageBitmap(bt);
            super.handleMessage(msg);
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_she_qu);
        mList = new ArrayList();
        iv = (ImageView) findViewById(R.id.iv_text);
//        MusicModelInterface mm = new MusicModel();

//        new Thread() {
//            @Override
//            public void run() {
//                ArrayList<Bitmap>  list=new ArrayList();
//                oldBitmap= ImageUtils.gainBitmap(SheQuActivity.this,R.mipmap.window_start);
//                newBitmap=ImageUtils.replaceBitmapColor(oldBitmap, Color.parseColor("#ffffff"),Color.parseColor("#66000000"));
//                Message msg=  handler.obtainMessage();
//                list.add(oldBitmap);
//                list.add(newBitmap);
//               msg.obj=list;
//                handler.sendMessage(msg);
                String url = "http://1p6438u002.51mypc.cn/DreamMusic/getDataServlet";
//                String url = "http://1p6438u002.51mypc.cn/DreamMusic/pic.do?picturePath=D:\\Users\\adminwk\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\DreamMusic\\WEB-INF\\upload\\5a34e515039349fc91777fa016f3099a_遇梦歌曲默认图片 测试 二.png";
//                MusicModelInterface mm = new MusicModel(this);
//                List<Music> listMuisc = mm.getMusics(url);
//                if (listMuisc != null) {
//                    Log.d("message", "转换成功");
//                } else {
//                    Log.d("message", "转换失败");
//                }
////                InputStream is = mm.getPicture(url);
////                if(is==null){
////                    Log.d("msg","没有东西");
////                }
////                Message msg = handler.obtainMessage();
////                Bitmap bt = BitmapFactory.decodeStream(is);
////                msg.obj = bt;
////                handler.sendMessage(msg);
//            }
//        }.start();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

}
