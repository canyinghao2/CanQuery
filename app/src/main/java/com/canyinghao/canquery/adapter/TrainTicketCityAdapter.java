package com.canyinghao.canquery.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.canyinghao.canhelper.ThreadHelper;
import com.canyinghao.canhelper.ThreadHelper.ThreadAsync;
import com.canyinghao.canhelper.ThreadHelper.ThreadCallBack;
import com.canyinghao.canquery.util.IAsynTask;
import com.canyinghao.canquery.model.TrainTicketCityInfo;
import com.canyinghao.canquery.adapter.NewBaseAdapter;
import com.canyinghao.canquery.view.alphabeticbar.QuickAlphabeticBar;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;

public class TrainTicketCityAdapter extends NewBaseAdapter<TrainTicketCityInfo> {
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	private Map<Integer, Boolean> hashMap;
	private QuickAlphabeticBar alpha;

	public TrainTicketCityAdapter(Context c, final List<TrainTicketCityInfo> list,
			final QuickAlphabeticBar alpha) {
		super(c, list);
		this.alpha = alpha;
		this.alphaIndexer = new HashMap<String, Integer>();
		this.sections = new String[list.size()];

		hashMap = new HashMap<Integer, Boolean>(list.size());

		alphaIndexer.clear();
		hashMap.clear();
		this.sections = new String[list.size()];
		
		ThreadHelper.getInstance().runAsync(new ThreadCallBack() {
			
			@Override
			public Object run(ThreadAsync arg0) {
				for (int k = 0; k < list.size(); k++) {
					hashMap.put(k, false);
				}

				for (int i = 0; i < list.size(); i++) {
					TrainTicketCityInfo info = list.get(i);
					String name = getAlpha(info.getSta_ename());
					if (!alphaIndexer.containsKey(name)) {
						alphaIndexer.put(name, i);
					}

				}

				Set<String> sectionLetters = alphaIndexer.keySet();
				ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
				Collections.sort(sectionList);
				sections = new String[sectionList.size()];
				sectionList.toArray(sections);
				return null;
			}
			
			@Override
			public void result(Object arg0) {
				alpha.setAlphaIndexer(alphaIndexer);
				
			}
			
			@Override
			public void progress(Integer... arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		

		

	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {

			ViewCache cache = new ViewCache();
			v = inflater.inflate(R.layout.community_location_city_item, null);

			cache.tv_city = (TextView) v.findViewById(R.id.tv_city);

			cache.sort_key = (TextView) v.findViewById(R.id.alpha);

			v.setTag(cache);
		}
		ViewCache cache = (ViewCache) v.getTag();

		final TrainTicketCityInfo info = list.get(position);

		String currentStr = getAlpha(list.get(position).getSta_ename());
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
				position - 1).getSta_ename()) : " ";

		if (!previewStr.equals(currentStr)) {
			cache.sort_key.setVisibility(View.VISIBLE);
			cache.sort_key.setText(currentStr);

		} else {
			cache.sort_key.setVisibility(View.GONE);

		}

		cache.tv_city.setText(info.getSta_name());
		cache.tv_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				try {
					TrainTicketCityInfo ttinfo = DbUtils.create(
							App.getContext()).findFirst(
							Selector.from(TrainTicketCityInfo.class)
									.where("type", "=", 1)
									.and("sta_name", "=", info.getSta_name()));

					if (ttinfo == null) {
						info.setType(1);
						try {
							DbUtils.create(App.getContext()).saveBindingId(info);
						} catch (DbException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Activity ac = (Activity) context;

				Intent intent = new Intent();
				intent.putExtra("city", info.getSta_name());
				ac.setResult(100, intent);
				ac.finish();

			}
		});
		return v;

	}

	class ViewCache {

		TextView tv_city;
		TextView sort_key;
	}

	private String getAlpha(String zhongwen) {

		if (zhongwen == null) {
			return "#";
		}
		if (zhongwen.trim().length() == 0) {
			return "#";
		}

		char c = zhongwen.trim().substring(0, 1).charAt(0);

		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}

	}

	public void setSelectedPosition(int position) {
		hashMap.put(position, !hashMap.get(position));
	}

}
