package com.canyinghao.canhelper;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 可点击后退的TextView
 * @author canyinghao
 *
 */
public class TextViewBack extends TextView {
    public TextViewBack(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (context instanceof Activity) {
                    Activity act = (Activity) context;

                    act.finish();
                }

            }
        });
    }
}
