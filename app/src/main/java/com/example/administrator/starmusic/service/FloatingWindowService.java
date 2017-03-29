 package com.example.administrator.starmusic.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.ui.MusicActivity;
import com.example.administrator.starmusic.ui.SheQuActivity;
import com.example.administrator.starmusic.util.Consts;
import com.example.administrator.starmusic.util.ImageUtils;
import com.example.administrator.starmusic.util.RotatoAnimation;
import com.example.administrator.starmusic.util.WindowBroadCastConsts;
import com.example.administrator.starmusic.view.CircleImageDrawable;
import com.example.administrator.starmusic.view.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

 public class FloatingWindowService extends Service implements View.OnClickListener,Consts {
    public FloatingWindowService() {
    }

    public static final String OPERATION = "operation";
    public static final int OPERATION_SHOW = 100;
    public static final int OPERATION_HIDE = 101;

    private static final int HANDLE_CHECK_ACTIVITY = 200;
     private ImageView civPic;

     private Bitmap oldBitmap;
     private Bitmap oldPauseBitmap;

     private  Bitmap newBitmap;
     private  Bitmap newPauseBitmap;
     private ArrayList<Bitmap> mList;

     private CircleImageDrawable drawable;
     private CircleImageDrawable pauseDrawable;

    private boolean isAdded = false; // 是否已增加悬浮窗
    private static WindowManager wm;
    private static WindowManager.LayoutParams params;
    private View btn_floatView;
    private  Intent activityIntent;
//    private List<String> homeList; // 桌面应用程序包名列表
//    private ActivityManager mActivityManager;
     private LayoutInflater inflater;
//     private UsageStatsManager sUsageStatsManager;
     private RotatoAnimation rotatoAnimation;
//     private CircleImageView civPic;
     /**
      * 广播接收者
      */
     private BroadcastReceiver receiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        muiscServiceIntent=new Intent();
        mList=new ArrayList();
//        homeList = getHomes();
        createFloatView();
        IntentFilter filter = new IntentFilter();
        filter.addAction(OFFWINDOW);
        filter.addAction(STARTWINDOW);
        filter.addAction(STARTANIM);
        filter.addAction(PAUSEANIM);
        receiver=new FloatingWindowReceiver();
        registerReceiver(receiver, filter);


    }

private class FloatingWindowReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(OFFWINDOW.equals(action)){
            removeFloatingWindow();
        }else  if(STARTWINDOW.equals(action)){
            mHandler.sendEmptyMessage(HANDLE_CHECK_ACTIVITY);
        }else if(STARTANIM.equals(action)){
                startMusic();
        }else if(PAUSEANIM.equals(action)){
                pauseMusic();

        }
    }
}
     private void pauseMusic(){
         if(rotatoAnimation.isRunning()){
             rotatoAnimation.pauseAnimation();
//                civPic.setImageResource(R.mipmap.window_start);
             civPic.setBackground(drawable);
         }
     }
     private Intent muiscServiceIntent;
private void startMusic(){
    if(!rotatoAnimation.isRunning()){
        rotatoAnimation.startAnimation();
//                civPic.setImageResource(R.mipmap.window_pause);
        civPic.setBackground(pauseDrawable);

    }
}
     @Override
    public void onDestroy() {
        super.onDestroy();
         unregisterReceiver(receiver);

    }

     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
         return START_NOT_STICKY;

     }

     @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }
     public interface OnDoubleClickListener {
         public void OnSingleClick(View v);
         public void OnDoubleClick(View v);
     }
    private boolean isDouble;
