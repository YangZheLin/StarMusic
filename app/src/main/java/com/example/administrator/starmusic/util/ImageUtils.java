package com.example.administrator.starmusic.util;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.starmusic.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/26.
 */

public class ImageUtils {

    public static String getImagePath(Intent data, Context context){
        Uri selectedImage = data.getData();
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = context.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String imagePath = c.getString(columnIndex);
        c.close();
        return imagePath;
    }

    public static  void addPointView(LinearLayout ll_points, Context context, List listImage){
        int a[]={R.mipmap.aaa,R.mipmap.bbb,R.mipmap.ccc,R.mipmap.ddd,R.mipmap.eee};

        ImageView imageALL;
        View v;
        LinearLayout.LayoutParams params;
        for(int i=0;i<a.length;i++){
            imageALL=new ImageView(context);
            imageALL.setImageResource(a[i]);
//            imageALL.setOnClickListener(new MyImageClick(i, newslist, activity));
            listImage.add(imageALL);
            v=new View(context);
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
    /**
     * 将bitmap中的某种颜色值替换成新的颜色
     * @param oldBitmap    图片
     * @param oldColor 要修改的颜色
     * @param newColor 新的颜色
     * @return
     */
    public static Bitmap replaceBitmapColor(Bitmap oldBitmap, int oldColor, int newColor)
    {
        //相关说明可参考 http://xys289187120.blog.51cto.com/3361352/657590/
        Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        int mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        int[] mArrayColor = new int[mArrayColorLengh];
        int count = 0;
        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                //获得Bitmap 图片中每一个点的color颜色值
                //将需要填充的颜色值如果不是
                //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                int color = mBitmap.getPixel(j, i);
                //将颜色值存在一个数组中 方便后面修改
                if (color != oldColor) {
                    mBitmap.setPixel(j, i, newColor);  //将除了老的颜色替换成其他明色
                }

            }
        }
        return mBitmap;
    }
    /**
     * 从资源文件中获取图片
     *
     * @param context
     * 上下文
     * @param drawableId
     * 资源文件id
     * @return
     */
    public static Bitmap gainBitmap(Context context, int drawableId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                drawableId);
        return bmp;
    }

}
