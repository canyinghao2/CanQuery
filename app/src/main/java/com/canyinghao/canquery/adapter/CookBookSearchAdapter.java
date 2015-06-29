package com.canyinghao.canquery.adapter;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canquery.adapter.NewBaseAdapter;

import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.activity.cookbook.CookBookDetailActivity;
import com.canyinghao.canquery.model.CookBookInfo.Data;
import com.canyinghao.canquery.model.CookBookInfo.Data.Steps;
import com.canyinghao.canquery.R;

public class CookBookSearchAdapter extends NewBaseAdapter {
	public CookBookSearchAdapter(Context c, List list) {
		super(c, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {
			ViewCache cache = new ViewCache();
			v = inflater
					.inflate(R.layout.query_cookbook_search_list_item, null);

			cache.tv_1 = (TextView) v.findViewById(R.id.tv_1);
			cache.tv_2 = (TextView) v.findViewById(R.id.tv_2);
			cache.tv_3 = (TextView) v.findViewById(R.id.tv_3);
			cache.iv_1 = (ImageView) v.findViewById(R.id.iv_1);

			v.setTag(cache);
		}

		ViewCache cache = (ViewCache) v.getTag();
		final Data info = (Data) list.get(position);

		String img = "";

		List<String> albums = info.getAlbums();
		if (albums.size() > 0) {
			img = albums.get(0);
		}
		AnimeUtil.getImageLoad().displayImage(img, cache.iv_1,
				AnimeUtil.getImageOption());

		cache.tv_1.setText(info.getTitle());

		String str = "用料：" + info.getIngredients() + ";" + info.getBurden();

		cache.tv_2.setText(str);
		List<Steps> stepList = info.getSteps();
		String stepStr = "";
		if (stepList != null) {
			for (int i = 0; i < stepList.size(); i++) {
				Steps steps = stepList.get(i);
				stepStr = stepStr + steps.getStep();
			}
		}
		cache.tv_3.setText(stepStr);

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {

				IntentHelper.getInstance().showIntent(context, CookBookDetailActivity.class,
						new String[] { "data" }, new Serializable[] { info });

			}
		});

		return v;
	}

	class ViewCache {

		private TextView tv_1;
		private TextView tv_2;
		private TextView tv_3;
		private ImageView iv_1;

	}
}
