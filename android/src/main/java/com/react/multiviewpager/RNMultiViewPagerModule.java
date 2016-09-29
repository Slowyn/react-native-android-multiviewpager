
package com.react.multiviewpager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.pixplicity.multiviewpager.MultiViewPager;

public class RNMultiViewPagerModule extends SimpleViewManager<MultiViewPager> {

  private final ReactApplicationContext reactContext;
  public static final String REACT_CLASS = "RNMultiViewPager";

  public RNMultiViewPagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public MultiViewPager createViewInstance(ThemedReactContext context) {
    return new MultiViewPager(context);
  }
}
