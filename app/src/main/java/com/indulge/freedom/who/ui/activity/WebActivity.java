package com.indulge.freedom.who.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.util.ToastUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.OnClick;



/**
 * WebView页面
 * 
 * @author huangyue
 * 
 */
public class WebActivity extends BaseActivity {

	public static final String ACTION_TITLE = "title";// webView标题的action
	public static final String ACTION_URL = "url";// webView网址的action

	@Bind(R.id.lLayout_webview)
	LinearLayout mLayout;
	@Bind(R.id.tv_header_title)
	TextView mTitle;
	@Bind(R.id.pro_banner)
	ProgressBar mProgressBar;
	@Bind(R.id.web_banner)
	WebView mWeb;
	private String mWebTitle;
	private String mURL;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_banner_web);
		// 适配4.4的Translucent bar
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.color_translucent_bar);
			tintManager.setTintColor(Color.parseColor("#E96E06"));
		}
	}

	@Override
	protected void getData() {
		Intent intent = getIntent();
		mWebTitle = intent.getStringExtra(ACTION_TITLE);
		mURL = intent.getStringExtra(ACTION_URL);
		Log.i("useNo", SPUtil.getUseNo(context));
		Log.i("WebActivity", mURL);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void showContent() {
		mTitle.setText(mWebTitle);
		if (!TextUtils.isEmpty(mURL)) {
			try {
				// mWeb.requestFocus();
				// mWeb.setScrollBarStyle(0);
				WebSettings settings = mWeb.getSettings();
				settings.setJavaScriptEnabled(true);// 支持js交互
				settings.setDomStorageEnabled(true);
				settings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不缓存
				settings.setJavaScriptCanOpenWindowsAutomatically(true);
				settings.setUseWideViewPort(true);
				settings.setLoadWithOverviewMode(true);
				settings.setBuiltInZoomControls(true);
				mWeb.loadUrl(mURL);
				mWeb.setWebChromeClient(new WebChromeClient() {
					@Override
					public void onProgressChanged(WebView view, int newProgress) {
						super.onProgressChanged(view, newProgress);
						try {
							mProgressBar.setProgress(newProgress);
							Log.i("HY", "加载的progress：" + newProgress);
							if (newProgress == 100) {
								mProgressBar.setVisibility(View.GONE);
							}
						} catch (Exception e) {

						}
					}

					@Override
					public void onGeolocationPermissionsShowPrompt(
							String origin, Callback callback) {
						callback.invoke(origin, true, false);
					}
				});
				mWeb.setWebViewClient(new WebViewClient() {
					public boolean shouldOverrideUrlLoading(WebView view,
							String url) {
						view.loadUrl(url);
						return true;
					}

					@Override
					public boolean shouldOverrideKeyEvent(WebView view,
							KeyEvent event) {
						return super.shouldOverrideKeyEvent(view, event);
					}
				});
				// mWeb.setVerticalScrollbarOverlay(true); // 指定的垂直滚动条有叠加样式
				// automobileWeb.setHorizontalScrollBarEnabled(false);//水平不显示
				// automobileWeb.setVerticalScrollBarEnabled(false); //垂直不显示
				// settings.setSupportZoom(true);// 设定支持缩放
			} catch (Exception e) {
				Log.i("HY", "跳转BannerWebActivity未知异常");
				try {
					mWeb.loadUrl(mURL);
				} catch (Exception e2) {
					Log.i("HY", "跳转BannerWebActivity加载未知异常");
				}
			}
		} else {
			ToastUtil.show(getApplicationContext(), "正在准备中，敬请期待");
		}
	}

	@OnClick(R.id.img_header_back)
	public void onClick(View view) {
		try {
			switch (view.getId()) {
			case R.id.img_header_back:
				finish();
				break;
			}
		} catch (Exception e) {
			Log.i("HY", "跳转BannerWebActivity未知异常");
			finish();
		}

	}

	@Override
	public void onBackPressed() {
		try {
			if (mWeb.canGoBack()) {
				mWeb.goBack();
			} else {
				finish();
			}
		} catch (Exception e) {
			Log.i("HY", "跳转BannerWebActivity未知异常");
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		try {
			if (mWeb != null) {
				mWeb.onPause();
				// mWeb.clearCache(true);
				// mWeb.clearFormData();
				// mWeb.clearHistory();
				// mWeb.clearMatches();
				// mWeb.clearSslPreferences();
				mLayout.removeView(mWeb);
				mWeb.destroy();
			}
		} catch (Exception e) {
			Log.i("HY", "跳转BannerWebActivity未知异常");
		}
		super.onDestroy();
	}
}