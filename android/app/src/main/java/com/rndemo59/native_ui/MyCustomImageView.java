package com.rndemo59.native_ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.rndemo59.R;

public class MyCustomImageView extends AppCompatImageView {

  private Bitmap mBitmap = null;
  private Rect mSourceRect = null;
  private RectF mDestRect = null;
  private boolean iconVisible = true;

  public MyCustomImageView(Context context) {
    super(context);
    initView(context, null);
  }

  public MyCustomImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
  }

  public MyCustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context, attrs);
  }

  private void initView(Context context, AttributeSet attrs) {
    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tiny_logo, null);
    mSourceRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());

    setScaleType(ScaleType.CENTER_CROP);

    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onReceiveNativeEvent();
      }
    });
  }

  public void setImageUrl(String data) {
    Glide.with(getContext()).load(data).into(this);
  }

  public void onReceiveNativeEvent() {
    WritableMap event = Arguments.createMap();
    event.putString("message", "MyMessage");
    ReactContext reactContext = (ReactContext)getContext();
    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
      getId(),
      "topChange",
      event);
  }



  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if(iconVisible){
      if(mDestRect == null){
        mDestRect = new RectF(getWidth() - 40, 0, getWidth(), 40);
      }
      if(mBitmap != null && mSourceRect != null){
        canvas.drawBitmap(mBitmap, mSourceRect, mDestRect, null);
      }
    }
  }

  public void setIconVisible() {
    iconVisible = true;
    invalidate();
  }

  public void setIconInvisible() {
    iconVisible = false;
    invalidate();
  }
}
