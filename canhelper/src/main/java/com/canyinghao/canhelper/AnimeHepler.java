package com.canyinghao.canhelper;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 * 动画工具类
 * @author canyinghao
 *
 */
public class AnimeHepler {

	private static AnimeHepler util;

	synchronized public static AnimeHepler getInstance() {

		if (util == null) {
			util = new AnimeHepler();

		}
		return util;

	}

	private AnimeHepler() {
		super();
	}

	/**
	 * 
	 * 给listView加一个刷新动画的EmptyView listview外层最好用FrameLayout单独包着
	 * 
	 * @param context
	 * @param lv
	 * @param is
	 */
	public void setAnimationEmptyView(Context context, AbsListView lv,
			ProgressBar bar, String str) {
		ViewGroup parentView = (ViewGroup) lv.getParent();

		removeItem(parentView, lv, -10321);
		removeItem(parentView, lv, -10322);

		LinearLayout wait = getProgressView(context, bar, str);

		parentView.addView(wait);
		lv.setTag(-10322, wait);
		lv.setEmptyView(wait);

	}

	/**
	 * 给listView加一个数据为空时EmptyView listview外层最好用FrameLayout单独包着
	 * 
	 * @param context
	 * @param lv
	 * @param rid
	 * @param message
	 * @param listener
	 */
	public void setNoDataEmptyView(Context context, AbsListView lv,
			Integer rid, String message, OnClickListener listener) {
		ViewGroup parentView = (ViewGroup) lv.getParent();

		removeItem(parentView, lv, -10321);
		removeItem(parentView, lv, -10322);

		LinearLayout inflate = getEmptyView(context, rid, message, listener);
		parentView.addView(inflate);
		lv.setEmptyView(inflate);
		lv.setTag(-10321, inflate);

	}

	/**
	 * 删除上一个EmptyView
	 * 
	 * @param parentView
	 * @param lv
	 * @param index
	 */
	private void removeItem(ViewGroup parentView, AbsListView lv, int index) {
		Object tag = lv.getTag(index);
		if (tag != null && tag instanceof View) {
			View s = (View) tag;
			parentView.removeView(s);
			lv.setTag(index, null);
		}

	}

	/**
	 * 得到一个刷新动画的EmptyView
	 * 
	 * @param context
	 * @param bar
	 * @param str
	 * @return
	 */
	private LinearLayout getProgressView(Context context, ProgressBar bar,
			String str) {

		LinearLayout ll1 = new LinearLayout(context);
		ll1.setOrientation(LinearLayout.VERTICAL);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ll1.setGravity(Gravity.CENTER);
		ll1.setLayoutParams(params);
		if (bar == null) {
			bar = new ProgressBar(context);
		}

		TextView textView = new TextView(context);

		textView.setText(str);
		textView.setGravity(Gravity.CENTER);

		ll1.addView(bar);
		ll1.addView(textView);
		return ll1;

	}

	/**
	 * 得到一个数据为空时的EmptyView
	 * 
	 * @param context
	 * @param rid
	 * @param str
	 * @param click
	 * @return
	 */
	private LinearLayout getEmptyView(Context context, Integer rid, String str,
			OnClickListener click) {

		LinearLayout ll2 = new LinearLayout(context);
		ll2.setOrientation(LinearLayout.VERTICAL);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ll2.setGravity(Gravity.CENTER);
		ll2.setLayoutParams(params);
		if (click != null) {
			ll2.setOnClickListener(click);
		}

		if (rid != null) {

			ImageView imageView = new ImageView(context);
			imageView.setImageResource(rid);
			ll2.addView(imageView);
		}
		TextView textView = new TextView(context);
		textView.setText(str);
		textView.setGravity(Gravity.CENTER);

		ll2.addView(textView);

		return ll2;

	}

	/**
	 * 给view设置动画效果，并设置动画结束后回调
	 * 
	 * @param context
	 * @param v
	 * @param animationId
	 * @param ae
	 */
	public  void startAnimation(Context context, View v, int animationId,
			final OnAnimEnd ae) {
		startAnimation(context, v, animationId, null, ae);
	}

