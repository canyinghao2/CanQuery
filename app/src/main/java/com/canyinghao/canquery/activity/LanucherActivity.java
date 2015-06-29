package com.canyinghao.canquery.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.view.keywords.KeywordsFlow;
import com.canyinghao.canquery.view.keywords.TwoSidedView;

import java.util.Random;


/**
 * 自定义FramLayout文字飞入飞出效果
 * 主页面
 *
 * @author Administrator
 */
public class LanucherActivity extends BaseActivity implements OnClickListener {

    public static final String[] keywords = {"菜谱大全", "吃货", "旅游资讯", "驴友", "星座运势", "命运", "电影票房", "明星", "天气预报", "小雨", "身份证查询", "位置",
            "邮编查询", "信件", "常用快递", "网购", "火车票查询", "回家", "航班动态", "机场"};

    private Button btnIn, btnOut;


    KeywordsFlow keywordsFlow;

    TwoSidedView tsv;

    @Override
    public void onClick(View v) {
        if (v == btnIn) {
            keywordsFlow.rubKeywords();

            feedKeywordsFlow(keywordsFlow, keywords);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        } else if (v == btnOut) {
            keywordsFlow.rubKeywords();

            feedKeywordsFlow(keywordsFlow, keywords);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
        } else if (v instanceof TextView) {
//			String keyword = ((TextView) v).getText().toString();
//			 Intent intent = new Intent();
//			 intent.setAction(Intent.ACTION_VIEW);
//			 intent.addCategory(Intent.CATEGORY_DEFAULT);
//			 intent.setData(Uri.parse("http://www.google.com.hk/#q=" +
//			 keyword));
//			 startActivity(intent);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        FrameLayout fl = new FrameLayout(context);
        fl.setBackgroundColor(Color.WHITE);
        setContentView(fl);
        keywordsFlow = new KeywordsFlow(context);
        keywordsFlow.setDuration(2000);
        keywordsFlow.setOnItemClickListener(this);

        fl.addView(keywordsFlow);


        ImageView img1 = new ImageView(context);
        img1.setImageResource(R.drawable.canquery1);
        ImageView img2 = new ImageView(context);
        img2.setImageResource(R.drawable.canquery1_);
        tsv = new TwoSidedView(context, img1, img2, 1000);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.CENTER;
        tsv.setLayoutParams(params);
        fl.addView(tsv);


        // 添加
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        post();


        AnimeHepler.getInstance().startAnimation(context, tsv, R.anim.in_translate_top, new AnimeHepler.OnAnimEnd() {
            @Override
            public void end() {


                tsv.goToBackView();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        float width = PhoneHelper.getInstance().getScreenDisplayMetrics().widthPixels / 2 + 100;
                        TranslateAnimation moveAnimation = new TranslateAnimation(0, width, 0, 0);
                        moveAnimation.setDuration(500);
                        moveAnimation.setFillAfter(true);

                        tsv.startAnimation(moveAnimation);
                        moveAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (!isFinishing()) {


                                    IntentHelper.getInstance().showIntent(context, MainActivity.class);
                                    finish();
                                }


                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 1000);


            }
        });


    }


    private void post() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                keywordsFlow.rubKeywords();

                feedKeywordsFlow(keywordsFlow, keywords);
                keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
                if (!isFinishing()) {
                    post();
                }


            }
        }, 2100);

    }


    private void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }
}
