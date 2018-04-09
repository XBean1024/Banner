package com.binny.banner;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.viewpager.R;

import java.util.ArrayList;

/**
 * 作者: binny
 * 时间: 2018/4/9
 * 描述: 自定义轮播图
 */
public class BannerPager extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private TextView mTitleTV;
    private LinearLayout mIndicatorLl;

    private ArrayList<ImageView> mImageViewsList;//要展示的图片集合
    private String[] mTitles;
    private Context mContext;
    private int mTotalSize;

    private Handler mHandler;
    private Runnable r;//要周期性的任务 释放掉

    public BannerPager(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.banner, this, true);
        mViewPager = findViewById(R.id.banner_vp);
        mTitleTV = findViewById(R.id.banne_title);
        mIndicatorLl = findViewById(R.id.banner_indicator);
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 设置banner 标题
     *
     * @param args 标题集合
     * @return banner
     */
    public BannerPager setTitles(String[] args) {
        if (args.length <= 0) {
            return this;
        }
        mTitles = args;
        mTitleTV.setVisibility(VISIBLE);
        mTitleTV.setText(mTitles[0]);
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param imageResourceIds 图片资源 在drawable 目录下的
     * @return banner
     */
    public BannerPager setImageResource(int[] imageResourceIds) {
        if (imageResourceIds.length <= 0) {
            return this;
        }
        mImageViewsList = new ArrayList<>();
        for (int imageResourceId : imageResourceIds) {
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(imageResourceId);
            mImageViewsList.add(imageView);
        }
        return this;
    }

    public BannerPager setAutoScroll(final long delayMillis) {
        mHandler = new Handler(mContext.getMainLooper());
        r = new Runnable() {
            @Override
            public void run() {
                int item = mViewPager.getCurrentItem() + 1;
                mViewPager.setCurrentItem(item);//模仿滑动
                mHandler.postDelayed(r, delayMillis);
            }
        };
        mHandler.postDelayed(r, delayMillis);
        return this;
    }

    public void setAdapter() {
        mTotalSize = mImageViewsList.size();
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new BannerAdapter());
        //设置中间位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mTotalSize;//要保证imageViews的整数倍
        mViewPager.setCurrentItem(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int realPosition = position % mTotalSize;
        mTitleTV.setText(mTitles[realPosition]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int pos = position % mTotalSize;
            ImageView imageView = mImageViewsList.get(pos);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 释放掉 那个周期性任务，以免内存泄漏
     */
    public void release() {
        mHandler.removeCallbacks(r);
    }
}
