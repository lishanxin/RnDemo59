package com.rndemo59.native_js_bridge;

import android.app.AlertDialog;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

public class AlertModule extends ReactContextBaseJavaModule {
  private static final String RCT_CLASS = "MyCustomAlert";

  public AlertModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Nonnull
  @Override
  public String getName() {
    return RCT_CLASS;
  }

  /**
   * Alert窗口弹出
   * @param message 展示内容
   * @param promise 回调方法， ES6-Promise
   */
  @ReactMethod
  public void alertPromise(String message, Promise promise) {
    AlertDialog.Builder dialogBuilder = new  AlertDialog.Builder(getCurrentActivity());
    dialogBuilder.setMessage(message);
    dialogBuilder.setOnCancelListener(dialogInterface -> {promise.reject(new Throwable("alertPromise cancel"));});
    dialogBuilder.setPositiveButton("ok", (dialog, which) -> {promise.resolve("alertPromise ok");});
    dialogBuilder.create().show();
  }

  /**
   *  Alert窗口弹出
   * @param message 展示内容
   * @param callback 回调方法，java接口Callback
   */
  @ReactMethod
  public void alertCallback(String message,  Callback callback) {
    AlertDialog.Builder dialogBuilder = new  AlertDialog.Builder(getCurrentActivity());
    dialogBuilder.setMessage(message);
    dialogBuilder.setOnCancelListener(dialogInterface -> {callback.invoke("alertCallback cancel");});
    dialogBuilder.setPositiveButton("ok", (dialog, which) -> {callback.invoke("alertCallback ok");});
    dialogBuilder.create().show();
  }

}
