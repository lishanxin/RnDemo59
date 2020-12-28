package com.rndemo59.native_ui;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaMeasureMode;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MyCustomImageViewManager extends SimpleViewManager<MyCustomImageView> {
  public static final String REACT_CLASS = "MyCustomImageView";

  @Nonnull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Nonnull
  @Override
  protected MyCustomImageView createViewInstance(@Nonnull ThemedReactContext reactContext) {
    return new MyCustomImageView(reactContext);
  }

  @ReactProp(name = "src")
  public void setSrc(MyCustomImageView view, String sourceUrl) {
    view.setImageUrl(sourceUrl);
  }


}
