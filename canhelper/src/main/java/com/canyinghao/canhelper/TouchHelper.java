package com.canyinghao.canhelper;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class TouchHelper {
	
	private static TouchHelper util;

	synchronized public static TouchHelper getInstance() {

		if (util == null) {
			util = new TouchHelper();
		}
		return util;
	}

	private TouchHelper() {
		super();
	}
	

	public boolean setTouch(View v, MotionEvent event) {

		float start = 1.0f;
		float end = 0.9f;
		Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		Animation endAnimation = new ScaleAnimation(end, start, end, start,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(200);
		scaleAnimation.setFillAfter(true);
		endAnimation.setDuration(200);
		endAnimation.setFillAfter(true);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			v.startAnimation(scaleAnimation);
			v.invalidate();
			break;
		case MotionEvent.ACTION_UP:
			v.startAnimation(endAnimation);
			v.invalidate();
			break;
		case MotionEvent.ACTION_CANCEL:
			v.startAnimation(endAnimation);
			v.invalidate();
			break;
		}
		return false;

	}

}
