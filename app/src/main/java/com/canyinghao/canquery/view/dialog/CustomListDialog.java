package com.canyinghao.canquery.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.adapter.NewBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomListDialog extends Dialog {

	private String title;

	private Context context;
	private String strs[];


	private OnItemClick itemClick;

	public CustomListDialog(final Context context, String title, String[] strs,
			OnItemClick itemClick) {
		super(context, R.style.CustomDialog);

		this.context = context;
		this.strs = strs;
		this.title = title;
		this.itemClick = itemClick;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_custom_listview_dialog);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		setCanceledOnTouchOutside(true);

		TextView tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
		ListView listview = (ListView) findViewById(R.id.listview);

		tv_dialog_title.setText(title);

		List list = new ArrayList();
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}
		ListAdapter adapter = new ListAdapter(context, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (itemClick != null) {
					itemClick.itemClick(arg0, arg1, arg2, arg3);

				}
				dismiss();
			}
		});

	}

	class ListAdapter extends NewBaseAdapter {

		public ListAdapter(Context c, List list) {
			super(c, list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int postion, View arg1, ViewGroup arg2) {

			LinearLayout ll = new LinearLayout(context);

			TextView tv = new TextView(context);
			int _5dp = BitmapHelper.getInstance().dp2Px(15);
			tv.setPadding(_5dp, _5dp, _5dp, _5dp);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 1, 0, 0);
			tv.setGravity(Gravity.CENTER);
			tv.setLayoutParams(params);
			String str = (String) list.get(postion);
			tv.setText(str);
			tv.setTextColor(context.getResources().getColor(R.color.blue_gray_700));
			tv.setBackgroundResource(R.drawable.community_white_blue_selector);
			tv.setTextSize(15);
			ll.addView(tv);

			return ll;
		}

	}

	public interface OnItemClick {

		void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3);

	}

}
