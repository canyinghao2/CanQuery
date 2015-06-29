package com.canyinghao.canquery.view.alphabeticbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canquery.R;

import java.util.HashMap;

/**
 * 字母表
 */
public class QuickAlphabeticBar extends ImageView {
	private TextView mDialogText;
	private Handler mHandler;
	private ListView mList;
	private float mHight;
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	private HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();

	public QuickAlphabeticBar(Context context) {
		super(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(Activity ctx) {
		mDialogText = (TextView) ctx.findViewById(R.id.fast_position);
//		 mDialogText = new TextView(ctx);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}

	public void init(View v) {
		mDialogText = (TextView) v.findViewById(R.id.fast_position);
		// mDialogText = new TextView(ctx);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}

	public void setListView(ListView mList) {
		this.mList = mList;
		this.mList.setVerticalScrollBarEnabled(false);
	}

	public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
	}

	public void setHight(float mHight) {
		this.mHight = mHight;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int act = event.getAction();
		float y = event.getY();
		final int oldChoose = choose;
		// 计算手指位置，找到对应的段，让mList移动段开头的位置上
		
		if (mHight<10) {
			mHight=getHeight();
		}
		int selectIndex = (int) (y / (mHight / letters.length));
		
		if (selectIndex > -1 && selectIndex < letters.length) {// 防止越界
			String key = letters[selectIndex];
			if (alphaIndexer.containsKey(key)) {
				
				int pos = alphaIndexer.get(key);
				if (mList.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				} else {
					this.mList.setSelectionFromTop(pos, 0);
				}
				mDialogText.setText(letters[selectIndex]);
			} else {
				mDialogText.setText("");
			}
		}

		switch (act) {
		case MotionEvent.ACTION_DOWN:
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			choose = -1;
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.VISIBLE) {
							mDialogText.setVisibility(INVISIBLE);
						}
					}
				});
			}
			break;
		default:
			break;
		}

		return true;
	}

	Paint paint = new Paint();
	int choose = -1;

	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*
		 * if (showBkg) {//设置边栏背景色
		 * canvas.drawColor(Color.parseColor("#b20264")); }
		 */
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / letters.length;
		for (int i = 0; i < letters.length; i++) {
			paint.setColor(Color.WHITE);
			paint.setTextSize(BitmapHelper.getInstance().dp2Px(13));
			// paint.setTypeface(Typeface.DEFAULT_BOLD);//粗体
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.BLUE);// 滑动时按下的字母颜色
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(letters[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}

	}

}
