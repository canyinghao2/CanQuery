package com.canyinghao.canhelper;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;



/**
 * 翻转动画
 * @author canyinghao
 *
 */
public class CustomFlipAnime {
	
	
	private static CustomFlipAnime util;

	public static CustomFlipAnime getInstance() {

		if (util == null) {
			util = new CustomFlipAnime();

		}
		return util;

	}

	
	
	/**
	 * 翻转动画，且用在view初始化以后
	 * @param mContainer 
	 * @param end 结束的角度
	 * @param depthZ 深度，若是同面，深度越大翻转开始view越小，若是反面，深度越大翻转结束view越小
	 * @param time 翻转一次的时间
	 * @param stopTime 如果可翻转回来，第一次翻转后停留的时间
	 * @param mReverse 翻转后是同面还是反面
	 * @param isHorizontal 是水平翻转还是垂直翻转
	 * @param isBack 是否翻转回来
	 */
	public  void startFlipRotation(View mContainer,  float end,float depthZ,int time,int stopTime,boolean mReverse,  boolean isHorizontal,boolean isBack) {
		float start=0;
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;

		final CustomRotateAnimation rotation = new CustomRotateAnimation(start, end,
				centerX, centerY, 1800f, mReverse,isHorizontal);
		
		rotation.setDuration(time);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		if (isBack) {
			rotation.setAnimationListener(new DisplayAnimationListener(mContainer,start,end,depthZ,time,stopTime,isHorizontal,mReverse));
		}
		

		mContainer.startAnimation(rotation);
	}

	private  class DisplayAnimationListener implements Animation.AnimationListener {
		private View view;
		private float start;
		private float end;
		private int time;
		private float depthZ;
		private int stopTime;
		private boolean isHorizontal;
		private boolean mReverse;

		private DisplayAnimationListener(View view,float start,float end,float depthZ,int time,int stopTime,boolean isHorizontal,boolean mReverse) {
			this.view = view;
			this.start=start;
			this.end=end;
			this.time=time;
			this.stopTime=stopTime;
			this.depthZ=depthZ;
			this.isHorizontal=isHorizontal;
			this.mReverse=mReverse;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
		
			view.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					final float centerX = view.getWidth() / 2.0f;
					final float centerY = view.getHeight() / 2.0f;
					CustomRotateAnimation rotation;


					rotation = new CustomRotateAnimation(end, start, centerX, centerY, depthZ,
							mReverse,isHorizontal);

					rotation.setDuration(time);
					rotation.setFillAfter(true);
					rotation.setInterpolator(new DecelerateInterpolator());

					view.startAnimation(rotation);
					
				}
			}, stopTime);
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	
	
	 
}

class CustomRotateAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;
    private boolean isHorizontal;

    public CustomRotateAnimation(float fromDegrees, float toDegrees,
            float centerX, float centerY, float depthZ, boolean reverse,boolean isHorizontal) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
        this.isHorizontal=isHorizontal;
        
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
    
        if (isHorizontal) {
        	camera.rotateY(degrees);
		}else {
			camera.rotateX(degrees);
		}
        
        
        camera.getMatrix(matrix);
        camera.restore();


        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
