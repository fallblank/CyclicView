package me.fallblank.cyclicview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fallb on 2016/8/12.
 */
public class CyclicView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private LinearLayout mIndicatorView;
    private TextView mDescriberView;

    /*轮播View需要的数据集*/
    private List<CyclicData> mDataList;
    private CyclicViewAdapter mAdapter;
    private int mSize;

    /*图片的拉伸方式*/
    private ImageView.ScaleType mScaleType;

    /*indicator drawable*/
    private int mIndicatorSelectedResId = R.drawable.blue_circle;
    private int mIndicatorUnselectedResId = R.drawable.while_circle;

    /*indicator的imageview*/
    private ImageView[] mIndicatorImages;

    /*indicator的半径*/
    private int mIndicatorRadius = 20;
    private int mIndicatorSeparation = 2;

    /*当前现实的位置*/
    private int mPosition = 0;

    /*用户是不是在滑动*/
    private boolean mIsUserControl = false;
    private boolean mIsAutoPlay = true;

    private final Handler mHandler = new Handler();
    private long mDelayTime = 1000 * 2;
    private long mPauseDalayTime = 1000 * 4;
    private final Runnable mTask = new Runnable() {
        @Override public void run() {
            if (!mIsUserControl && mIsAutoPlay) {
                int newPosition = (mViewPager.getCurrentItem() + 1) % mSize;
                mViewPager.setCurrentItem(newPosition, true);
                mHandler.postDelayed(mTask,mDelayTime);
            } else {
                mHandler.postDelayed(mTask, mPauseDalayTime);
            }
        }
    };


    public CyclicView(Context context) {
        this(context, null);
    }


    public CyclicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CyclicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void startPlay() {
        mHandler.removeCallbacks(mTask);
        mHandler.postDelayed(mTask, mDelayTime);
    }
    public void stopPlay(){
        mHandler.removeCallbacks(mTask);
    }


    private void init() {
        //充填轮播View，并将其添加到当前对象中，相对于把view当成当前对象（FrameView）的子view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_cyclic, this, true);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mDescriberView = (TextView) view.findViewById(R.id.image_describer);
        mIndicatorView = (LinearLayout) view.findViewById(R.id.indicator);

        mDataList = new ArrayList<>();
        mAdapter = new CyclicViewAdapter(getContext(), mDataList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }


    public void setData(List<CyclicData> list) {
        stopPlay();
        mDataList.clear();
        mSize = list.size();
        mDataList.addAll(list);
        createIndicator();
        mAdapter.notifyDataSetChanged();
        startPlay();

    }


    public void setScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
    }


    private void createIndicator() {
        mIndicatorView.removeAllViews();
        mIndicatorImages = new ImageView[mSize];
        for (int i = 0; i < mSize; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 0) {
                Drawable drawable = getContext().getResources()
                    .getDrawable(R.drawable.blue_circle);
                imageView.setImageResource(R.drawable.blue_circle);
            } else {
                imageView.setImageResource(R.drawable.while_circle);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                mIndicatorRadius * 2,
                mIndicatorRadius * 2);
            params.leftMargin = mIndicatorSeparation;
            params.rightMargin = mIndicatorSeparation;
            mIndicatorImages[i] = imageView;
            mIndicatorView.addView(imageView, params);
        }
    }


    /**
     * OnPageChangeListener的实现
     * 达到以下目的：
     * 1.更改关联的mIndicatorView和mDescriberView
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override public void onPageSelected(int position) {
        CyclicData data = mDataList.get(position);
        mDescriberView.setText(data.getDescriber());
        mIndicatorImages[mPosition].setImageResource(R.drawable.while_circle);
        mIndicatorImages[position].setImageResource(R.drawable.blue_circle);
        mPosition = position;
    }


    @Override public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                mIsUserControl = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                mIsUserControl = false;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                break;
            default:
                break;
        }
    }


    public void setIndicatorRadius(int indicatorRadius) {
        mIndicatorRadius = indicatorRadius;
    }

    public void setIndicatorSeparation(int distance){
        mIndicatorSeparation = distance;
    }
}
