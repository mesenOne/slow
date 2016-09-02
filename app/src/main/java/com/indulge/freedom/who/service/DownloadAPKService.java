package com.indulge.freedom.who.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.config.Constant;
import com.indulge.freedom.who.db.DBService;
import com.indulge.freedom.who.download.DownloadTask;

import com.indulge.freedom.who.interf.DownloadApkListener;
import com.indulge.freedom.who.model.VersionInfo;
import com.indulge.freedom.who.util.NetUtil;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.util.ToastUtil;


/**
 * 下载apk的服务
 * 
 * @author huangyue
 * 
 */
public class DownloadAPKService extends Service {
	private static final String TAG = "下载服务";
	public static final String APK = "智慧汽车网.apk";// apk文件名
	public static final String DOWNLOAD_ACTION = "downloadAPK";// 接收下载apk服务的广播action

	public static final String STATUS = "downloadStatus";// 下载状态action
	public static final String PROGRESS = "progress";// 进度action
	public static final int PAUSE = 0;// 下载正常状态
	public static final int LOADING = 1;// 下载正常状态
	public static final int COMPLETE = 2;// 下载完成状态
	public static final int FAIL = 3;// 下载失败状态
	public static int sDownloadStatus = PAUSE;// 状态

	private DBService mDbService;
	private String mDownloadUrl = "";
	private RandomAccessFile mAccessFile;
	private DownloadTask mDownloadTask;
	private File mApk;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("下载服务", "onCreate");
		mDbService = new DBService(getApplicationContext());
		mApk = new File(AppContext.getAppDir(), APK);

	}

	private void download() {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(mDownloadUrl);
					HttpURLConnection http = (HttpURLConnection) url
							.openConnection();
					http.setConnectTimeout(3000); // 设置连接超时
					http.setReadTimeout(3000);
					http.connect();
					long apkSize = http.getContentLength();
					if (mApk.exists()) {
						if (apkSize == mDbService.getMark(0)) {
							Intent intent = new Intent(DOWNLOAD_ACTION);
							intent.putExtra(STATUS, COMPLETE);
							sendBroadcast(intent);
							return;
						}
						if (mDbService.getMark(0) == 0) {
							mApk.delete();
						}
					} else {
						mDbService.saveMark(mDownloadUrl, 0, 0);
					}
					try {
						mAccessFile = new RandomAccessFile(mApk, "rwd");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					mAccessFile.setLength(apkSize);
					// long per = apkSize / 3;
					// if (mDbService.getMark(1) == 0) {
					// mDbService.saveMark(mDownloadUrl, 1, per);
					// }
					// if (mDbService.getMark(2) == 0) {
					// mDbService.saveMark(mDownloadUrl, 2, per * 2);
					// }
					/* 单线程下载 */
					mDownloadTask = new DownloadTask(0, mAccessFile,
							mDownloadUrl, mDbService.getMark(0), apkSize,
							mDbService, new DownloadApkListener() {

								@Override
								public void load(int progress) {
									Intent intent = new Intent(DOWNLOAD_ACTION);
									if (progress == 100) {
										intent.putExtra(STATUS, COMPLETE);
										sendBroadcast(intent);
										installApk();
										stopSelf();
									} else if (progress < 100) {
										intent.putExtra(STATUS, LOADING);
										intent.putExtra(PROGRESS, progress);
										sendBroadcast(intent);
									}
								}

								@Override
								public void pause() {
									Intent intent = new Intent(DOWNLOAD_ACTION);
									intent.putExtra(STATUS, PAUSE);
									sendBroadcast(intent);
									stopSelf();
								}

								@Override
								public void fail() {
									Intent intent = new Intent(DOWNLOAD_ACTION);
									intent.putExtra(STATUS, FAIL);
									sendBroadcast(intent);
									stopSelf();
								}

							});
					new Thread(mDownloadTask).start();
					// new Thread(new DownloadTask(0, mAccessFile, mDownloadUrl,
					// mDbService.getMark(1), per * 2 - 1, mDbService))
					// .start();
					// new Thread(new DownloadTask(0, mAccessFile, mDownloadUrl,
					// mDbService.getMark(2), apkSize, mDbService))
					// .start();
				} catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "异常");
				}
			};
		}.start();
	}

	private void fail() {
		Intent intent = new Intent(DOWNLOAD_ACTION);
		intent.putExtra(STATUS, FAIL);
		sendBroadcast(intent);
		stopSelf();
	}

	@Override
	public void onDestroy() {
		Log.i("下载服务", "onDestroy");
		if (mDownloadTask != null) {
			mDownloadTask.setPause();
		}
		stopSelf();
		super.onDestroy();
	}

	/**
	 * 安装apk
	 */
	protected void installApk() {
		File apkfile = new File(AppContext.getAppDir(), APK);
		if (!apkfile.exists()) {
			return;
		}
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkfile),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

}