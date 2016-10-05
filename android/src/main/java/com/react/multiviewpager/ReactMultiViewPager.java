package com.react.multiviewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.NativeGestureUtil;

public class ReactMultiViewPager extends ViewPager {

    private class Adapter extends PagerAdapter {

        private final List<View> mViews = new ArrayList<>();
        private boolean mIsViewPagerInIntentionallyInconsistentState = false;

        void addView(View child, int index) {
            mViews.add(index, child);
            notifyDataSetChanged();
            setOffscreenPageLimit(mViews.size());
        }

        void removeViewAt(int index) {
            mViews.remove(index);
            notifyDataSetChanged();
            setOffscreenPageLimit(mViews.size());
        }

        /**
         * Replace a set of views to the ViewPager adapter and update the ViewPager
         */
        void setViews(List<View> views) {
            mViews.clear();
            mViews.addAll(views);
            notifyDataSetChanged();
            mIsViewPagerInIntentionallyInconsistentState = false;
        }

        /**
         * Remove all the views from the adapter and de-parents them from the ViewPager
         * After calling this, it is expected that notifyDataSetChanged should be called soon
         * afterwards.
         */
        void removeAllViewsFromAdapter(ViewPager pager) {
            mViews.clear();
            pager.removeAllViews();
            mIsViewPagerInIntentionallyInconsistentState = true;
        }

        View getViewAt(int index) {
            return mViews.get(index);
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public int getItemPosition(Object object) {
            // if we've removed all views, we want to return POSITION_NONE intentionally
            return mIsViewPagerInIntentionallyInconsistentState || !mViews.contains(object) ?
                    POSITION_NONE : mViews.indexOf(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view, 0, generateDefaultLayoutParams());
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class PageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mEventDispatcher.dispatchEvent(
                    new PageScrollEvent(getId(), position, positionOffset));
        }

        @Override
        public void onPageSelected(int position) {
            if (!mIsCurrentItemFromJs) {
                mEventDispatcher.dispatchEvent(
                        new PageSelectedEvent(getId(), position));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            String pageScrollState;
            switch (state) {
                case SCROLL_STATE_IDLE:
                    pageScrollState = "idle";
                    break;
                case SCROLL_STATE_DRAGGING:
                    pageScrollState = "dragging";
                    break;
                case SCROLL_STATE_SETTLING:
                    pageScrollState = "settling";
                    break;
                default:
                    throw new IllegalStateException("Unsupported pageScrollState");
            }
            mEventDispatcher.dispatchEvent(
                    new PageScrollStateChangedEvent(getId(), pageScrollState));
        }
    }

    private final EventDispatcher mEventDispatcher;
    private boolean mIsCurrentItemFromJs;
    private boolean mScrollEnabled = true;

    public ReactMultiViewPager(ReactContext reactContext) {
        super(reactContext);
        mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        mIsCurrentItemFromJs = false;
        setOnPageChangeListener(new PageChangeListener());
        setAdapter(new Adapter());
    }

    @Override
    public Adapter getAdapter() {
        return (Adapter) super.getAdapter();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mScrollEnabled) {
            return false;
        }

        if (super.onInterceptTouchEvent(ev)) {
            NativeGestureUtil.notifyNativeGestureStarted(this, ev);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mScrollEnabled) {
            return false;
        }

        return super.onTouchEvent(ev);
    }

    public void setCurrentItemFromJs(int item, boolean animated) {
        mIsCurrentItemFromJs = true;
        setCurrentItem(item, animated);
        mIsCurrentItemFromJs = false;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        mScrollEnabled = scrollEnabled;
    }

    /*package*/ void addViewToAdapter(View child, int index) {
        getAdapter().addView(child, index);
    }

    /*package*/ void removeViewFromAdapter(int index) {
        getAdapter().removeViewAt(index);
    }

    /*package*/ int getViewCountInAdapter() {
        return getAdapter().getCount();
    }

    /*package*/ View getViewFromAdapter(int index) {
        return getAdapter().getViewAt(index);
    }

    public void setViews(List<View> views) {
        getAdapter().setViews(views);
    }

    public void removeAllViewsFromAdapter() {
        getAdapter().removeAllViewsFromAdapter(this);
    }
}