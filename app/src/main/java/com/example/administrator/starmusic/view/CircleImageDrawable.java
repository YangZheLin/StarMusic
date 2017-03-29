package com.example.administrator.starmusic.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/6.
 */

public class CircleImageDrawable extends Drawable {
    private Paint mPaint;
    private Paint shadowPaint;
    public int mWidth;
    private Bitmap mBitmap ;

    public CircleImageDrawable(Bitmap bitmap,Bitmap shadowBitmap)
    {
        mBitmap = bitmap ;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        BitmapShader bitmapShadow = new BitmapShader(shadowBitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mPaint = new Paint();
        shadowPaint = new Paint();
        mPaint.setAntiAlias(true);
        shadowPaint.setAntiAlias(true);
        mPaint.setShader(bitmapShader);
        shadowPaint.setShader(bitmapShadow);
        mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, shadowPaint);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2-4, mPaint);
    }

    @Override
    public int getIntrinsicWidth()
    {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight()
    {
        return mWidth;
    }

    @Override
    public void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
        mPaint.setColorFilter(cf);
    }


    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }

}
