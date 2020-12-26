package com.rndemo59.native_js_bridge;

import com.facebook.react.bridge.JavaScriptModule;

public interface TestJavaToJavascript extends JavaScriptModule {

  void testMethod(String appKey);
}
