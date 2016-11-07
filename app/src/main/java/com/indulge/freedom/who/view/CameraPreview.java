package com.indulge.freedom.who.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.util.CameraUtils;
import com.indulge.freedom.who.util.ConversionUtil;
import com.indulge.freedom.who.util.ImageUtils;
import com.indulge.freedom.who.util.LocalImageHelper;
import com.indulge.freedom.who.util.ScreenUtils;

import static com.indulge.freedom.who.ui.activity.publishActivity.listFiles;


/**
 * 自定义相机
 * 
 * @author huhuan
 * 
 */
public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback, AutoFocusCallback {
	private static final String TAG = "CameraPreview";
	private MediaPlayer shootMP;
	private boolean mPrepare = true;
	public static List<String> mList;
	public Map<String, Bitmap> bitmaps;

	public static boolean canOpenCamera = true;
	private int viewWidth = 0;
	private int viewHeight = 0;

	/** 监听接口 */

	private SurfaceHolder holder;
	private Camera camera;
	private FocusView mFocusView;

	/** 当前屏幕旋转角度 */
	private int mOrientation = 0;

	// Preview类的构造方法
	@SuppressLint("ClickableViewAccessibility")
	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		if (!safeCameraOpen()) {
			canOpenCamera = false;
		}
		// 获得SurfaceHolder对象
		holder = getHolder();
		// 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
		holder.addCallback(this);
		setOnTouchListener(onTouchListener);
	}

	private void init(Context context) {
		if (!AppContext.getAppDir().exists()) {
			AppContext.getAppDir().mkdirs();
		}
		mList = new ArrayList<String>();
		bitmaps = new HashMap<String, Bitmap>();

	}

	public boolean safeCameraOpen() {
		boolean qOpened = false;
		camera = null;
		try {
			camera = getCameraInstance();
			qOpened = (camera != null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qOpened;
	}

	// 在surface创建时激发
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e(TAG, "==surfaceCreated==");
		if (camera == null) {
			camera = getCameraInstance();
		}
		try {
			// 设置用于显示拍照摄像的SurfaceHolder对象
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
			// 释放手机摄像头
			camera.release();
			camera = null;
		}
		updateCameraParameters();
		if (camera != null) {
			camera.startPreview();
		}
		setFocus();

	}

	// 在surface销毁时激发
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e(TAG, "==surfaceDestroyed==");
		// 释放手机摄像头
		camera.release();
		camera = null;
	}

	// 在surface的大小发生改变时激发
	public void surfaceChanged(final SurfaceHolder holder, int format, int w,
			int h) {
		// stop preview before making changes
		try {
			camera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}
		// set preview size and make any resize, rotate or
		// reformatting changes here
		updateCameraParameters();
		// start preview with new settings
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();

		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
		setFocus();
	}

	/**
	 * 点击显示焦点区域
	 */
	OnTouchListener onTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int width = mFocusView.getWidth();
				int height = mFocusView.getHeight();
				mFocusView.setX(event.getX() - (width / 2));
				mFocusView.setY(event.getY() - (height / 2));
				mFocusView.beginFocus();
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				focusOnTouch(event);
			}
			return true;
		}
	};

	/**
	 * 启动屏幕朝向改变监听函数 用于在屏幕横竖屏切换时改变保存的图片的方向
	 */
	private void startOrientationChangeListener() {
		OrientationEventListener mOrEventListener = new OrientationEventListener(
				getContext()) {
			@Override
			public void onOrientationChanged(int rotation) {

				if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
					rotation = 0;
				} else if ((rotation > 45) && (rotation <= 135)) {
					rotation = 90;
				} else if ((rotation > 135) && (rotation <= 225)) {
					rotation = 180;
				} else if ((rotation > 225) && (rotation <= 315)) {
					rotation = 270;
				} else {
					rotation = 0;
				}
				if (rotation == mOrientation)
					return;
				mOrientation = rotation;
				updateCameraOrientation();
			}
		};
		mOrEventListener.enable();
	}

	private void updateCameraOrientation() {
		if (camera != null) {
			Camera.Parameters parameters = camera.getParameters();
			// rotation参数为 0、90、180、270。水平方向为0。
			int rotation = 90 + mOrientation == 360 ? 0 : 90 + mOrientation;
			parameters.setRotation(rotation);// 生成的图片转90°
			// 预览图片旋转90°
			camera.setDisplayOrientation(90);// 预览转90°
			camera.setParameters(parameters);
		}
	}

	/**
	 * 获取摄像头实例
	 * 
	 * @return
	 */
	private Camera getCameraInstance() {
		Camera c = null;
		try {
			if (c == null) {
				c = Camera.open(0); // attempt to get a Camera instance
			}
		} catch (Exception e) {
		}
		return c;
	}

	private void updateCameraParameters() {
		if (camera != null) {
			Camera.Parameters p = camera.getParameters();
			Camera.Parameters p1=p;
			setParameters(p);

			try {
				camera.setParameters(p);
			} catch (Exception e) {
				Camera.Size size = findBestPreviewSize(p1);// get top size
				// set max Picture Size
				Camera.Size pSize = findBestPictureSize(p1);
				p.setPreviewSize(size.width, size.height);
				p.setPictureSize(pSize.width, pSize.height);
				try {
					camera.setParameters(p);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		}
	}

	/**
	 * @param p
	 */
	private void setParameters(Camera.Parameters p) {
		List<String> focusModes = p.getSupportedFocusModes();
		if (focusModes
				.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
			p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		} else {
			p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		}

		long time = new Date().getTime();
		p.setGpsTimestamp(time);
		// 设置照片格式
		p.setPictureFormat(ImageFormat.JPEG);
		Camera.Size previewSize = getOptimalPreviewSize(
				p.getSupportedPreviewSizes(), ScreenUtils.getScreenHeight(getContext()), ScreenUtils.getScreenWidth(getContext()));
		p.setPreviewSize(previewSize.width, previewSize.height);
		Camera.Size pictureSize = getOptimalPreviewSize(
				p.getSupportedPictureSizes(), ScreenUtils.getScreenHeight(getContext()), ScreenUtils.getScreenWidth(getContext()));
		p.setPictureSize(pictureSize.width, pictureSize.height);
		p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
			camera.setDisplayOrientation(90);
			p.setRotation(90);
		}
		// 开启屏幕朝向监听
		startOrientationChangeListener();
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {

	}

	public void start() {
		if (camera != null) {
			camera.startPreview();
		}
	}

	public void stop() {
		if (camera != null) {
			camera.stopPreview();
		}
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		viewWidth = MeasureSpec.getSize(widthSpec);
		viewHeight = MeasureSpec.getSize(heightSpec);
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
	}

	/**
	 * 获取预览大小
	 * 
	 * @param sizes
	 * @param w
	 * @param h
	 * @return
	 */
	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w,
			int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	/**
	 * 找到最合适的显示分辨率 （防止预览图像变形）
	 * 
	 * @param parameters
	 * @return
	 */
	private Camera.Size findBestPreviewSize(Camera.Parameters parameters) {

		// 系统支持的所有预览分辨率
		String previewSizeValueString = null;
		previewSizeValueString = parameters.get("preview-size-values");

		if (previewSizeValueString == null) {
			previewSizeValueString = parameters.get("preview-size-value");
		}

		if (previewSizeValueString == null) { // 有些手机例如m9获取不到支持的预览大小 就直接返回屏幕大小
			return camera.new Size(
					CameraUtils.getScreenWH(getContext()).widthPixels,
					CameraUtils.getScreenWH(getContext()).heightPixels);
		}
		float bestX = 0;
		float bestY = 0;

		float tmpRadio = 0;
		float viewRadio = 0;

		if (viewWidth != 0 && viewHeight != 0) {
			viewRadio = Math.min(
					(float) ScreenUtils.getScreenWidth(getContext()),
					(float) ScreenUtils.getScreenHeight(getContext()))
					/ Math.max(
							(float) ScreenUtils.getScreenWidth(getContext()),
							(float) ScreenUtils.getScreenHeight(getContext()));
		}

		String[] COMMA_PATTERN = previewSizeValueString.split(",");
		for (String prewsizeString : COMMA_PATTERN) {
			prewsizeString = prewsizeString.trim();

			int dimPosition = prewsizeString.indexOf('x');
			if (dimPosition == -1) {
				continue;
			}

			float newX = 0;
			float newY = 0;

			try {
				newX = Float.parseFloat(prewsizeString
						.substring(0, dimPosition));
				newY = Float.parseFloat(prewsizeString
						.substring(dimPosition + 1));
			} catch (NumberFormatException e) {
				continue;
			}

			float radio = Math.min(newX, newY) / Math.max(newX, newY);
			if (tmpRadio == 0) {
				tmpRadio = radio;
				bestX = newX;
				bestY = newY;
			} else if (tmpRadio != 0
					&& (Math.abs(radio - viewRadio)) < (Math.abs(tmpRadio
							- viewRadio))) {
				tmpRadio = radio;
				bestX = newX;
				bestY = newY;
			}
		}

		if (bestX > 0 && bestY > 0) {
			return camera.new Size((int) bestX, (int) bestY);
		}
		return null;
	}
	  private Camera.Size findBestPictureSize(Camera.Parameters parameters) {
	        int  diff = Integer.MIN_VALUE;
	        String pictureSizeValueString = parameters.get("picture-size-values");
	             
	         // saw this on Xperia
	         if (pictureSizeValueString == null) {
	             pictureSizeValueString = parameters.get("picture-size-value");
	         }
	             
	         if(pictureSizeValueString == null) {
	             return  camera.new Size(ScreenUtils.getScreenWidth(getContext()),ScreenUtils.getScreenHeight(getContext()));
	         }
	             
	         Log.d("tag", "pictureSizeValueString : " + pictureSizeValueString);
	         int bestX = 0;
	         int bestY = 0;
	            
	            
	         for(String pictureSizeString : ",".split(pictureSizeValueString))
	         {
	             pictureSizeString = pictureSizeString.trim();
	                 
	             int dimPosition = pictureSizeString.indexOf('x');
	             if(dimPosition == -1){
	                 Log.e(TAG, "Bad pictureSizeString:"+pictureSizeString);
	                 continue;
	             }
	                 
	             int newX = 0;
	             int newY = 0;
	                 
	             try{
	                 newX = Integer.parseInt(pictureSizeString.substring(0, dimPosition));
	                 newY = Integer.parseInt(pictureSizeString.substring(dimPosition+1));
	             }catch(NumberFormatException e){
	                 Log.e(TAG, "Bad pictureSizeString:"+pictureSizeString);
	                 continue;
	             }
	                
	             Point screenResolution = new Point (ScreenUtils.getScreenWidth(getContext()),ScreenUtils.getScreenHeight(getContext()));
	                 
	             int newDiff = Math.abs(newX - screenResolution.x)+Math.abs(newY- screenResolution.y);
	                 if(newDiff == diff)
	                 {
	                     bestX = newX;
	                     bestY = newY;
	                     break;
	                 } else if(newDiff > diff){
	                     if((3 * newX) == (4 * newY)) {
	                         bestX = newX;
	                         bestY = newY;
	                         diff = newDiff;
	                     }
	                 }
	             }
	                 
	         if (bestX > 0 && bestY > 0) {
	            return camera.new Size(bestX, bestY);
	         }
	        return null;
	    }
	/**
	 * 设置焦点和测光区域
	 * 
	 * @param event
	 */
	public void focusOnTouch(MotionEvent event) {

		int[] location = new int[2];
		RelativeLayout relativeLayout = (RelativeLayout) getParent();
		relativeLayout.getLocationOnScreen(location);

		Rect focusRect = CameraUtils.calculateTapArea(mFocusView.getWidth(),
				mFocusView.getHeight(), 1f, event.getRawX(), event.getRawY(),
				location[0], location[0] + relativeLayout.getWidth(),
				location[1], location[1] + relativeLayout.getHeight());
		Rect meteringRect = CameraUtils.calculateTapArea(mFocusView.getWidth(),
				mFocusView.getHeight(), 1.5f, event.getRawX(), event.getRawY(),
				location[0], location[0] + relativeLayout.getWidth(),
				location[1], location[1] + relativeLayout.getHeight());

		Camera.Parameters parameters = camera.getParameters();
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

		if (parameters.getMaxNumFocusAreas() > 0) {
			List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
			focusAreas.add(new Camera.Area(focusRect, 1000));

			parameters.setFocusAreas(focusAreas);
		}

		if (parameters.getMaxNumMeteringAreas() > 0) {
			List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
			meteringAreas.add(new Camera.Area(meteringRect, 1000));

			parameters.setMeteringAreas(meteringAreas);
		}

		try {
			camera.setParameters(parameters);
		} catch (Exception e) {
		}
		camera.autoFocus(this);
	}

	/**
	 * 设置聚焦的图片
	 * 
	 * @param focusView
	 */
	public void setFocusView(FocusView focusView) {
		this.mFocusView = focusView;
	}

	/**
	 * 设置自动聚焦，并且聚焦的圈圈显示在屏幕中间位置
	 */
	public void setFocus() {
		if (!mFocusView.isFocusing()) {
			try {
				camera.autoFocus(this);
				mFocusView
						.setX((ScreenUtils.getScreenWidth(getContext()) - mFocusView
								.getWidth()) / 2);
				mFocusView.setY((viewHeight - mFocusView.getHeight()) / 2);
				mFocusView.beginFocus();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 删除指定的图片
	 * 
	 * @param name
	 */
	public void delete(String name) {
		if (mList.contains(name)) {
			int position = mList.indexOf(name);
			mList.remove(name);
			bitmaps.remove(name);
			listFiles.get(position).deleteSelf();
			listFiles.remove(position);
		}
	}

	/**
	 * 拍照
	 */
	public void takePic(final TakeCallBack callBack) {
		if (mPrepare) {
			mPrepare = false;
			camera.takePicture(new ShutterCallback() {
				@Override
				public void onShutter() {
					shootSound();
				}
			}, new PictureCallback() {

				@Override
				public void onPictureTaken(byte[] data, Camera camera) {

				}
			}, new PictureCallback() {

				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					Log.i(TAG, "oritention" + mOrientation);
					camera.stopPreview();
					// if(camera.getParameters().getPreviewSize().width)
					// 保存图片
					String name = System.currentTimeMillis() + ".jpg";
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					int degree = 0;
					if (1.0f * bm.getHeight() / bm.getWidth() == 9.0f / 16.0f) {
						switch (mOrientation) {
						case 0:
							degree = 90;
							bm = ImageUtils.zoomBitmap(bm, (int) (1.0f*viewHeight / bm.getHeight() * bm
									.getWidth()),viewHeight,
									 degree);
							bm=Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
									bm.getHeight()
											- ConversionUtil.dp2px(
													getContext(), 200));
							break;
						case 90:
							degree = 180;
							bm=ImageUtils.comp(getContext(),bm, 200, 100, degree);
							break;
						case 180:
							degree = 270;
							bm = ImageUtils.zoomBitmap(bm, (int) (1.0f*viewHeight / bm.getHeight() * bm
									.getWidth()),viewHeight,
									 degree);
							bm=Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
									bm.getHeight()
											- ConversionUtil.dp2px(
													getContext(), 200));
							break;
						case 270:
							degree = 0;
							bm=ImageUtils.comp(getContext(),bm, 200, 100, degree);
							break;

						default:
							break;
						}
					} else {
						if (mOrientation == 0 || mOrientation == 180) {
							bm = Bitmap.createBitmap(
									bm,
									0,
									0,
									bm.getWidth(),
									bm.getHeight()
											- ConversionUtil.dp2px(
													getContext(), 140));
							bm = ImageUtils.zoomBitmap(bm, viewWidth,
									(int) (1.0f*viewWidth * bm.getHeight() / bm
											.getWidth()), degree);
						}else{
							bm=ImageUtils.comp(getContext(),bm, 200, 100, 0);
						}

					}
					
					File file = ImageUtils.saveBitmap2file(bm, name, 50);
					bitmaps.put(name, bm);
					mList.add(name);
					callBack.callBack(bm, name);
					Uri uri = Uri.fromFile(file);
					LocalImageHelper.LocalFile localFile = new LocalImageHelper.LocalFile();
					localFile.setOriginalUri(uri.toString());
					localFile.setThumbnailUri(uri.toString());
					localFile.setFilepath(name);
					localFile.setAbsolutePath(file.getAbsolutePath());
					listFiles.add(localFile);
				}
			});
		}
	}

	/**
	 * 拍照的声音
	 */
	private void shootSound() {
		AudioManager manager = (AudioManager) getContext().getSystemService(
				Context.AUDIO_SERVICE);
		int volume = manager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
		if (volume != 0) {
			if (shootMP == null)
				shootMP = MediaPlayer
						.create(getContext(),
								Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
			if (shootMP != null)
				shootMP.start();
		}
	}

	/**
	 * 开启预览
	 */
	public void startSurface() {
		camera.startPreview();
		mPrepare = true;
	}

	public interface TakeCallBack {
		void callBack(Bitmap oraginalBitmap, String name);
	}

	public interface SavePictrue {
		void complete(boolean hasSaved);
	}

}
