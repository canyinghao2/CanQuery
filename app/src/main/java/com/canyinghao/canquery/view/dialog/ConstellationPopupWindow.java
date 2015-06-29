package com.canyinghao.canquery.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.constellation.ConstellationActivity;
import com.canyinghao.canquery.view.textview.TextDrawable;

import java.util.Random;

import de.greenrobot.event.EventBus;

public class ConstellationPopupWindow extends PopupWindow {

	public static String[] cons = { "水瓶", "双鱼","白羊", "金牛", "双子", "巨蟹", "狮子", "处女", "天秤",
			"天蝎", "射手", "摩羯" };
	public static String[] times = { "1/20-2/18", "2/19-3/20","3/21-4/19", "4/20-5/20", "5/21-6/21",
			"6/22-7/22", "7/23-8/22", "8/23-9/22", "9/23-10/23", "10/24-11/22",
			"11/23-12/21", "12/22-1/19"};


	public static int[] rids = { R.drawable.query_star_aquarius,
            R.drawable.query_star_pisces,R.drawable.query_star_aries,
			R.drawable.query_star_taurus, R.drawable.query_star_gemini,
			R.drawable.query_star_cancer, R.drawable.query_star_leo,
			R.drawable.query_star_virgo, R.drawable.query_star_libra,
			R.drawable.query_star_scorpio, R.drawable.query_star_sagittarius,
			R.drawable.query_star_capricornus };

	public ConstellationPopupWindow(final Context context) {
		super(context);

		final LinearLayout view = new LinearLayout(context);
		final ScrollView scroll = new ScrollView(context);

		view.setOrientation(LinearLayout.VERTICAL);

		view.setGravity(Gravity.CENTER);

		scroll.addView(view);

        setAnimationStyle(R.style.down_animation);
		view.setGravity(Gravity.RIGHT);
		for (int i = 0; i < cons.length; i++) {
            Random random = new Random();

            int co = Color.rgb(random.nextInt(200) + 20,
                    random.nextInt(200) + 20,
                    random.nextInt(200) + 20);
            TextDrawable td = TextDrawable.builder()
                    .buildRound("", co);

			TextView tv1 = new TextView(context);
			tv1.setBackgroundDrawable(td);
			int dp80 = BitmapHelper.getInstance().dp2Px(80);
			int dp10 = BitmapHelper.getInstance().dp2Px(10);
			LayoutParams params = new LayoutParams(dp80, dp80);
			tv1.setLayoutParams(params);
			tv1.setPadding(dp10, dp10, dp10, dp10);
			tv1.setGravity(Gravity.CENTER);
			params.setMargins(dp10, dp10, dp10, dp10);
			tv1.setText(cons[i]+"座");
			tv1.setTextColor(context.getResources().getColor(R.color.white));
			tv1.setTextSize(15);
			tv1.setCompoundDrawablesWithIntrinsicBounds(0, rids[i], 0, 0);
			final int num=i;
			tv1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AnimeHepler.getInstance().startAnimation(context, v, R.anim.small_2_big, new OnAnimEnd() {
						
						
						
						@Override
						public void end() {
							Intent intent = new Intent(ConstellationActivity.TAG);
							intent.putExtra("num", num);
							
							EventBus.getDefault().post(intent);
							dismiss();
							
						}
					});
					
				}
			});
			view.addView(tv1);
			TextView tv2 = new TextView(context);
			tv2.setText(times[i]);
			LayoutParams params2 = new LayoutParams(dp80, LayoutParams.WRAP_CONTENT);
			params2.setMargins(dp10, 0, dp10,0);
			tv2.setGravity(Gravity.CENTER);
			tv2.setLayoutParams(params2);
			tv2.setTextSize(14);
			tv2.setTextColor(context.getResources().getColor(R.color.white));
			view.addView(tv2);

		}

	    view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
				
				
			}
		});
		setFocusable(true);
		setContentView(scroll);

		setOutsideTouchable(true);

		scroll.setBackgroundResource(R.color.half_transparent);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setBackgroundDrawable(new BitmapDrawable());
		showAtLocation(view, Gravity.CENTER, 0, 0);

	}

}
