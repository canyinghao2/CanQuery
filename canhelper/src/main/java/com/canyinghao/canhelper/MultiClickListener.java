package com.canyinghao.canhelper;

import android.os.Handler;
import android.view.View;
/**
 * 多次点击事件
 * @author canyinghao
 *
 */
public abstract class MultiClickListener implements View.OnClickListener {
    // 点击时间间隔
    private long maxTime;
    // 已经连续 点击的次数
    private int count;
    public MultiClickListener() {
        this(250);
    }
    public MultiClickListener(long multiTouchInterval) {
        this.maxTime = multiTouchInterval;
        this.count = 0;
    }
    @Override
    public void onClick(final View v) {
        this.count++;
        if (this.count == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MultiClickListener.this.onMultiClickListener(v,
                            MultiClickListener.this.count);
                    MultiClickListener.this.count = 0;
                }
            }, MultiClickListener.this.maxTime);
        }
    }
    public abstract void onMultiClickListener(View v, int clickCount);
}
