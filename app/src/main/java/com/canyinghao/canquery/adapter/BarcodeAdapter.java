package com.canyinghao.canquery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.model.BarcodeInfo;

import java.util.List;

/**
 * Created by yangjian on 15/4/13.
 */
public class BarcodeAdapter extends  NewBaseAdapter {
    public BarcodeAdapter(Context c, List list) {
        super(c, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FrameLayout ll=      new FrameLayout(context);


        ll.setPadding(30,20,30,20);

        Object obj= list.get(i);

        String shopname="dd";
        String price="dd";
        if (obj instanceof BarcodeInfo.Result.Shop){

            BarcodeInfo.Result.Shop shop= (BarcodeInfo.Result.Shop) obj;
            price=   shop.getPrice();
            shopname=shop.getShopname();

        }
        if (obj instanceof BarcodeInfo.Result.Eshop){

            BarcodeInfo.Result.Eshop shop= (BarcodeInfo.Result.Eshop) obj;
            price=   shop.getPrice();
            shopname=shop.getShopname();

        }

        TextView name=   new TextView(context);
        name.setText(shopname);
        name.setTextSize(17);
        name.setTextColor(Color.WHITE);

        ll.addView(name);
        TextView tv=   new TextView(context);
        tv.setText("￥"+price+"元");
        tv.setTextSize(15);
        tv.setTextColor(context.getResources().getColor(R.color.white));

        FrameLayout.LayoutParams params1=   new   FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);

        params1.gravity= Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(params1);
        ll.addView(tv);
        return ll;
    }
}
