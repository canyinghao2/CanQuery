package com.canyinghao.canquery.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canquery.adapter.NewBaseAdapter;
import com.canyinghao.canquery.adapter.ExpressageAdapter.ViewCache;
import com.canyinghao.canquery.model.PostcodeInfo.PostcodeListInfo;
import com.canyinghao.canquery.R;

public class PostCodeResultAdapter extends NewBaseAdapter {

	public PostCodeResultAdapter(Context c, List list) {
		super(c, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {
			ViewCache cache = new ViewCache();
			v = LayoutInflater.from(context).inflate(
					R.layout.query_postcode_result_item, null);
			cache.tv_pid = (TextView) v.findViewById(R.id.tv_pid);
			cache.tv_cid = (TextView) v.findViewById(R.id.tv_cid);
			cache.tv_did = (TextView) v.findViewById(R.id.tv_did);
			cache.tv_qid = (TextView) v.findViewById(R.id.tv_qid);
			cache.tv_post = (TextView) v.findViewById(R.id.tv_post);

			v.setTag(cache);
		}
		ViewCache cache = (ViewCache) v.getTag();

		PostcodeListInfo info = (PostcodeListInfo) list.get(position);
		cache.tv_pid.setText(info.getProvince());
		cache.tv_cid.setText(info.getCity());
		cache.tv_did.setText(info.getDistrict());
		cache.tv_qid.setText(info.getAddress());
		cache.tv_post.setText(info.getPostNumber());
		return v;
	}

	class ViewCache {

		private TextView tv_pid;
		private TextView tv_cid;
		private TextView tv_did;
		private TextView tv_qid;
		private TextView tv_post;
	}

}
