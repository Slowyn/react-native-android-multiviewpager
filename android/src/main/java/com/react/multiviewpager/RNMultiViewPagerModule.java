
package com.react.multiviewpager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;

import com.pixplicity.multiviewpager.MultiViewPager;

public class RNMultiViewPagerModule extends SimpleViewManager<MultiViewPager> {

  public static final String REACT_CLASS = "RNMultiViewPager";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public MultiViewPager createViewInstance(ThemedReactContext context) {
    return new MultiViewPager(context);
  }
}
