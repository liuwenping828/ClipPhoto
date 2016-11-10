package com.lwp.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;


public class ClipImageLayout extends RelativeLayout {

    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    /**
     * 设置水平边距 自定属性 app:horizontalPadding="0.5dp"
     */
    private int mHorizontalPadding;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ClipImageLayout);

        mHorizontalPadding = a.getDimensionPixelOffset(R.styleable.ClipImageLayout_horizontalPadding, 0);

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);


        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());

        mZoomImageView.setHorizontalPadding(mHorizontalPadding);

        mClipImageView.setHorizontalPadding(mHorizontalPadding);

    }

    /**
     * 对外公布设置边距的方法,单位为dp
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    /**
     * 裁切图片
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }

    // 设置裁剪矩形的宽高
    public void setClipRectSize(int width, int height) {
        // 设置裁剪矩形的高度
        mZoomImageView.clipRectHeight(height);

        mClipImageView.setClipRectSize(width, height);
    }


    public void setImageBitmap(Bitmap bm) {
        mZoomImageView.setImageBitmap(bm);
    }

    public void setImageDrawable(Drawable d) {
        mZoomImageView.setImageDrawable(d);
    }


}
