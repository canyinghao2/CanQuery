package com.canyinghao.canquery.view.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.BarcodeAdapter;
import com.canyinghao.canquery.model.BarcodeInfo;
import com.canyinghao.canquery.model.BarcodeInfo.Result.Summary;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.AnimeUtil;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BarcodeActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.iv)
	ImageView iv;
	@InjectView(R.id.tv_name)
	TextView tv_name;
	@InjectView(R.id.tv_code)
	TextView tv_code;
	@InjectView(R.id.tv_interval)
	TextView tv_interval;
	@InjectView(R.id.listview)
    ListView listview;


    private List list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_barcode_activity);
		ButterKnife.inject(this);
		setView();
		getIntentData();
        list=new ArrayList();

	}

	private void setView() {


        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "条码查询", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);

	}

	private void getIntentData() {
		Intent intent = getIntent();
		if (intent.hasExtra("barcode")) {
			String barcode = intent.getStringExtra("barcode");
			query("", barcode);
		}

	}

	void query(String cityid, String barcode) {
		if (TextUtils.isEmpty(cityid)) {
			cityid = "340";
		}
		Parameters params = new Parameters();
		params.add("cityid", cityid);
		params.add("barcode", barcode);
		params.add("pkg", "com.canyinghao.canquery");
		JuheWeb.getJuheData(params, 52, "http://api.juheapi.com/jhbar/bar",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {

						LogHelper.logi(result);
						BarcodeInfo info = JsonHelper.getInstance().getObject(
								result, BarcodeInfo.class);

                        if (info!=null&&info.getError_code()!=0){

                            PhoneHelper.getInstance().show(info.getReason());
                        }
						
						
						if (info!=null&&info.getResult()!=null&&info.getResult().getSummary()!=null) {
							Summary su=info.getResult().getSummary();
							tv_name.setText(su.getName());
							tv_code.setText(su.getBarcode());
                            tv_interval.setText(su.getInterval());

                            setShareContent(su.getName(),su.getBarcode()+su.getInterval());
                            if (!TextUtils.isEmpty(su.getImgurl())){
                                AnimeUtil.getImageLoad().displayImage(su.getImgurl(),iv);

                            }else{

                                iv.setImageResource(R.drawable.empty_cry);
                            }


                           List elist= info.getResult().getEshop();
                            List slist=info.getResult().getShop();
                            if (elist!=null){

                                list.addAll(elist);


                            }

                            if (slist!=null){

                               list.addAll(slist);
                            }


                            BarcodeAdapter adapter=  new BarcodeAdapter(context,list);


                            listview.setAdapter(adapter);


						}


						
						
						
						
						
						
						

					}

					@Override
					public void requestEnd() {

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}




}
