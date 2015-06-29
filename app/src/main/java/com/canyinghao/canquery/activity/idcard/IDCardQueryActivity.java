package com.canyinghao.canquery.activity.idcard;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.model.IDCardInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.util.JsonUtil;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class IDCardQueryActivity extends BaseBarActivity {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.tv_number)
	 TextView tv_number;
	@InjectView(R.id.tv_gender)
	 TextView tv_gender;
	@InjectView(R.id.tv_birthday)
	 TextView tv_birthday;
	@InjectView(R.id.tv_city)
	 TextView tv_city;

    @InjectView(R.id.fl_ad)
    FrameLayout fl_ad;

	@InjectView(R.id.et_search)
	 TextView et_search;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_idcard_activity);
		ButterKnife.inject(this);
        AnimeUtil.addAdFrameLayout(context, fl_ad);

        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "身份证号码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);
	}



	@OnClick({ R.id.iv_search })
	public void click(final View v) {
		AnimeHepler.getInstance().startAnimation(context, v, R.anim.small_2_big,
				new OnAnimEnd() {

					

					@Override
					public void end() {
						String text = et_search.getText().toString().trim();
						if (!TextUtils.isEmpty(text)) {
							 idcardQuery(v,text);
						
						} else {
							PhoneHelper.getInstance().show("请输入身份证号码");
						}

					}
				});

	}

	 void idcardQuery(final View v, final String cardno) {
		Parameters params = new Parameters();
		params.add("cardno", cardno);

		v.setEnabled(false);

		JuheWeb.getJuheData(params, 38, "http://apis.juhe.cn/idcard/index",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");

						String lists = map.get("list");
						if (TextUtils.isEmpty(lists)) {
							return;
						}
						final IDCardInfo info = JsonHelper.getInstance().getObject(lists,
								IDCardInfo.class);
						if (info != null) {

							tv_birthday.setText(info.getBirthday());
							tv_number.setText(cardno);
							tv_city.setText(info.getArea());
							tv_gender.setText(info.getSex());


                            setShareContent(cardno,info.getArea()+info.getBirthday()+info.getSex());

						} else {
							PhoneHelper.getInstance().show("请输入正确的身份证号");
						}

					}

					@Override
					public void requestEnd() {
						v.setEnabled(true);

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}

	
}
