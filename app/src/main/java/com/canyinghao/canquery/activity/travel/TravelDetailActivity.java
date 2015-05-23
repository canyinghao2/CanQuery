package com.canyinghao.canquery.activity.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.model.TravelInfo.Hotel;
import com.canyinghao.canquery.model.TravelInfo.Scenery;
import com.canyinghao.canquery.util.AnimeUtil;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TravelDetailActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;


	@InjectView(R.id.iv_1)
	 ImageView iv_1;
	@InjectView(R.id.tv_1)
	 TextView tv_1;
	@InjectView(R.id.tv_2)
	 TextView tv_2;
	@InjectView(R.id.tv_3)
	 TextView tv_3;
	@InjectView(R.id.tv_4)
	 TextView tv_4;
	@InjectView(R.id.tv_5)
	 TextView tv_5;
	@InjectView(R.id.tv_6)
	 TextView tv_6;
	@InjectView(R.id.tv_7)
	 TextView tv_7;
	@InjectView(R.id.tv_8)
	 TextView tv_8;
	@InjectView(R.id.tv_9)
	 TextView tv_9;

	 Serializable sinfo;
	 String url;

	 boolean isHotel;

	 String mStr = "";
	 String mStr2 = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_travel_detail_activity);
		ButterKnife.inject(this);


		getIntentData();


	}



	 void getIntentData() {

		String title = "";
		String grade = "";

		String price = "";
		String address = "";
		String imgurl = "";
		String intro = "";
		String manyidu = "";

		Intent intent = getIntent();
		if (intent.hasExtra("info")) {
			sinfo = intent.getSerializableExtra("info");

		}

		if (intent.hasExtra("isHotel")) {
			isHotel = intent.getBooleanExtra("isHotel", false);
		}
		if (isHotel) {
			mStr = "酒店";
			mStr2 = "房价：";
			tv_8.setVisibility(View.GONE);

			Hotel info = (Hotel) sinfo;
			title = info.getTitle();
			grade = info.getGrade();
			price = info.getPrice_min();
			address = info.getAddress();
			imgurl = info.getImgurl();
			intro = info.getIntro();
			url = info.getUrl();
			manyidu = info.getManyidu();
		} else {
			mStr = "景点";
			mStr2 = "门票：";
			tv_6.setVisibility(View.GONE);

			Scenery info = (Scenery) sinfo;
			title = info.getTitle();
			grade = info.getGrade();
			price = info.getPrice_min();
			address = info.getAddress();
			imgurl = info.getImgurl();
			intro = info.getIntro();
			url = info.getUrl();
		}


         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", title, new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);



         setShareContent(title,intro);

		tv_9.setText(mStr +"介绍：");

         if (TextUtils.isEmpty(grade)){


             grade="5";

         }

		int int1 = Integer.parseInt(grade.replace(" ",""));
		String str = "";
		for (int i = 0; i < int1; i++) {
			str = "A" + str;
		}
		Spanned html1 = Html.fromHtml(mStr + "网址：<u>" + url
				+ "</u>");
		Spanned html2 = Html.fromHtml(mStr2+ price
				+ "元");
		if (isHotel) {
			tv_2.setText(grade + "星级" + mStr);
		} else {
			tv_2.setText(str + "级" + mStr);
		}

		tv_3.setText("地址：" + address);
		tv_4.setText(html1);
		tv_5.setText(html2);
		tv_6.setText("满意度：" + manyidu);
		tv_7.setText( intro);

		AnimeUtil.getImageLoad().displayImage(imgurl, iv_1,
				AnimeUtil.getImageOption());

	}

	@OnClick({ R.id.tv_4, R.id.tv_8 })
	public void click(View v) {
		switch (v.getId()) {
		case R.id.tv_4:
			PhoneHelper.getInstance().openWeb(url);
			break;
		case R.id.tv_8:



			IntentHelper.getInstance().showIntent(context, TravelMainActivity.class,
					new String[] { "info" }, new Serializable[] { sinfo });
			break;

		}

	}

}
