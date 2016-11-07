package com.indulge.freedom.who.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.indulge.freedom.who.AppManager;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;
import com.indulge.freedom.who.util.ConversionUtil;
import com.indulge.freedom.who.util.ImageUtils;
import com.indulge.freedom.who.util.ScreenUtils;
import com.indulge.freedom.who.util.ToastUtil;
import com.indulge.freedom.who.view.CameraPreview;
import com.indulge.freedom.who.view.FocusView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.indulge.freedom.who.ui.activity.publishActivity.listFiles;


/**
 * 拍照页面
 * 
 * @author huhuan
 * 
 */
public class CameraActivity extends Activity implements SensorEventListener {
	@Bind(R.id.hc_main)
	CameraPreview mCamera;
	@Bind(R.id.ll_main)
	LinearLayout mContaint;
	@Bind(R.id.hs_main)
	HorizontalScrollView mScrollView;
	@Bind(R.id.btn_take)
	Button mTake;
	@Bind(R.id.btn_cancel)
	Button mCancel;
	@Bind(R.id.btn_complete)
	Button mComplete;

	@Bind(R.id.iv_bg_cut_photo)
	ImageView ivCutBg;
	
	@Bind(R.id.view_focus)
	FocusView focusView;
	private int size = 0;
	private int maxSize=0;
	
	private int mScreenWidth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mScreenWidth = ScreenUtils.getScreenWidth(this);
		Intent intent = getIntent();
		maxSize=intent.getIntExtra(Constant.UPLOAD_PHOTO_CHOOSE_COUNT, 0);
		init();
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}
	/**
	 * 初始化
	 */
	private void init() {
		setContentView();
		ButterKnife.bind(this);
		getData();
		showContent();
	}
	private void setContentView() {
		setContentView(R.layout.activity_camera);
	}

	private void getData() {
		if (!CameraPreview.canOpenCamera) {
			setResult(RESULT_CANCELED);
			ToastUtil.show(getApplicationContext(),"摄像头开启被拒绝,请至手机设置中开启相关权限");
			CameraPreview.canOpenCamera = true;
			CameraActivity.this.finish();

		}
		mCamera.setFocusView(focusView);
        mSensorManager = (SensorManager) this.getApplicationContext().getSystemService(Context.
                SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.
                TYPE_ACCELEROMETER);
        LayoutParams params=ivCutBg.getLayoutParams();
        params.width=mScreenWidth;
        params.height=(int) (mScreenWidth*Constant.THUMAIL_RATIO+ConversionUtil.dp2px(getApplicationContext(), 120));
        ivCutBg.setLayoutParams(params);
        
		
	}
	boolean isRotated = false;
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

	private void showContent() {
	}

	@OnClick({ R.id.btn_take, R.id.btn_cancel, R.id.btn_complete })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take:
				takePictrue();
			break;
		case R.id.btn_cancel:
			listFiles.clear();
			finish();
			break;
		case R.id.btn_complete:
			mComplete.setClickable(false);
			setResult(ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
			finish();
			break;
		default:
			break;
		}
	}

	private void takePictrue() {
		if (size >= maxSize) {
			ToastUtil.show(getApplicationContext(), getResources().getString(R.string.msg_amount_limit));
		} else {
			mCamera.takePic(new CameraPreview.TakeCallBack() {

				@SuppressLint({ "NewApi", "InflateParams" })
				@Override
				public void callBack(Bitmap orignalBitmap, final String name) {

					final View view = LayoutInflater.from(CameraActivity.this)
							.inflate(R.layout.item_pic, null);
					ImageView imageView = (ImageView) view
							.findViewById(R.id.img_item_pic);
					imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(orignalBitmap, ConversionUtil.dp2px(CameraActivity.this, 80), ConversionUtil.dp2px(CameraActivity.this, 60), ThumbnailUtils.OPTIONS_RECYCLE_INPUT));
					Button delete = (Button) view
							.findViewById(R.id.btn_item_delete);
					delete.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mCamera.delete(name);
							mContaint.removeView(view);
							size--;
							if (mContaint.getChildCount() == 0) {
								mComplete.setVisibility(View.GONE);
							} else {
								mComplete.setVisibility(View.VISIBLE);
							}
						}
					});
					mContaint.addView(view);
					size++;
					mScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
					if (mContaint.getChildCount() == 0) {
						mComplete.setVisibility(View.GONE);
					} else {
						mComplete.setVisibility(View.VISIBLE);
					}
					mCamera.startSurface();
				}
			});
		}

	}

	private float mLastX = 0;
	private float mLastY = 0;
	private float mLastZ = 0;
	private boolean mInitialized = false;
	private SensorManager mSensorManager;
	private Sensor mAccel;

	@Override
	public void onSensorChanged(SensorEvent event) {

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			mInitialized = true;
		}
		float deltaX = Math.abs(mLastX - x);
		float deltaY = Math.abs(mLastY - y);
		float deltaZ = Math.abs(mLastZ - z);

		if (deltaX > 0.8 || deltaY > 0.8 || deltaZ > 0.8) {
			mCamera.setFocus();
		}
		mLastX = x;
		mLastY = y;
		mLastZ = z;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	protected void onDestroy() {
		ButterKnife.unbind(this);
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

}