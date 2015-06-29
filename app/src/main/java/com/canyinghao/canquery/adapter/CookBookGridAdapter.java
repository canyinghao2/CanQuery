package com.canyinghao.canquery.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.cookbook.CookBookDetailActivity;
import com.canyinghao.canquery.model.CookBookInfo.Data;
import com.canyinghao.canquery.model.CookBookInfo.Data.Steps;
import com.canyinghao.canquery.util.AnimeUtil;

import java.io.Serializable;
import java.util.List;

public class CookBookGridAdapter extends NewBaseAdapter {
	public CookBookGridAdapter(Context c, List list) {
		super(c, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {
			ViewCache cache = new ViewCache();
			v = inflater.inflate(R.layout.query_cookbook_main_item, null);

			cache.tv_1 = (TextView) v.findViewById(R.id.tv_1);
			cache.tv_2 = (TextView) v.findViewById(R.id.tv_2);
			cache.iv_2 = (ImageView) v.findViewById(R.id.iv_2);
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
		AnimeUtil.getImageLoad().displayImage(img, cache.iv_2,
				AnimeUtil.getImageOption());

		cache.tv_1.setText(info.getTitle());
        cache.tv_1.setTextColor(context.getResources().getColor(R.color.black_text_54));
        cache.tv_2.setTextColor(context.getResources().getColor(R.color.black_text_54));

		String str = "用料：" + info.getIngredients() + ";" + info.getBurden();

		cache.tv_2.setText(str);
		List<Steps> stepList = info.getSteps();

		if (stepList.size() > 0) {
			Steps steps = stepList.get(0);
			String img2 = steps.getImg();
			AnimeUtil.getImageLoad().displayImage(img2, cache.iv_1,
					AnimeUtil.getImageOption());
		}

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

		private ImageView iv_1;
		private ImageView iv_2;

	}
}
