package com.indulge.freedom.who.ui.fragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;

import com.indulge.freedom.who.util.ScreenUtils;
import com.indulge.freedom.who.util.ToastUtil;
import com.squareup.picasso.Picasso;

/**
 * 基于Butterknife注解的Fragment
 * 
 * @author huhuan
 * 
 */
public abstract class BaseFragment extends Fragment {
	public abstract int getContentViewId();
	protected Context context;
	protected View mRootView;
	protected int mScreenWidth;
	protected int mScreenHeight;

	// 应用是否销毁标志
	protected boolean isDestroy;

	private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

	@Nullable
	@Override
	/**
	 * 绑定butterknife找到的视图并创建
	 */
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(getContentViewId(), container, false);
		ButterKnife.bind(this, mRootView);// 绑定framgent
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		mScreenHeight = ScreenUtils.getScreenHeight(context);
		return mRootView;
	}


		@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		/**
		 * 解决重用fragment问题
		 */
		if (savedInstanceState != null) {
			boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (isSupportHidden) {
				ft.hide(this);
			} else {
				ft.show(this);
			}
			ft.commit();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
	}



	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.context = context;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initAllMembersView(savedInstanceState);
	}

	/**
	 * 处理找到找到视图并创建后的操作
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void initAllMembersView(Bundle savedInstanceState);

	/**
	 * 加载图片
	 * 
	 * @param imageView
	 * @param url
	 */
	protected void loadImage(ImageView imageView, String url) {
		if (!TextUtils.isEmpty(url)) {
			Picasso.with(context.getApplicationContext()).load(url)
					.config(Bitmap.Config.RGB_565).into(imageView);
		}
	}

	/**
	 * 提示信息
	 * 
	 * @param message
	 */
	protected void show(String message) {
//		ToastUtil.show(context, message);
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
		Toast toast = new Toast(context);
		toast.setView(toastRoot);
		toast.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 80);
		TextView tv = (TextView) toastRoot.findViewById(R.id.toast_notice);
		tv.setText(message);
		toast.show();
	}



	/**
	 * 展示一个特定颜色的Toast
	 *
	 * @param message
	 */
	protected void toast(String message) {
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
		Toast toast = new Toast(context);
		toast.setView(toastRoot);
		toast.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 80);
		TextView tv = (TextView) toastRoot.findViewById(R.id.toast_notice);
		tv.setText(message);
		toast.show();
	}

	/**
	 * 销毁时解除绑定
	 */
	@Override
	public void onDestroyView() {
		isDestroy = true;
		ButterKnife.unbind(this);// 解绑
		super.onDestroyView();
	}


	
	
}