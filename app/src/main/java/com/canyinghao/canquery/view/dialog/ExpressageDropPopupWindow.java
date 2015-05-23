package com.canyinghao.canquery.view.dialog;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.canyinghao.canquery.activity.expressage.ExpressageQueryActivity;
import com.canyinghao.canquery.model.ExpressageCompanyInfo;
import com.canyinghao.canquery.model.ExpressageHistroty;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;

public class ExpressageDropPopupWindow extends PopupWindow {

	public ExpressageDropPopupWindow(final Context context, final TextView v,
			final TextView v2, List list) {
		super(context);

		final LinearLayout view = new LinearLayout(context);
		final ScrollView scroll = new ScrollView(context);

		view.setOrientation(LinearLayout.VERTICAL);

		view.setGravity(Gravity.CENTER);

		scroll.addView(view);

		for (int i = 0; i < list.size(); i++) {
			final ExpressageHistroty his = (ExpressageHistroty) list.get(i);
			final String text = his.getNum();
			final View inflate = LayoutInflater.from(context).inflate(
					R.layout.query_expressage_history_item, null);
			TextView textView = (TextView) inflate
					.findViewById(R.id.tv_histroy);
			ImageView iv_fork = (ImageView) inflate.findViewById(R.id.iv_fork);

			iv_fork.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						DbUtils.create(App.getContext()).delete(
								ExpressageHistroty.class,
								WhereBuilder.b("num", "=", text));

						view.removeView(inflate);

					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			textView.setText(text);

			OnClickListener click = new OnClickListener() {

				@Override
				public void onClick(View vw) {
					v.setText(text);
					v2.setText(his.getCom());

					ExpressageQueryActivity acti = (ExpressageQueryActivity) context;

					ExpressageCompanyInfo einfo = new ExpressageCompanyInfo(
							his.getCom(), his.getNo());
//					acti.setInfo(einfo);
					acti.setCom(his.getCom());
					dismiss();
				}
			};
			textView.setOnClickListener(click);

			LayoutParams params = null;

			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);

			if (i == 0) {
				params.setMargins(1, 1, 1, 1);
			} else {
				params.setMargins(1, 0, 1, 1);
			}
			inflate.setLayoutParams(params);

			view.addView(inflate);
		}

		setFocusable(true);
		setContentView(scroll);

		setOutsideTouchable(true);

		scroll.setBackgroundResource(R.color.black_gray);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		showAsDropDown(v, 0, 0);

	}

}
