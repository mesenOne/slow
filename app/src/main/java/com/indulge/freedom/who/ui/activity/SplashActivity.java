package com.indulge.freedom.who.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.AppManager;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;
//import com.indulge.freedom.who.http.Http;
//import com.indulge.freedom.who.http.Result;
import com.indulge.freedom.who.model.Ad;
import com.indulge.freedom.who.util.SPUtil;
//import com.umeng.analytics.AnalyticsConfig;
//import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.bmob.v3.BmobUser;

/**
 * Created by fengkehua on 2016/7/1.
 */
public class SplashActivity extends Activity {


    private ImageView mSplash;
    private AnimationDrawable mAnimationDrawable;
    private CountDownTimer mTimer;
    private int mDisplay;
    private Ad mAd;// 广告页信息
//    private Call<Result<Ad>> mAdCall;
    private DownLoadAd downLoadAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppContext.startedApp = true;


        mSplash = (ImageView) findViewById(R.id.img_splash);
        mAnimationDrawable = (AnimationDrawable) mSplash.getDrawable();
        mAnimationDrawable.start();
        mDisplay = 2500;

        getAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        welcome();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void getAd() {
//        mAdCall = Http.getService().getAd(Http.getParams(null));
//        mAdCall.enqueue(new Callback<Result<Ad>>() {
//            @Override
//            public void onResponse(Response<Result<Ad>> response,
//                                   Retrofit retrofit) {
//                if (response != null) {
//                    Result<Ad> result = response.body();
//                    if (result != null) {
//                        Log.i("HY", "获取广告信息：" + result.toString());
//                        switch (result.retCode) {
//                            // 请求成功
//                            case "200":
////                                mAd = result.result;
//                                mAd=null;  // 广告锁
//                                if (mAd != null
//                                        && !TextUtils.isEmpty(mAd
//                                        .getStartPageImgNo4S())) {
//                                    downLoadAd = new DownLoadAd();
//                                    downLoadAd.execute(mAd.getStartPageImgNo4S());
//                                }
//                                break;
//                        }
//                    }
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable arg0) {
//
//            }
//
//        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void welcome() {
        if (mTimer == null) {
            mTimer = new CountDownTimer(mDisplay, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mDisplay -= 1000;
                    Log.i("HY", mDisplay + " ms");
                }

                @Override
                public void onFinish() {

//                    if (mAdCall != null) {
//                        mAdCall.cancel();
//                        mAdCall=null;
//                    }
                    if (mAd != null && mAd.isStartPageIsDownLoad()) {
                        Intent intent = new Intent(SplashActivity.this,
                                AdActivity.class);
                        intent.putExtra(AdActivity.AD, mAd);
                        startActivity(intent);
                    } else {  // 如果没有广告
                        if (SPUtil.getFirstIn(SplashActivity.this)) {
                            SPUtil.saveFirstIn(SplashActivity.this, false);
                            // 如果是第一次登陆就启动引导页
                            startActivity(new Intent(SplashActivity.this,
                                    GuideActivity.class));
                        } else {

                            String username = (String) BmobUser.getObjectByKey("username");
                            if(username!=null){
                                // 如果不是第一次登陆 并且已经登陆 就直接进入主界面
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this,
                                        HomePagerActivity.class);
                                intent.putExtra(Constant.ACTION, Constant.CAR_PECCANCY);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }
                    }
                    finish();
                }
            };
            mTimer.start();
        }

    }

    private class DownLoadAd extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection http = (HttpURLConnection) url
                        .openConnection();
                http.setConnectTimeout(5000); // 设置连接超时
                http.setReadTimeout(5000);
                http.connect();
                String adPicName = params[0].substring(params[0]
                        .lastIndexOf("/"));
                File adPic = new File(AppContext.getPictureDir(), adPicName);
                Constant.adPictureSize = http.getContentLength();
                if (adPic.exists()) {
                    Log.i("HY", "本地的广告图片大小：" + adPic.length() + "，网络图片大小："
                            + Constant.adPictureSize);
                    if (adPic.length() == Constant.adPictureSize) {
                        Log.i("HY", "本地存在该广告图片，并且大小和网络大小相等，不再下载");
                        return true;
                    }
                    adPic.delete();
                    Log.i("HY", "本地存在该广告图片，但是大小和网络图片大小不相等，说明没下载完。重新下载");
                }
                if (Constant.adPictureSize != 0) {
                    Log.i("HY", "开始下载");
                    FileOutputStream outputStream = new FileOutputStream(
                            new File(AppContext.getPictureDir(), adPicName));
                    // 得到输入流
                    InputStream inStream = http.getInputStream();
                    byte[] buffer = new byte[1024];
                    int offset = 0;
                    while ((offset = inStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, offset);
                    }
                    outputStream.flush();
                    outputStream.close();
                    inStream.close();
                }
            } catch (Exception e) {
                Log.i("HY", "下载广告图片异常");
                e.printStackTrace();
            }
            return true;
        }

    }

    @Override
    protected void onDestroy() {
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AppContext.startedApp = false;
    }

}
