package com.rndemo59;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class MainActivity extends ReactActivity {

  private static final int RECYCLER_POST_EVENT = 1;
  private static final String TEST_EVENT_KEY = "testLsxEvent";

  /**
   * Returns the name of the main component registered from JavaScript.
   * This is used to schedule rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "RNDemo59";
  }

  Runnable recycleRunnable = new Runnable() {
    @Override
    public void run() {
      Message message = new Message();
      message.what = RECYCLER_POST_EVENT;
      handler.sendMessage(message);
      handler.postDelayed(recycleRunnable, 2000);
    }
  };
  private int count = 0;
  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case RECYCLER_POST_EVENT:
          ReactContext reactContext = MainActivity.this.getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
          if (reactContext != null) {
            WritableArray writableArray = Arguments.createArray();
            writableArray.pushString("A");
            writableArray.pushString("B");
            writableArray.pushInt(3);
            WritableMap params = Arguments.createMap();
            params.putString("Key1", "Hello world");
            params.putInt("Key2_count", count++);
            params.putDouble("Key3", 2.2);
            params.putBoolean("Key4", true);
            params.putArray("Key5", writableArray);
            sendEvent(reactContext, TEST_EVENT_KEY, params);
          }
          break;
        default:
          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    handler.postDelayed(recycleRunnable, 2000);
  }

  private void sendEvent(ReactContext reactContext,
                         String eventName,
                         @Nullable WritableMap params) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }
}
