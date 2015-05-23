package com.canyinghao.canquery.activity.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.model.CookBookInfo.Data;
import com.canyinghao.canquery.model.CookBookInfo.Data.Steps;
import com.canyinghao.canquery.util.AnimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CookBookDetailActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.tv_tag)
	 TextView tv_tag;
	@InjectView(R.id.tv_imtro)
	 TextView tv_imtro;
	@InjectView(R.id.ll_ingredients)
	 LinearLayout ll_ingredients;
	@InjectView(R.id.ll_steps)
	 LinearLayout ll_steps;
	@InjectView(R.id.iv)
	 ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_cookbook_detail_activity);
		ButterKnife.inject(this);



		getIntentData();
	}

	 void getIntentData() {

		Intent intent = getIntent();
		if (intent.hasExtra("data")) {

			Data data = (Data) intent.getSerializableExtra("data");
			initView(data);
		}

	}

	 void initView(Data data) {


         setToolbar(toolbar, R.drawable.ic_arrow_back_white, data.getTitle(), data.getTitle(), new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);
		List<String> albums = data.getAlbums();
		if (albums.size() > 0) {
			AnimeUtil.getImageLoad().displayImage(albums.get(0), iv,
					AnimeUtil.getImageSimpleOption());
		}




		tv_tag.setText("标签：" + data.getTags());
		tv_imtro.setText("介绍：" +data.getImtro());


         setShareContent(data.getTitle(),data.getImtro());
		String burden = data.getIngredients() + ";" + data.getBurden();

		String[] split = burden.split(";");

		for (int i = 0; i < split.length; i++) {
			LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			String str = split[i];
			String[] strs = str.split(",");
			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			TextView tv_1 = new TextView(context);
			TextView tv_2 = new TextView(context);
			tv_1.setTextColor(context.getResources()
					.getColor(R.color.white));
			tv_2.setTextColor(context.getResources()
					.getColor(R.color.white));
			tv_2.setGravity(Gravity.RIGHT);
			tv_2.setLayoutParams(params2);
			ll.addView(tv_1);
			ll.addView(tv_2);
			ll.setPadding(5, 5, 5, 5);
			params2.setMargins(0, 0, 0, 1);
			ll.setLayoutParams(params2);


			ll_ingredients.addView(ll);
			if (strs.length >= 2) {
				tv_1.setText(strs[0]);
				tv_2.setText(strs[1]);
			}

		}

		List<Steps> sList = data.getSteps();
		if (sList == null) {
			return;
		}
		for (int i = 0; i < sList.size(); i++) {
			Steps steps = sList.get(i);
			LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);
			TextView tv_1 = new TextView(context);
			ImageView iv_1 = new ImageView(context);
			tv_1.setTextColor(context.getResources()
					.getColor(R.color.white));
			iv_1.setLayoutParams(params2);
			ll.addView(tv_1);
			ll.addView(iv_1);

			tv_1.setText(steps.getStep());
			AnimeUtil.getImageLoad().displayImage(steps.getImg(), iv_1,
					AnimeUtil.getImageSimpleOption());
			ll.setLayoutParams(params2);
			ll_steps.addView(ll);

		}

	}

}