//     然后再添加一个方法：
     public  void registerDoubleClickListener(final View view, final OnDoubleClickListener listener){
         if(listener!=null){
//         time.add(System.currentTimeMillis());
         view .setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 isDouble=!isDouble;
                 time.add(System.currentTimeMillis());
                 if(time.size()==1){
                    if(isDouble){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(isDouble){
                                    listener.OnSingleClick(view);
                                    time.clear();
                                    isDouble=false;
                                }

                            }
                        },200);
                    }
                 }else if(time.size()==2){
                if(!isDouble){
                  mHandler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          if((time.get(1)-time.get(0))<300L&&(time.get(1)-time.get(0))>=100L){
                              if(!isDouble){
                                  listener.OnDoubleClick(view);
                                  time.clear();
                              }

                          }
                      }
                  },200)  ;
                }

                 } else if(time.size()==3){
                     view.setEnabled(false);
                     Toast.makeText(FloatingWindowService.this,"点击太快了 1秒以后在点",Toast.LENGTH_SHORT).show();
                     mHandler.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             time.clear();
                             view.setEnabled(true);
                            isDouble=false;
                         }
                     }, 1000);
                 }
             }
         });
         }
     }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case HANDLE_CHECK_ACTIVITY:
                        if(!isAdded) {
                            wm.addView(btn_floatView, params);
                            isAdded = true;
                    } else {
                        if(isAdded) {
                            removeFloatingWindow();
                        }
                    }
                    break;

                case 2:;
                break;
                case 3:;
                    mList= (ArrayList) msg.obj;
                    drawable=new CircleImageDrawable(mList.get(0),mList.get(1));
                    pauseDrawable=new CircleImageDrawable(mList.get(2),mList.get(3));
                    civPic.setBackground(drawable);

                    rotatoAnimation= new RotatoAnimation(civPic);
                    wm = (WindowManager) getApplicationContext()
                            .getSystemService(Context.WINDOW_SERVICE);
                    params = new WindowManager.LayoutParams();

                    // 设置window type
                    params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        /*
         * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE;
         * 那么优先级会降低一些, 即拉下通知栏不可见
         */

                    params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

                    // 设置Window flag
                    params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 下面的flags属性的效果形同“锁定”。
         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
        wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | LayoutParams.FLAG_NOT_FOCUSABLE
                               | LayoutParams.FLAG_NOT_TOUCHABLE;
         */

                    // 设置悬浮窗的长得宽
                    params.width = drawable.mWidth;
                    params.height = drawable.mWidth;
                    params.gravity= Gravity.CENTER;
//        params.gravity= Gravity.RIGHT|Gravity.BOTTOM;
                    DisplayMetrics dm =getResources().getDisplayMetrics();
//        params.x = dm.widthPixels;
//        params.y = dm.heightPixels;
                    params.x=200;
                    params.y=0;
