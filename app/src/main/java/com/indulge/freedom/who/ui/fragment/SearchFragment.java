package com.indulge.freedom.who.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.view.xrecyclerview.XRecyclerView;

//import com.umeng.analytics.MobclickAgent;


/**
 * 我的页面fragment
 * 
 * @author fangxiaotian
 * 
 */

public class SearchFragment extends BaseFragment<XRecyclerView> {


	
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
