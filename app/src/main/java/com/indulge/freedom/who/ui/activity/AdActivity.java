/**
 * 
 */
package com.indulge.freedom.who.ui.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;
import com.indulge.freedom.who.model.Ad;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.util.ScreenUtils;
import com.indulge.freedom.who.util.Tools;
import com.squareup.picasso.Picasso;


/**
 * 广告页
 * 
 * @author fangxiaotian
 * 
 */
public class AdActivity extends Activity {
	public static final String AD = "ad";// 广告信息的action

	private Ad mAd;// 启动页传来的广告信息

	private int mDisplay;
	private CountDownTimer mTimer;

	@Bind(R.id.tv_ignore)
	Button tvIgnore;

	@Bind(R.id.iv_ad)
	ImageView ivAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);
		ButterKnife.bind(this);
		getData();
	}

	@OnClick({ R.id.tv_ignore, R.id.iv_ad })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_ignore:



			if (mTimer != null) {
				mTimer.cancel();
				mTimer = null;
			}
			if (SPUtil.getFirstIn(AdActivity.this)) {
				SPUtil.saveFirstIn(AdActivity.this, false);
				startActivity(new Intent(AdActivity.this, GuideActivity.class));
			} else {

				String username = (String) BmobUser.getObjectByKey("username");
				if(username!=null){
					Intent intent = new Intent();
					intent.setClass(AdActivity.this, HomePagerActivity.class);
					intent.putExtra(Constant.ACTION, Constant.CAR_PECCANCY);
					startActivity(intent);
				}else{
					Intent intent = new Intent();
					intent.setClass(AdActivity.this, LoginActivity.class);
					startActivity(intent);
				}

			}
			finish();
			break;
		case R.id.iv_ad:
			if (mAd != null && !TextUtils.isEmpty(mAd.getAdvertUrl())) {
				if (mTimer != null) {
					mTimer.cancel();
					mTimer = null;
				}
				if (Tools.containsString(mAd.getAdvertUrl(), "WeiZhang")) {
					Intent intent = new Intent(this,
							HomePagerActivity.class);
					intent.putExtra(Constant.ACTION, Constant.CAR_PECCANCY);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(this,
							WebActivity.class);
					intent.putExtra(WebActivity.ACTION_TITLE,
							mAd.getStartPageName());
					if(Tools.containsString(mAd.getAdvertUrl(),
						"zhqc_financial")){	//车主理财链接
						intent.putExtra(WebActivity.ACTION_URL,
								mAd.getAdvertUrl() + "&UserId=" + SPUtil.getUserId(this) + "&EquipmentNo=" + SPUtil.getDeviceId(this));
					} else {
						intent.putExtra(WebActivity.ACTION_URL,
								mAd.getAdvertUrl());
					}
					startActivity(intent);
				}
			}
			break;
		default:
			break;
		}
	}

	private void getData() {
		mDisplay = 5000;
		mAd = (Ad) getIntent().getSerializableExtra(AD);// 获取启动页传来的广告信息
		if (mAd != null) {
			int width = ScreenUtils.getScreenWidth(this);
			int height = ScreenUtils.getScreenHeight(this);
			String adPicUrl = mAd.getStartPageImgNo4S();
			String adPicName = "";
			if (adPicUrl.contains("/")) {
				adPicName = adPicUrl.substring(adPicUrl.lastIndexOf("/"));
			}
			if (!TextUtils.isEmpty(adPicName)) {
				File adPictrue = new File(AppContext.getPictureDir(), adPicName);
				if (adPictrue.exists()
						&& adPictrue.length() == Constant.adPictureSize
						&& adPictrue.length() != 0) {// 如果广告的图片已经缓存到本地并且本地的图片和网络上的广告图片大小相等时就取本地的广告图片
					Log.i("HY", "本地读取广告页");
					ivAd.setImageBitmap(BitmapFactory.decodeFile(adPictrue
							.getAbsolutePath()));
				} else {// 否则就直接从网络上获取图片
					Log.i("HY", "网络加载广告页");
					if (!TextUtils.isEmpty(mAd.getStartPageImgNo4S())) {
						Picasso.with(this).load(mAd.getStartPageImgNo4S())
								.config(Bitmap.Config.RGB_565)
								.resize(width, height).into(ivAd);
					}
				}
			} else {
				Log.i("HY", "网络加载广告页");
				if (!TextUtils.isEmpty(mAd.getStartPageImgNo4S())) {
					Picasso.with(this).load(mAd.getStartPageImgNo4S())
							.config(Bitmap.Config.RGB_565)
							.resize(width, height).into(ivAd);
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		welcome();
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
					tvIgnore.setText("跳过 " + (millisUntilFinished / 1000) + "s");
					mDisplay -= 1000;
				}

				@Override
				public void onFinish() {
					if (SPUtil.getFirstIn(AdActivity.this)) {
						SPUtil.saveFirstIn(AdActivity.this, false);
						startActivity(new Intent(AdActivity.this,
								GuideActivity.class));
					} else {

						String username = (String) BmobUser.getObjectByKey("username");
						if(username!=null){
							Intent intent = new Intent();
							intent.setClass(AdActivity.this, HomePagerActivity.class);
							intent.putExtra(Constant.ACTION, Constant.CAR_PECCANCY);
							startActivity(intent);
						}else{
							Intent intent = new Intent();
							intent.setClass(AdActivity.this, LoginActivity.class);
							startActivity(intent);
						}

					}
					finish();
				}
			};
			mTimer.start();
		}

	}

	@Override
	protected void onDestroy() {
		ButterKnife.unbind(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (SPUtil.getFirstIn(AdActivity.this)) {
			SPUtil.saveFirstIn(AdActivity.this, false);
			startActivity(new Intent(AdActivity.this, GuideActivity.class));
		} else {
			startActivity(new Intent(AdActivity.this, textActivity.class));
		}
		finish();
	}

}
