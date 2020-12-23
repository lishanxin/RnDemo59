package com.rndemo59.mini_program;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.rndemo59.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class MiniProgramActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

  private static final String TAG = MiniProgramActivity.class.getSimpleName();
  private static final String URL_DOWNLOAD_KEY = "bundle.url.key";
  private static final String REACT_APPLICATION_NAME = "MiniProgram";
  ReactRootView mReactRootView;

  ReactInstanceManager mReactInstanceManager;

  String path = null;
  String zipFile = null;

  public static void show(Context context, String bundleDownloadUrl){
    Intent intent = new Intent(context, MiniProgramActivity.class);
    intent.putExtra(URL_DOWNLOAD_KEY, bundleDownloadUrl);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    if(intent == null) {
      Log.d(TAG, "intent is null, finish()");
      finish();
      return;
    }
    String downloadUrl = intent.getStringExtra(URL_DOWNLOAD_KEY);
    if(downloadUrl == null){
      Log.d(TAG, "downloadUrl is null, finish()");
      finish();
      return;
    }
    String fileDir =getFilesDir().getPath() + "/" + downloadUrl.substring(downloadUrl.lastIndexOf("/"), downloadUrl.lastIndexOf("."));

    path = fileDir + "/index.android.bundle";
    zipFile = fileDir + "/android.bundle.zip";
    setContentView(R.layout.activity_mini_program);

    mReactRootView = (ReactRootView) findViewById(R.id.mini_program_root_view);

    if (new File(path).exists()) {
      setReactNative();
    } else {
      Log.d(TAG, "加载中...");
      downLoadBundle(downloadUrl, zipFile, new OnDownloadSucceedListener(){

        @Override
        public void onDownloadSucceed() {
          try {
            unZip(MiniProgramActivity.this, zipFile, path, new OnUnzipSucceedListener() {
              @Override
              public void onUnzipSucceed() {
                runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    recreate();
                  }
                });
              }
            });
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  @Override
  public void invokeDefaultOnBackPressed() {
    super.onBackPressed();
  }

  @Override
  protected void onPause() {
    super.onPause();

    if (mReactInstanceManager != null) {
      mReactInstanceManager.onHostPause(this);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (mReactInstanceManager != null) {
      mReactInstanceManager.onHostResume(this, this);
    }
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (mReactInstanceManager != null) {
      mReactInstanceManager.onHostDestroy(this);
    }
  }


  @Override
  public void onBackPressed() {
    if (mReactInstanceManager != null) {
      mReactInstanceManager.onBackPressed();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
      mReactInstanceManager.showDevOptionsDialog();
      return true;
    }
    return super.onKeyUp(keyCode, event);
  }


  private void downLoadBundle(String url, String path, OnDownloadSucceedListener listener) {
    downloadSmallFile(url, path, listener);
  }



  private void setReactNative() {
    if (new File(path).exists()) {
      mReactInstanceManager = ReactInstanceManager.builder()
        .setApplication(this.getApplication())
        .setJSBundleFile(path)//设置加载文件
        .setNativeModuleCallExceptionHandler(new NativeModuleCallExceptionHandler() {
          @Override
          public void handleException(Exception e) {
            e.printStackTrace();
          }
        })
        .addPackage(new MainReactPackage())
        .setUseDeveloperSupport(false)
        .setInitialLifecycleState(LifecycleState.RESUMED)
        .build();
      mReactRootView.startReactApplication(mReactInstanceManager, REACT_APPLICATION_NAME, null);//启动入口
    } else {
      Toast.makeText(this, "请把项目根目录下的 android.bundle 放到手机的根目录下。", Toast.LENGTH_LONG).show();
    }
  }


  /**
   * 文件下载
   *
   * <p>
   *
   */
  public void downloadSmallFile(final String uri, final String filePath, OnDownloadSucceedListener listener) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(uri).build();

        try {
          Response response = client.newCall(request).execute();
          if (!response.isSuccessful()) {
            return ;
          }

          ResponseBody body = response.body();
//      long contentLength = body.contentLength();
          BufferedSource source = body.source();
          File file = new File(filePath);
          if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
          }
          BufferedSink sink = Okio.buffer(Okio.sink(file));
          sink.writeAll(source);
          sink.flush();
          listener.onDownloadSucceed();
          Log.d(TAG, "下载成功。。。");
        } catch (IOException e) {
          Log.d(TAG, "下载失败。。。");
          e.printStackTrace();
        }
      }
    }).start();
  }


  /**
   * 解压指定zip文件
   *
   * @param unZipfile
   *            压缩文件的路径
   * @param destFile
   *            　　　解压到的目录　
   */
  public static void unZip(Context context, String unZipfile, String destFile, OnUnzipSucceedListener listener)
    throws IOException, FileNotFoundException, ZipException {
    Log.d(TAG, "开始解压。。。");
    BufferedInputStream bi;
    ZipFile zipFile = new ZipFile(new File(unZipfile));

    Enumeration e = zipFile.entries();
    while (e.hasMoreElements()) {
      ZipEntry ze2 = (ZipEntry) e.nextElement();
      String entryName = ze2.getName();
      if (ze2.isDirectory()) {
        System.out.println("正在创建解压目录 - " + entryName);
        File decompressDirFile = new File(destFile);
        if (!decompressDirFile.exists()) {
          decompressDirFile.mkdirs();
        }
      } else {
        System.out.println("正在创建解压文件 - " + entryName);
        String fileDir = destFile.substring(0, destFile.lastIndexOf("/"));
        File fileDirFile = new File(fileDir);
        if (!fileDirFile.exists()) {
          fileDirFile.mkdirs();
        }
        BufferedOutputStream bos = new BufferedOutputStream(
          new FileOutputStream(destFile));
        bi = new BufferedInputStream(zipFile.getInputStream(ze2));
        byte[] readContent = new byte[1024];
        int readCount = bi.read(readContent);
        while (readCount != -1) {
          bos.write(readContent, 0, readCount);
          readCount = bi.read(readContent);
        }
        bos.close();
      }
    }
    zipFile.close();
    listener.onUnzipSucceed();
    Log.d(TAG, "解压成功。。。");
  }

  public interface OnDownloadSucceedListener{
    void onDownloadSucceed();
  }
  public interface OnUnzipSucceedListener{
    void onUnzipSucceed();
  }

}
