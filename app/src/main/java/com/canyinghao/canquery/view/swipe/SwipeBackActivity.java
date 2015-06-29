package com.canyinghao.canquery.view.swipe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.activity.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SwipeBackActivity extends BaseActivity {
    public SwipeBackLayout mSwipeBackLayout;

    public boolean isSwipe = true;

    public static Drawable temptDrawble;

    private Drawable saveDrawble;


    public boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (isSwipe) {

//            getWindow().setBackgroundDrawable(
//                    new ColorDrawable(Color.TRANSPARENT));
//            getWindow().getDecorView().setBackgroundDrawable(null);
            mSwipeBackLayout = new SwipeBackLayout(this);
            // mSwipeBackLayout.setBackgroundResource(R.drawable.voip_call_bg);
            mSwipeBackLayout
                    .addSwipeListener(new SwipeBackLayout.SwipeListener() {
                        @Override
                        public void onScrollStateChange(int state,
                                                        float scrollPercent) {

                        }

                        @Override
                        public void onEdgeTouch(int edgeFlag) {

                        }

                        @Override
                        public void onScrollOverThreshold() {

                        }
                    });
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
            mSwipeBackLayout.setEnableGesture(true);
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (isSwipe) {
            mSwipeBackLayout.attachToActivity(this);

            if (temptDrawble != null && isFirst) {

                mSwipeBackLayout.setBackgroundDrawable(temptDrawble);


            }



            saveDrawble=temptDrawble;
            temptDrawble=getTemptDrawble();


        }


    }


    @Override
    protected void onDestroy() {

        if (isSwipe){

            if (saveDrawble!=null){

                temptDrawble=saveDrawble;
            }

        }



        super.onDestroy();

    }

    public  View getRootView(Activity context) {

        return ((ViewGroup) context.findViewById(android.R.id.content))
                .getChildAt(0);
    }

    public void convertActivityToTranslucent(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains(
                        "TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method method = Activity.class.getDeclaredMethod(
                    "convertToTranslucent", translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[]{null});

        } catch (Throwable t) {
        }
    }

    public void finishThis() {
        if (isSwipe) {
            convertActivityToTranslucent(this);
            mSwipeBackLayout.scrollToFinishActivity();
        }

    }


    public Drawable getTemptDrawble() {
        View rootView = getRootView(this);
//		int statusBarHeight = getSbar();
//		View st = rootView.findViewById(R.id.st_h);
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//		st.setLayoutParams(params);
//		st.setVisibility(View.VISIBLE);
        Bitmap bitmap = loadBitmapFromView(rootView);
        Drawable drawable = BitmapHelper.getInstance().bitmap2Drawable(bitmap);
//		st.setVisibility(View.GONE);
        return drawable;

    }

    public Bitmap loadBitmapFromView(View view) {
        if (view == null) {
            return null;
        }

        int w = PhoneHelper.getInstance().getScreenDisplayMetrics().widthPixels;
        int h = PhoneHelper.getInstance().getScreenDisplayMetrics().heightPixels;
        view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
        // 这个方法也非常重要，设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 生成bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        // 把view中的内容绘制在画布上
        view.draw(canvas);

        return bitmap;
    }

    private int getSbar() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = getResources().getDimensionPixelSize(x);
            return sbar;

        } catch (Exception e1) {

            e1.printStackTrace();

        }
        return sbar;

    }





}
