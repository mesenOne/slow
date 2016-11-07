package com.indulge.freedom.who.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.view.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
//import com.umeng.analytics.MobclickAgent;


import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 我的页面fragment
 * 
 * @author fangxiaotian
 * 
 */

public class MineFragment extends BaseFragment<XRecyclerView>  {
//
//	@Bind(R.id.lv_geogas)
//	XRecyclerView mRecyclerView;
	
	@Override
	public int getContentViewId() {
		return R.layout.fragment_me;
	}

	@Override
	protected XRecyclerView createScrollable() {
		return null;
	}

	@Override
	protected View getObservableScrollTitleView() {
		return null;
	}

	@SuppressLint("InlinedApi")
	@Override
	protected void initAllMembersView(Bundle savedInstanceState) {
		// 适配4.4的Translucent bar
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getActivity().getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			Window window = getActivity().getWindow();
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}

	}


	

}
