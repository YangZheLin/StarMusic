package com.example.administrator.starmusic.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyViewPager2 extends ViewPager {
	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	OnSingleTouchListener onSingleTouchListener;


	public MyViewPager2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	private int startX;
	private int startY;
	/**
	 * 1. ���»�����Ҫ���� 2. ���һ������ҵ�ǰ�ǵ�һ��ҳ��,��Ҫ���� 3. ���󻬶����ҵ�ǰ�����һ��ҳ��,��Ҫ����
	 */
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		Log.d("MyViewPager2", "dispatchTouchEvent: ");
//		return super.dispatchTouchEvent(ev);
//	}

//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		Log.d("MyViewPager2", "onTouchEvent: ");
//		return super.onTouchEvent(ev);
//	}
//		@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		getParent().requestDisallowInterceptTouchEvent(true);
//
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			startX = (int) ev.getX();
//			startY = (int) ev.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//
//			int endX = (int) ev.getX();
//			int endY = (int) ev.getY();
//
//			int dx = endX - startX;
//			int dy = endY - startY;
//
//			if (Math.abs(dy) < Math.abs(dx)) {
//				int currentItem = getCurrentItem();
//				// ���һ���
//				if (dx > 0) {
//					// ���һ�
//					if (currentItem == 0) {
//						// ��һ��ҳ��,��Ҫ����
//						getParent().requestDisallowInterceptTouchEvent(false);
//					}
//				} else {
//					// ����
//					int count = getAdapter().getCount();// item����
//					if (currentItem == count - 1) {
//						// ���һ��ҳ��,��Ҫ����
//						getParent().requestDisallowInterceptTouchEvent(false);
//					}
//				}
//
//			} else {
//				// ���»���,��Ҫ����
//				getParent().requestDisallowInterceptTouchEvent(false);
//			}
//
//			break;
//
//		default:
//			break;
//		}
//
//		return super.dispatchTouchEvent(ev);
//	}
//
//
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		Log.d("MyViewPager2", "onInterceptTouchEvent: ");
//		return super.onInterceptTouchEvent(ev);
//	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		//当拦截触摸事件到达此位置的时候，返回true，
		//说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		//每次进行onTouch事件都记录当前的按下的坐标
		curP.x = arg0.getX();
		curP.y = arg0.getY();

		if(arg0.getAction() == MotionEvent.ACTION_DOWN){
			//记录按下时候的坐标
			//切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
			downP.x = arg0.getX();
			downP.y = arg0.getY();
			//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		if(arg0.getAction() == MotionEvent.ACTION_MOVE){
			//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		if(arg0.getAction() == MotionEvent.ACTION_UP){
			//在up时判断是否按下和松手的坐标为一个点
			//如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
			if(downP.x==curP.x && downP.y==curP.y){
				onSingleTouch();
				return true;
			}
		}

		return super.onTouchEvent(arg0);
	}

	/**
	 * 单击
	 */
	public void onSingleTouch() {
		if (onSingleTouchListener!= null) {

			onSingleTouchListener.onSingleTouch();
		}
	}

	/**
	 * 创建点击事件接口
	 * @author wanpg
	 *
	 */
	public interface OnSingleTouchListener {
		public void onSingleTouch();
	}

	public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}

}
