package com.rndemo59.native_ui;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
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

  private static final int CHANGE_ICON_VISIBLE = 1;
  private static final int CHANGE_ICON_INVISIBLE = 2;

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

  // Java调用JS组件方法的注册
  @Nullable
  @Override
  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
      .put(
        "topChange",
        MapBuilder.of(
          "phasedRegistrationNames",
          MapBuilder.of("bubbled", "onChange")))
      .build();
  }

  // JS调用Java组件方法的注册
  @Nullable
  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("changeIconVisible", CHANGE_ICON_VISIBLE, "changeIconInvisible", CHANGE_ICON_INVISIBLE);
  }

  // JS调用Java组件的方法
  @Override
  public void receiveCommand(@Nonnull MyCustomImageView root, int commandId, @Nullable ReadableArray args) {
    switch (commandId){
      case CHANGE_ICON_VISIBLE:
        root.setIconVisible();
        break;
      case CHANGE_ICON_INVISIBLE:
        root.setIconInvisible();
        break;
    }
  }
}
