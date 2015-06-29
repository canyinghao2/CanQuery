package com.canyinghao.canquery.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.canyinghao.canhelper.DateHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.configs.QueryConfigs;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yangjian on 15/4/13.
 */
public class AboutMeActivity extends  BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
     @InjectView(R.id.tv_version)
     TextView tv_version;
    @InjectView(R.id.tv_have)
     TextView tv_have;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aboutme_activity);

        ButterKnife.inject(this);

        tv_version.setText("版本号："+ QueryConfigs.Version);
        tv_have.setText("版权所有2015-"+ DateHelper.getInstance().getCurrentYear());


        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "关于", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);





    }


    @OnClick(R.id.tv_version)
    public void click(View v){

//        startService(new Intent(UpdateService.TAG));



    }


}
