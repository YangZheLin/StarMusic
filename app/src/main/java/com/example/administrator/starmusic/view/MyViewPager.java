package com.example.administrator.starmusic.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {


	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	private int startX;
	private int startY;


	/**
	 * 1. ���»�����Ҫ���� 2. ���һ������ҵ�ǰ�ǵ�һ��ҳ��,��Ҫ���� 3. ���󻬶����ҵ�ǰ�����һ��ҳ��,��Ҫ����
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		System.out.println("MyViewPager+dispatchTouchEvent1");
		getParent().requestDisallowInterceptTouchEvent(true);

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) ev.getX();
			startY = (int) ev.getY();
//			System.out.println("����Y�����ֵ"+startY);
			break;
		case MotionEvent.ACTION_MOVE:

			int endX = (int) ev.getX();
			int endY = (int) ev.getY();

			int dx = endX - startX;
			int dy = endY - startY;

			if (Math.abs(dy) < Math.abs(dx)) {
				int currentItem = getCurrentItem();
				// ���һ���
				if (dx > 0) {
					// ���һ�
					if (currentItem == 0) {
						// ��һ��ҳ��,��Ҫ����
//						getParent().requestDisallowInterceptTouchEvent(true);
					}
				} else {
					// ����
					int count = getAdapter().getCount();// item����
					if (currentItem == count - 1) {
						// ���һ��ҳ��,��Ҫ����
						getParent().requestDisallowInterceptTouchEvent(true);
					}
				}

			} else {
				// ���»���,��Ҫ����
//				getParent().requestDisallowInterceptTouchEvent(true);
			}

			break;

		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		Log.d("MyViewPager", "onTouchEvent ");
//		System.out.println("MyViewPager+onTouchEvent");
		return super.onTouchEvent(arg0);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		Log.d("MyViewPager", "onInterceptTouchEvent");
////		System.out.println("MyViewPager+onInterceptTouchEvent");
//		float y2 = arg0.getY();
////		return super.onInterceptTouchEvent(arg0);
////		return false;
//		int height=getHeight();
////		System.out.println("height����ͷ���ֵĸ߶�"+height);
//		if(y2>200){
//			return super.onInterceptTouchEvent(arg0);
//		}else{
//			return false;
//		}


		return super.onInterceptTouchEvent(arg0);

	}
}
