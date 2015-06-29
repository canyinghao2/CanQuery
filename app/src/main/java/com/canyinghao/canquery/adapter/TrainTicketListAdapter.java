package com.canyinghao.canquery.adapter;

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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.canyinghao.canhelper.StringHelper;
import com.canyinghao.canquery.model.TrainTicketCityInfo;
import com.canyinghao.canquery.model.TrainTicketInfo;
import com.canyinghao.canquery.adapter.NewBaseAdapter;
import com.canyinghao.canquery.view.alphabeticbar.QuickAlphabeticBar;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.R.color;

public class TrainTicketListAdapter extends NewBaseAdapter {

	private String[] types = { "高软：", "软卧：", "软座：", "特等座：", "商务座：", "一等座：",
			"二等座：", "硬卧：", "硬座：", "无座：", "其他：", };

	private String[] tt = new String[] {  "高铁", "动车", "特快", "直达", "快速",
	"其他" };
private String[] ttCodes = new String[] { "G", "D", "T", "Z", "K", "Q" };
	public TrainTicketListAdapter(Context c, List list) {
		super(c, list);

	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {

			ViewCache cache = new ViewCache();
			v = inflater.inflate(R.layout.query_trainticket_list_item,
					null);

			cache.tv_type_name = (TextView) v.findViewById(R.id.tv_type_name);
			cache.tv_type = (TextView) v.findViewById(R.id.tv_type);
			cache.tv_start_time = (TextView) v.findViewById(R.id.tv_start_time);
			cache.tv_from_station = (TextView) v
					.findViewById(R.id.tv_from_station);
			cache.tv_arrive_time = (TextView) v
					.findViewById(R.id.tv_arrive_time);
			cache.tv_to_station = (TextView) v.findViewById(R.id.tv_to_station);
			cache.tv_lishi = (TextView) v.findViewById(R.id.tv_lishi);
			cache.csg_text = (GridView) v.findViewById(R.id.csg_text);

			v.setTag(cache);
		}
		ViewCache cache = (ViewCache) v.getTag();

		TrainTicketInfo info = (TrainTicketInfo) list.get(position);
		
		String train_no = info.getTrain_no();
		cache.tv_type.setText(train_no);
		
		String type_name="";
		for (int i = 0; i < ttCodes.length; i++) {
			if (train_no.contains(ttCodes[i])) {
				type_name=tt[i];
				break;
			}
		}
		cache.tv_type_name.setText(type_name);
		cache.tv_start_time.setText(info.getStart_time());
		cache.tv_from_station.setText(info.getFrom_station_name());
		cache.tv_arrive_time.setText(info.getArrive_time());
		cache.tv_to_station.setText(info.getTo_station_name());
		cache.tv_lishi.setText("耗时"+info.getLishi());
		ArrayList<String> arrayList = new ArrayList<String>();
		String gr_num = info.getGr_num();
		String rw_num = info.getRw_num();
		String rz_num = info.getRz_num();
		String tz_num = info.getTz_num();
		String swz_num = info.getSwz_num();
		String zy_num = info.getZy_num();
		String ze_num = info.getZe_num();
		String yw_num = info.getYw_num();
		String yz_num = info.getYz_num();
		String wz_num = info.getWz_num();
		String qt_num = info.getQt_num();

		getTypeString(arrayList, info, gr_num, rw_num, rz_num, tz_num, swz_num,
				zy_num, ze_num, yw_num, yz_num, wz_num, qt_num);

		if (arrayList.size()>0) {
			cache.csg_text.setVisibility(View.VISIBLE);
		}else {
			cache.csg_text.setVisibility(View.GONE);
		}
		GridAdapter gridAdapter = new GridAdapter(context, arrayList);
		cache.csg_text.setAdapter(gridAdapter);

		return v;

	}

	private void getTypeString(List list, TrainTicketInfo info, String... str) {

		for (int i = 0; i < str.length; i++) {
			String num = str[i];
			if (!TextUtils.isEmpty(num)&&!num.equals("--")) {
				String text = "";
				if (StringHelper.getInstance().isInt(num)) {
					text = "张";
				}
				list.add(types[i] + num + text);
			}
		}

	}

	class ViewCache {

		TextView tv_type_name;
		TextView tv_type;
		TextView tv_start_time;
		TextView tv_from_station;
		TextView tv_arrive_time;
		TextView tv_to_station;
		TextView tv_lishi;
		GridView csg_text;
	}

	class GridAdapter extends NewBaseAdapter {

		public GridAdapter(Context c, List list) {
			super(c, list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {

			TextView tv = new TextView(context);
//			tv.setGravity(Gravity.CENTER);
			tv.setPadding(25, 5, 5, 5);
			tv.setTextColor(context.getResources().getColor(R.color.black_gray));
			tv.setTextSize(12);
			String str = (String) list.get(position);

			SpannableStringBuilder builder = new SpannableStringBuilder(str);

			ForegroundColorSpan redSpan = new ForegroundColorSpan(context
					.getResources().getColor(R.color.voip_blue_top));
			int indexOf = str.indexOf("：")+1;

			builder.setSpan(redSpan, indexOf, str.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv.setText(builder);

			return tv;
		}

	}

}
