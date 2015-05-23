package com.canyinghao.canhelper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 点击、选中效果的工具类
 * @author canyinghao
 *
 */
public class SelectorHepler {
	private static SelectorHepler util;

	synchronized public static SelectorHepler getInstance() {

		if (util == null) {
			util = new SelectorHepler();

		}
		return util;

	}

	private SelectorHepler() {
		super();
	}

	/**
	 * 传入Drawable的id，得到一个Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param context
	 * @param idNormal
	 * @param idPressed
	 * @param idFocused
	 * @param idUnable
	 * @return
	 */
	public StateListDrawable getSelectorDrawable(Context context, int idNormal,
			int idPressed, int idFocused, int idUnable) {
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = idNormal == -1 ? null : context.getResources()
				.getDrawable(idNormal);
		Drawable pressed = idPressed == -1 ? null : context.getResources()
				.getDrawable(idPressed);
		Drawable focused = idFocused == -1 ? null : context.getResources()
				.getDrawable(idFocused);
		Drawable unable = idUnable == -1 ? null : context.getResources()
				.getDrawable(idUnable);
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, focused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 得到点击改变状态的Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param context
	 * @param idNormal
	 * @param idPressed
	 * @return
	 */
	public StateListDrawable getPressedSelectorDrawable(Context context,
			int idNormal, int idPressed) {

		Drawable normal = idNormal == -1 ? null : context.getResources()
				.getDrawable(idNormal);
		Drawable pressed = idPressed == -1 ? null : context.getResources()
				.getDrawable(idPressed);

		StateListDrawable bg = getPressedSelectorDrawable(normal, pressed);
		return bg;
	}

	/**
	 * 得到点击改变状态的Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param normal
	 * @param pressed
	 * @return
	 */
	public StateListDrawable getPressedSelectorDrawable(Drawable normal,
			Drawable pressed) {
		StateListDrawable bg = new StateListDrawable();

		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressed);

		bg.addState(new int[] { android.R.attr.state_enabled }, normal);

		bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 得到选中改变状态的Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param context
	 * @param idNormal
	 * @param idchecked
	 * @return
	 */
	public StateListDrawable getCheckedSelectorDrawable(Context context,
			int idNormal, int idchecked) {

		Drawable normal = idNormal == -1 ? null : context.getResources()
				.getDrawable(idNormal);
		Drawable checked = idchecked == -1 ? null : context.getResources()
				.getDrawable(idchecked);

		StateListDrawable bg = getCheckedSelectorDrawable(normal, checked);
		return bg;
	}

	/**
	 * 得到选中改变状态的Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param normal
	 * @param checked
	 * @return
	 */
	public StateListDrawable getCheckedSelectorDrawable(Drawable normal,
			Drawable checked) {
		StateListDrawable bg = new StateListDrawable();

		bg.addState(new int[] { android.R.attr.state_checked,
				android.R.attr.state_enabled }, checked);

		bg.addState(new int[] { android.R.attr.state_enabled }, normal);

		bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 得到焦点改变即改变状态的Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param context
	 * @param idNormal
	 * @param idchecked
	 * @return
	 */
	public StateListDrawable getFocusedSelectorDrawable(Context context,
			int idNormal, int idchecked) {

		Drawable normal = idNormal == -1 ? null : context.getResources()
				.getDrawable(idNormal);
		Drawable checked = idchecked == -1 ? null : context.getResources()
				.getDrawable(idchecked);

		StateListDrawable bg = getFocusedSelectorDrawable(normal, checked);
		return bg;
	}

	/**
	 * 得到焦点改变即改变状态的Selector,一般给setBackgroundDrawable使用
	 * 
	 * @param normal
	 * @param focused
	 * @return
	 */
	public StateListDrawable getFocusedSelectorDrawable(Drawable normal,
			Drawable focused) {
		StateListDrawable bg = new StateListDrawable();

		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, focused);
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);

		bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 得到可以改变状态的Selector,一般给setTextColor使用
	 * 
	 * @param normal
	 * @param pressed
	 * @param focused
	 * @param unable
	 * @param checked
	 * @return
	 */
	public ColorStateList getColorStateList(int normal, int pressed,
			int focused, int unable, int checked) {
		int[] colors = new int[] { pressed, focused, normal, focused, unable,
				checked, normal };
		int[][] states = new int[7][];
		states[0] = new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused };
		states[2] = new int[] { android.R.attr.state_enabled };
		states[3] = new int[] { android.R.attr.state_focused };
		states[4] = new int[] { android.R.attr.state_window_focused };
		states[5] = new int[] { android.R.attr.state_checked,
				android.R.attr.state_enabled };
		states[6] = new int[] {};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}

	/**
	 * 得到点击改变状态的Selector,一般给setTextColor使用
	 * 
	 * @param normal
	 * @param pressed
	 * @return
	 */
	public ColorStateList getPressedSelectorColor(int normal, int pressed) {
		int[] colors = new int[] { pressed, normal, normal };
		int[][] states = new int[3][];
		states[0] = new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.state_enabled };
		states[2] = new int[] {};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}

	/**
	 * 得到选中改变状态的Selector,一般给setTextColor使用
	 * 
	 * @param normal
	 * @param pressed
	 * @return
	 */
	public ColorStateList getCheckedSelectorColor(int normal, int pressed) {
		int[] colors = new int[] { pressed, normal, normal };
		int[][] states = new int[3][];
		states[0] = new int[] { android.R.attr.state_checked,
				android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.state_enabled };
		states[2] = new int[] {};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}

	/**
	 * 将多张图片合并生成一个Drawable
	 * 
	 * @param down
	 * @param up
	 * @return
	 */
	public LayerDrawable getLayerDrawable(Drawable down, Drawable up) {
		Drawable[] layerList = { new InsetDrawable(down, 0, 0, 0, 0),
				new InsetDrawable(up, 0, 0, 0, 0) };

		return new LayerDrawable(layerList);
	}

	/**
	 * 将多张图片生成一个连续播放的Drawable动画
	 * 
	 * @param time
	 * @param dr
	 * @return
	 */
	public AnimationDrawable getAnimationDrawable(int time, Drawable... dr) {
		AnimationDrawable animationDrawable = new AnimationDrawable();
		for (int i = 0; i < dr.length; i++) {
			animationDrawable.addFrame(dr[i], time);
		}
		animationDrawable.setOneShot(false);
		animationDrawable.start();
		return animationDrawable;
	}

}
