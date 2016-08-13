package me.fallblank.cyclicview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fallb on 2016/8/13.
 */
public class CyclicViewAdapter extends PagerAdapter {

    private List<CyclicData> mDataList;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_XY;
    private Context mContext;
    private ArrayList<ImageView> mViews = new ArrayList<>();


    CyclicViewAdapter(Context context, List<CyclicData> dataList) {
        mDataList = dataList;
        mContext = context;
    }


    @Override public int getCount() {
        return mDataList.size();
    }


    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = null;
        if (position < mViews.size()) {
            view = mViews.get(position);
        } else {
            Drawable drawable = null;
            drawable = mDataList.get(position).getDrawable();
            view = new ImageView(mContext);
            view.setImageDrawable(drawable);
            view.setScaleType(mScaleType);
            mViews.add(view);
        }
        container.addView(view,0);
        return view;
    }


    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }


    public void setScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
    }
}
