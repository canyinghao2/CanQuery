package com.canyinghao.canquery.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.util.SystemBarTintManager;

import de.greenrobot.event.EventBus;

public class BaseActivity extends ActionBarActivity {
    public Activity context;


    public FrontiaSocialShare mSocialShare;

    public FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();
    public SystemBarTintManager mTintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        context = this;


        setShare();
        setShareContent(getResources().getString(R.string.app_name), "这是一款用来进行日常生活相关的查询的工具，界面美观，高效实用。它可以查询，菜谱大全、旅游景点、星座运势、电影票房、天气预报、身份证号、邮编、常用快递、火车票、航班动态等。更多功能敬请期待。");


        EventBus.getDefault().register(this);
//
//        mTintManager = new SystemBarTintManager(this);
//        mTintManager.setStatusBarTintResource(R.color.blue_gray_500);

    }

    public void onEventMainThread(Intent intent) {

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setShare() {

        mSocialShare = Frontia.getSocialShare();
        mSocialShare.setContext(this);

        String name = getResources().getString(R.string.app_name);
        mSocialShare.setClientName(FrontiaAuthorization.MediaType.SMS.toString(), name);
        mSocialShare.setClientName(FrontiaAuthorization.MediaType.WEIXIN.toString(), name);
        mSocialShare.setClientName(FrontiaAuthorization.MediaType.QQFRIEND.toString(), name);
        mSocialShare.setClientName(FrontiaAuthorization.MediaType.SINAWEIBO.toString(), name);
        mSocialShare.setClientName(FrontiaAuthorization.MediaType.QZONE.toString(), name);

        // mSocialShare.setClientId(MediaType.SINAWEIBO.toString(),
        // "568898243");
        mSocialShare.setClientId(FrontiaAuthorization.MediaType.QZONE.toString(), "1104543354");
        mSocialShare.setClientId(FrontiaAuthorization.MediaType.QQFRIEND.toString(), "1104543354");
        mSocialShare.setClientId(FrontiaAuthorization.MediaType.WEIXIN.toString(),
                "wx0a0b92224794021d");
        // mImageContent.setWXMediaObjectType(FrontiaIMediaObject.TYPE_URL);

    }

    public void setShareContent(String title, String content) {

        mImageContent.setTitle(title);

        mImageContent.setContent(content);


        mImageContent.setLinkUrl("http://canying.duapp.com");

    }


    public void showShare(View view) {


        mSocialShare.show(view, mImageContent, FrontiaSocialShare.FrontiaTheme.NIGHT, new
                FrontiaSocialShareListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFailure(int paramInt, String paramString) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onCancel() {
                        // TODO Auto-generated method stub

                    }
                });

    }
}