	/**
	 * 给view设置动画效果，并设置动画结束后回调
	 * 
	 * @param context
	 * @param v
	 * @param anim
	 * @param ae
	 */
	public  void startAnimation(Context context, View v, Animation anim,
			final OnAnimEnd ae) {
		startAnimation(context, v, 0, anim, ae);
	}

	/**
	 * 给view设置动画效果，并设置动画结束后回调
	 * 
	 * @param context
	 * @param v
	 * @param animationId
	 * @param ae
	 */
	private  void startAnimation(Context context, View v,
			int animationId, Animation anim, final OnAnimEnd ae) {
		if (anim == null) {
			anim = AnimationUtils.loadAnimation(context, animationId);
		}
		v.startAnimation(anim);

		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				if (ae != null) {
					ae.end();
				}

			}
		});

	}

	public interface OnAnimEnd {

		void end();
	}

	/**
	 * 将ImageView的图片变为灰色，0灰色，1彩色
	 * 
	 * @param iv
	 * @param f
	 */
	public void setImageViewGray(ImageView iv, float f) {
		try {
			ColorMatrix matrix = new ColorMatrix();
			matrix.setSaturation(f);
			ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
			iv.setColorFilter(filter);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * ImageView开始播放动画
	 * 
	 * @param iv
	 */
	public void startImageAnimation(ImageView iv) {
		iv.setVisibility(View.VISIBLE);
		AnimationDrawable anim = (AnimationDrawable) iv.getDrawable();
		anim.start();

	}

	/**
	 * 旋转动画，旋转后保留最后的状态
	 * 
	 * @param rotate
	 * @return
	 */
	public RotateAnimation animeRotateAnimation(int rotate) {

		RotateAnimation animation = new RotateAnimation(0, rotate);
		animation.setFillAfter(true);
		animation.setInterpolator(new LinearInterpolator());
		return animation;
	}

	/**
	 * 放大且逐渐透明
	 * 
	 * @return
	 */
	public AnimationSet animeScale2Alpha() {
		ScaleAnimation sa = new ScaleAnimation(0, 2, 0, 2,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation aa = new AlphaAnimation(1, 0);
		sa.setDuration(1000);
		sa.setRepeatMode(Animation.RESTART);
		sa.setRepeatCount(Integer.MAX_VALUE);
		aa.setDuration(1000);
		// aa.setRepeatMode(Animation.RESTART);
		// aa.setRepeatCount(Integer.MAX_VALUE);
		final AnimationSet set = new AnimationSet(true);
		set.addAnimation(sa);
		set.addAnimation(aa);
		return set;

	}

	/**
	 * 一个移动动画
	 * 
	 * @return
	 */
	public TranslateAnimation animDefaultMove() {
		TranslateAnimation moveAnimation = new TranslateAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0.5f);
		moveAnimation.setDuration(1000);
		moveAnimation.setFillAfter(true);
		// moveAnimation.setRepeatMode(Animation.RESTART);
		// moveAnimation.setRepeatCount(Integer.MAX_VALUE);
		return moveAnimation;

	}

	/**
	 * 点击后先缩小再放大
	 * 
	 * @return
	 */
	public AnimationSet animSmall2Big() {

		AlphaAnimation alpha = new AlphaAnimation(0.5f, 1);
		alpha.setDuration(200);
		alpha.setInterpolator(new AccelerateInterpolator());

		ScaleAnimation scale1 = new ScaleAnimation(0.6f, 1.2f, 0.6f, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale1.setDuration(200);
		scale1.setInterpolator(new AccelerateInterpolator());
		ScaleAnimation scale2 = new ScaleAnimation(1.2f, 0.8f, 1.2f, 0.8f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale2.setDuration(300);
		scale2.setInterpolator(new DecelerateInterpolator());
		ScaleAnimation scale3 = new ScaleAnimation(0.8f, 1f, 0.8f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale3.setDuration(200);
		scale3.setInterpolator(new DecelerateInterpolator());

		AnimationSet set = new AnimationSet(true);
		set.addAnimation(alpha);
		set.addAnimation(scale1);
		set.addAnimation(scale2);
		set.addAnimation(scale3);
		return set;

	}

}
