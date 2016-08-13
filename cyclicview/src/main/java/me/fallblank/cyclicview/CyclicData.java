package me.fallblank.cyclicview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by fallb on 2016/8/12.
 */
public class CyclicData {
    Drawable mDrawable;
    String mDescriber;


    public CyclicData(
        @NonNull Context context, @DrawableRes int drawable, @StringRes int descriper) {
        if (drawable == 0) throw new IllegalArgumentException("drawable can't be null");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable = context.getResources().getDrawable(drawable, context.getTheme());
        } else {
            mDrawable = context.getResources().getDrawable(drawable);
        }
        if (descriper != 0) {
            mDescriber = context.getResources().getString(descriper);
        }
    }


    public CyclicData(@NonNull Drawable drawable, String describer) {
        mDrawable = drawable;
        mDescriber = describer;
    }


    public Drawable getDrawable() {
        return mDrawable;
    }


    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }


    public String getDescriber() {
        return mDescriber;
    }


    public void setDescriber(String describer) {
        mDescriber = describer;
    }
}