//        btn_floatView.setOnClickListener(this);
                    registerDoubleClickListener(btn_floatView, new OnDoubleClickListener() {
                        @Override
                        public void OnSingleClick(View v) {
                            sendguangbo();
                        }

                        @Override
                        public void OnDoubleClick(View v) {
                            tiaozhuan();
                        }
                    });
                    // 设置悬浮窗的Touch监听
                    btn_floatView.setOnTouchListener(new View.OnTouchListener() {
                        int lastX, lastY;
                        int paramX, paramY;
                        long currentTime;
                        long lastTime;
                        long newTime;


                        public boolean onTouch(View v, MotionEvent event) {
                            int dianji=0;
                            switch(event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    currentTime=System.currentTimeMillis();
                                    lastX = (int) event.getRawX();
                                    lastY = (int) event.getRawY();
                                    paramX = params.x;
                                    paramY = params.y;
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    int dx = (int) event.getRawX() - lastX;
                                    int dy = (int) event.getRawY() - lastY;
                                    params.x = paramX + dx;
                                    params.y = paramY + dy;
// 更新悬浮窗位置
                                    wm.updateViewLayout(btn_floatView, params);
                                    break;
                                case MotionEvent.ACTION_UP:
//
//                                    lastTime=System.currentTimeMillis();
//                                    newTime=lastTime-currentTime;
//
//                                        if(lastTime-currentTime<200){
//                                            sendguangbo();
//                                        }




                                    break;
                            }
                            return false;
                        }
                    });

                    wm.addView(btn_floatView, params);
                    isAdded = true;
                    break;


            }
        }
    };

     private void tiaozhuan(){
         Intent musicActivity=new Intent(FloatingWindowService.this,MusicActivity.class);
         musicActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         musicActivity.putExtra("isRunning",rotatoAnimation.isRunning());
         startActivity(musicActivity);
     }

     private void removeFloatingWindow(){
//    rotatoAnimation.stopAnimation();
    wm.removeView(btn_floatView);
    isAdded = false;
}
     private class MyThread extends Thread{
         private  Context context;
         public MyThread(Context context){
             this.context=context;
         }
         @Override
         public void run() {
             ArrayList<Bitmap>  list=new ArrayList();
             oldBitmap= ImageUtils.gainBitmap(context,R.mipmap.window_start);
             oldPauseBitmap=ImageUtils.gainBitmap(context,R.mipmap.window_pause);
             newBitmap=ImageUtils.replaceBitmapColor(oldBitmap, Color.parseColor("#ffffff"),Color.parseColor("#66000000"));
             newPauseBitmap=ImageUtils.replaceBitmapColor(oldPauseBitmap,Color.parseColor("#ffffff"),Color.parseColor("#66000000"));
             Message msg=  mHandler.obtainMessage();
             list.add(oldBitmap);
             list.add(newBitmap);
             list.add(oldPauseBitmap);
             list.add(newPauseBitmap);
             msg.obj=list;
             msg.what=3;
             mHandler.sendMessage(msg);
         }
     }

    /**
     * 创建悬浮窗
     */
    private void createFloatView() {
        inflater=LayoutInflater.from(getApplicationContext());
        btn_floatView=inflater.inflate(R.layout.floating_window,null);
//        civPic= (CircleImageView) btn_floatView.findViewById(R.id.civ_pic);
        civPic= (ImageView)  btn_floatView.findViewById(R.id.iv_text);
                new MyThread(getApplicationContext()).start();
    }

//    /**
//     * 获得属于桌面的应用的应用包名称
//     * @return 返回包含所有包名的字符串列表
//     */
//    private List<String> getHomes() {
//        List<String> names = new ArrayList<String>();
//        PackageManager packageManager = this.getPackageManager();
//        // 属性
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
//                PackageManager.MATCH_DEFAULT_ONLY);
//        for(ResolveInfo ri : resolveInfo) {
//            names.add(ri.activityInfo.packageName);
//        }
//        return names;
//    }

//    /**
//     * 判断当前界面是否是桌面
//     */
//    public boolean isHome(){
//        return homeList.contains(getTopActivty());
//    }

//     //获取到栈顶应用程序的包名
//     public String getTopActivty() {
//
//         String topPackageName="888";
//
////android5.0以上获取方式
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//             UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
//             long time = System.currentTimeMillis();
//
//             List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
//
//             if (stats != null) {
//                 SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
//                 for (UsageStats usageStats : stats) {
//                     mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
//                 }
//                 if (mySortedMap != null && !mySortedMap.isEmpty()) {
//                     topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                     Log.e("TopPackage Name", topPackageName);
//                 }
//             }
//
//         }
////android5.0以下获取方式
//         else{
//             if(mActivityManager == null) {
//            mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//        }
//             List<ActivityManager.RunningTaskInfo> tasks =  mActivityManager.getRunningTasks(1);
//
//             ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
//
//             topPackageName = taskInfo.topActivity.getPackageName();
//
//         }
//
//         return topPackageName;
//
//     }

     public void sendguangbo(){
         if(rotatoAnimation.isRunning()){
             muiscServiceIntent.setAction(ACTION_PAUSE);
             civPic.setBackground(pauseDrawable);

         }else{
             muiscServiceIntent.setAction(ACTION_PLAY);
             civPic.setBackground(drawable);
         }

        FloatingWindowService.this.sendBroadcast( muiscServiceIntent);
     }
private List<Long> time=new ArrayList();
     @Override
     public void onClick(View v) {

 }
 }
