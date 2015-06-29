package com.canyinghao.canquery.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

import com.canyinghao.canquery.App;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import cn.domob.android.ads.AdView;

public class AnimeUtil {


    public static ImageLoader getImageLoad() {

        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {

            // ImageLoaderConfiguration config = new
            // ImageLoaderConfiguration.Builder(
            // App.getContext())
            // .threadPriority(Thread.NORM_PRIORITY - 2)
            // .denyCacheImageMultipleSizesInMemory()
            // .memoryCache(new WeakMemoryCache())
            // .memoryCacheExtraOptions(480, 800)
            // .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
            // null)
            // .threadPoolSize(3)
            // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
            // .memoryCacheSize(2 * 1024 * 1024)
            // .memoryCacheSizePercentage(13)
            // // default
            // .discCacheSize(50 * 1024 * 1024)
            // // 缓冲大小
            // .discCacheFileCount(100)
            // // 缓冲文件数目
            // .discCacheFileNameGenerator(new Md5FileNameGenerator())
            // .tasksProcessingOrder(QueueProcessingType.LIFO).build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    App.getContext())
                    .memoryCacheExtraOptions(480, 800)
                            // default = device screen dimensions
                    .discCacheExtraOptions(480, 800, CompressFormat.PNG, 75,
                            null)

                    .threadPoolSize(3)
                            // default
                    .threadPriority(Thread.NORM_PRIORITY - 1)
                            // default
                    .tasksProcessingOrder(QueueProcessingType.FIFO)
                            // default
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024)
                    .memoryCacheSizePercentage(13) // default

                    .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                    .discCacheFileCount(50) // 缓冲文件数目
                    .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) //

                    .imageDownloader(new BaseImageDownloader(App.getContext())) //

                    .imageDecoder(new BaseImageDecoder(true)) // default
                    .defaultDisplayImageOptions(
                            DisplayImageOptions.createSimple()) // default
                    .writeDebugLogs().build();
            imageLoader.init(config);

        }

        return imageLoader;

    }

    public static DisplayImageOptions getImageOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        return options;

    }

    public static DisplayImageOptions getImageCallOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        return options;

    }

    public static DisplayImageOptions getImageRectOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true).cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(8))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        return options;

    }

    public static DisplayImageOptions getImageRoundOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true)

                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(400))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        return options;

    }

    public static DisplayImageOptions getImageViewPagerOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                        // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true).cacheOnDisc(true)

                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;

    }

    public static DisplayImageOptions getImageSimpleOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                        // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true).cacheOnDisc(true)

                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;

    }

    public static DisplayImageOptions getImageViewPagerSimpleOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                        // 设置图片加载/解码过程中错误时候显示的图片
                        // .cacheInMemory(true).cacheOnDisc(true)

                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;

    }


    public static ScaleAnimation getScaleAnime(boolean isStart) {
        float start = 1.0f;
        float end = 0.9f;
        if (!isStart) {
            start = 0.9f;
            end = 1.0f;


        }

        ScaleAnimation scaleAnimation = new ScaleAnimation(start, end, start, end, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);

        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }


    public static void addAdFrameLayout(Activity context, ViewGroup vg) {


        AdView mAdview = new AdView(context, QueryConfigs.PUBLISHER_ID, QueryConfigs.InlinePPID);
        mAdview.setKeyword("");
        mAdview.setUserGender("");
        mAdview.setUserBirthdayStr("");
        mAdview.setUserPostcode("");
        if (vg instanceof FrameLayout) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM;

            mAdview.setLayoutParams(params);

        } else {

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            mAdview.setLayoutParams(params);

        }


        vg.addView(mAdview);


    }


    public static boolean setTouchBack(View v, MotionEvent event,Activity ac) {









        switch (event.getAction()){


            case MotionEvent.ACTION_DOWN:

                float X=  event.getX();

                if (X<10){

                    v.setTag(X);

                }else{

                    v.setTag(null);
                }





                break;

            case MotionEvent.ACTION_MOVE:

                if (v.getTag()!=null){


                  float x= (float) v.getTag();


                    float tempX=  event.getX();

                    if (tempX-x>50){


                        ac.finish();

                    }


                }








                break;

            case MotionEvent.ACTION_UP:

                if (v.getTag()!=null){


                    float x= (float) v.getTag();


                    float tempX=  event.getX();

                    if (tempX-x>50){


                        ac.finish();

                    }


                }
                break;
        }





        return false;

    }









}
