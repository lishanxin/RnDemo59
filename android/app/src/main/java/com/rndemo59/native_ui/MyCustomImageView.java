package com.rndemo59.native_ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.rndemo59.R;

public class MyCustomImageView extends AppCompatImageView {

  private Bitmap mBitmap = null;
  private Rect mSourceRect = null;
  private RectF mDestRect = null;

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
  }

  public void setImageUrl(String data) {
    Glide.with(getContext()).load(data).into(this);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if(mDestRect == null){
      mDestRect = new RectF(getWidth() - 40, 0, getWidth(), 40);
    }
    if(mBitmap != null && mSourceRect != null){
      canvas.drawBitmap(mBitmap, mSourceRect, mDestRect, null);
    }
  }
}
