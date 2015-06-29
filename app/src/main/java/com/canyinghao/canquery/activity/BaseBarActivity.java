package com.canyinghao.canquery.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.view.swipe.SwipeBackActivity;

public class BaseBarActivity extends SwipeBackActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


	}



    public void setToolbar(final Toolbar toolbar,int icon,String title,String subTitle,View.OnClickListener navigation,Toolbar.OnMenuItemClickListener menuItemClickListener){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(icon);
        toolbar.setTitle(title);
        toolbar.setSubtitle(subTitle);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(navigation);

        if (menuItemClickListener!=null){
            toolbar.setOnMenuItemClickListener(menuItemClickListener);

        }else{

            toolbar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.action_share:


                           showShare(toolbar);

                            break;
                    }


                    return true;
                }
            });

        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }







}
