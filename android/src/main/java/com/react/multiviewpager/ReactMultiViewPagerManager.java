package com.react.multiviewpager;

import java.util.Map;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nullable;

public class ReactMultiViewPagerManager extends ViewGroupManager<ReactMultiViewPager> {

    private static final String REACT_CLASS = "ReactMultiViewPager";

    public static final int COMMAND_SET_PAGE = 1;
    public static final int COMMAND_SET_PAGE_WITHOUT_ANIMATION = 2;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactMultiViewPager createViewInstance(ThemedReactContext reactContext) {
        ReactMultiViewPager pager = new ReactMultiViewPager(reactContext);
        pager.setClipToPadding(false);
        return pager;
    }


    @ReactProp(name = "scrollEnabled", defaultBoolean = true)
    public void setScrollEnabled(ReactMultiViewPager viewPager, boolean value) {
        viewPager.setScrollEnabled(value);
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                PageScrollEvent.EVENT_NAME, MapBuilder.of("registrationName", "onPageScroll"),
                PageScrollStateChangedEvent.EVENT_NAME, MapBuilder.of("registrationName", "onPageScrollStateChanged"),
                PageSelectedEvent.EVENT_NAME, MapBuilder.of("registrationName", "onPageSelected")
        );
    }

    @Override
    public Map<String,Integer> getCommandsMap() {
        return MapBuilder.of(
                "setPage",
                COMMAND_SET_PAGE,
                "setPageWithoutAnimation",
                COMMAND_SET_PAGE_WITHOUT_ANIMATION);
    }

    @Override
    public void receiveCommand(
            ReactMultiViewPager viewPager,
            int commandType,
            @Nullable ReadableArray args) {
        Assertions.assertNotNull(viewPager);
        Assertions.assertNotNull(args);
        switch (commandType) {
            case COMMAND_SET_PAGE: {
                viewPager.setCurrentItemFromJs(args.getInt(0), true);
                return;
            }
            case COMMAND_SET_PAGE_WITHOUT_ANIMATION: {
                viewPager.setCurrentItemFromJs(args.getInt(0), false);
                return;
            }
            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandType,
                        getClass().getSimpleName()));
        }
    }

    @Override
    public void addView(ReactMultiViewPager parent, View child, int index) {
        parent.addViewToAdapter(child, index);
    }

    @Override
    public int getChildCount(ReactMultiViewPager parent) {
        return parent.getViewCountInAdapter();
    }

    @Override
    public View getChildAt(ReactMultiViewPager parent, int index) {
        return parent.getViewFromAdapter(index);
    }

    @Override
    public void removeViewAt(ReactMultiViewPager parent, int index) {
        parent.removeViewFromAdapter(index);
    }

    @ReactProp(name = "pageMargin", defaultFloat = 0)
    public void setPageMargin(ReactMultiViewPager pager, float margin) {
        pager.setPageMargin((int) PixelUtil.toPixelFromDIP(margin));
    }

    @ReactProp(name = "pagePadding", defaultFloat = 60)
    public void setPagePadding(ReactMultiViewPager pager, float padding) {
        pager.setPadding(
                (int) PixelUtil.toPixelFromDIP(padding),
                0,
                (int) PixelUtil.toPixelFromDIP(padding)
                ,0
        );
    }
}