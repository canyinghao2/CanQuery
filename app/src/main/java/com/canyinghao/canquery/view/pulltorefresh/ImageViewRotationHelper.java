package com.canyinghao.canquery.view.pulltorefresh;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageViewRotationHelper {  
    /** The imageview */  
    private final ImageView mImageView;  
    /** The matrix */  
    private Matrix mMatrix;  
    /** Pivot X */  
    private float mRotationPivotX;  
    /** Pivot Y */  
    private float mRotationPivotY;  
      
    /** 
     * The constructor method. 
     *  
     * @param imageView the image view 
     */  
    public ImageViewRotationHelper(ImageView imageView) {  
        mImageView = imageView;  
    }  
      
    /** 
     * Sets the degrees that the view is rotated around the pivot point. Increasing values 
     * result in clockwise rotation. 
     * 
     * @param rotation The degrees of rotation. 
     * 
     * @see #getRotation() 
     * @see #getPivotX() 
     * @see #getPivotY() 
     * @see #setRotationX(float) 
     * @see #setRotationY(float) 
     * 
     * @attr ref android.R.styleable#View_rotation 
     */  
    public  void setRotation(float rotation) {  
//        if (APIUtils.hasHoneycomb()) {  
//            mImageView.setRotation(rotation);  
//        } else {  
            if (null == mMatrix) {  
                mMatrix = new Matrix();  
                  
                // 计算旋转的中心点  
                Drawable imageDrawable = mImageView.getDrawable();  
                if (null != imageDrawable) {  
                    mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);  
                    mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);  
                }  
            }  
              
            mMatrix.setRotate(rotation, mRotationPivotX, mRotationPivotY);  
            mImageView.setImageMatrix(mMatrix);  
//        }  
    }  
}  
