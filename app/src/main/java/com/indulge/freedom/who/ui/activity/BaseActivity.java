package com.indulge.freedom.who.ui.activity;

import cn.bmob.v3.exception.BmobException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import com.indulge.freedom.who.AppManager;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;
import com.indulge.freedom.who.util.ScreenUtils;
import com.indulge.freedom.who.util.ToastUtil;
import com.squareup.picasso.Picasso;

/**
 * Activity基类
 * 
 * @author huangyue
 * 
 */
public abstract class BaseActivity extends FragmentActivity {
	protected Context context;
	protected int mScreenWidth;
	protected int mScreenHeight;

	public static String TAG = "FKH";

	// 应用是否销毁标志
	protected boolean isDestroy;

	// 防止重复点击设置的标志，涉及到点击打开其他Activity时，将该标志设置为false，在onResume事件中设置为true
	private boolean clickable = true;

	protected boolean savedIstance=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		create(savedInstanceState);
		if(!savedIstance){
			context = this;
			mScreenWidth = ScreenUtils.getScreenWidth(context);
			mScreenHeight = ScreenUtils.getScreenHeight(context);
			init();
			// 添加Activity到堆栈
			AppManager.getAppManager().addActivity(this);
		}
	}

	@Override
	protected void onDestroy() {
		destory();
		ButterKnife.unbind(this);
		isDestroy = true;
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
		if (this.mCompositeSubscription != null) {
			this.mCompositeSubscription.unsubscribe();
		}
	}
	
	protected void create(Bundle savedInstanceState){
		
	}

	/**
	 * 初始化
	 */
	protected void init() {
		setContentView();
		ButterKnife.bind(this);
		findViews();
		getData();
		showContent();
	}

	/**
	 * destory
	 */
	protected void destory() {

	}

	/**
	 * 加载布局
	 */
	protected abstract void setContentView();

	/**
	 * 初始化控件
	 */
	protected void findViews() {

	}

	/**
	 * 初始化数据
	 */
	protected abstract void getData();

	/**
	 * 处理数据
	 */
	protected abstract void showContent();

	@Override
	protected void onResume() {
		super.onResume();
		// 每次返回界面时，将点击标志设置为可点击
		clickable = true;
	}

	@Override
	public void onPause() {
		super.onPause();
	}


	private CompositeSubscription mCompositeSubscription;

	/**
	 * 解决Subscription内存泄露问题
	 * @param s
	 */
	protected void addSubscription(Subscription s) {
		if (this.mCompositeSubscription == null) {
			this.mCompositeSubscription = new CompositeSubscription();
		}
		this.mCompositeSubscription.add(s);
	}



	/**
	 * 当前是否可以点击
	 * 
	 * @return
	 */
	protected boolean isClickable() {
		return clickable;
	}

	/**
	 * 锁定点击
	 */
	protected void lockClick() {
		clickable = false;
	}

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (isClickable()) {
			lockClick();
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

//	/**
//	 * 网络访问
//	 *
//	 * @param call
//	 * @param back
//	 */
//	protected <T> void call(Call<Result<T>> call, final IBack<T> back) {
//		call.enqueue(new Callback<Result<T>>() {
//
//			@Override
//			public void onFailure(Throwable arg0) {
//				if (!isDestroy) {
//					back.fail();
//					show(Constant.FAIL);
//				}
//			}
//
//			@Override
//			public void onResponse(Response<Result<T>> response,
//					Retrofit retrofit) {
//				if (!isDestroy) {
//					if (response != null) {
//						Result<T> result = response.body();
//						if (result != null) {
//							Log.d("HY", "请求结果：" + result.toString());
//							switch (result.retCode) {
//							case "200":
//								back.success(result);
//								break;
////							case 2:// 请求成功无数据
////								back.noDate();
////								break;
////							case 4:// 身份验证失败
////								back.notLoggedIn();
////								break;
////							case 5:
////								back.fail();
////								back.bail(result);
////								break;
//							default:
//
//								back.fail();
//								if(result.msg!=null){
//									if(!TextUtils.isEmpty(result.msg)){
//										show(result.msg);
//									}
//								}
//								break;
//							}
//						} else {
//							back.fail();
//							Log.d("HY", "result = null");
//							show(Constant.FAIL);
//						}
//					} else {
//						back.fail();
//						Log.d("HY", "response = null");
//						show(Constant.FAIL);
//					}
//				}
//			}
//		});
//	}
//
//	public abstract class IBack<T> {
//		abstract void success(Result<T> result);
//
//		void noDate() {
//
//		}
//
//		void notLoggedIn() {
//
//		}
//
//		abstract void fail();
//
//		void bail(Result<T> result){
//		}
//
//	}
//

	public static void log(String msg) {
		Log.i(TAG,"===============================================================================");
		Log.i(TAG, msg);
	}



	public static void loge(Throwable e) {
		Log.i(TAG,"===============================================================================");
		if(e instanceof BmobException){
			Log.e(TAG, "错误码："+((BmobException)e).getErrorCode()+",错误描述："+((BmobException)e).getMessage());
		}else{
			Log.e(TAG, "错误描述："+e.getMessage());
		}
	}



	/**
	 * 此方法是让字体不随系统改变而改变的
	 */
	@Override
	public Resources getResources() {
	    Resources res = super.getResources();
	    Configuration config=new Configuration();
	    config.setToDefaults();
	    res.updateConfiguration(config,res.getDisplayMetrics() );
	    return res;
	}



}
