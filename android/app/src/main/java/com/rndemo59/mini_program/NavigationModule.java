package com.rndemo59.mini_program;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rndemo59.MainActivity;

import javax.annotation.Nonnull;

public class NavigationModule extends ReactContextBaseJavaModule {
  private static final String RCT_CLASS = "MyNavigation";

  public NavigationModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Nonnull
  @Override
  public String getName() {
    return RCT_CLASS;
  }

  /**
   *
   * @param bundleUrl bundle文件的下载地址
   */
  @ReactMethod
  public void navigateToMiniProgram(String bundleUrl) {
    Activity context = getCurrentActivity();
    if(context instanceof MainActivity){
      MiniProgramActivity.show(context, bundleUrl);
    }
  }

}
