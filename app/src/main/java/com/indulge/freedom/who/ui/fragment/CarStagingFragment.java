package com.indulge.freedom.who.ui.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.indulge.freedom.who.R;

import butterknife.Bind;
import butterknife.OnClick;



/**
 * 车主分期页(主页)
 * 
 * @author fengkehua
 * 
 */
public class CarStagingFragment extends BaseFragment {

	 @Override
	 public int getContentViewId() {
		 return R.layout.activity_main;
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