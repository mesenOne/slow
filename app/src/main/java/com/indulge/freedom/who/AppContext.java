package com.indulge.freedom.who;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.indulge.freedom.who.config.Api;
import com.indulge.freedom.who.permission.PermissionListener;
import com.indulge.freedom.who.permission.PermissionManager;
import com.indulge.freedom.who.util.LocalImageHelper;
import com.indulge.freedom.who.util.SPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.helper.BmobNative;
import cn.jpush.android.api.JPushInterface;


public class AppContext extends Application {

	// 保存每次选取图片返回的localFile
	public static List<LocalImageHelper.LocalFile> listFiles = new ArrayList<LocalImageHelper.LocalFile>();
	public static List<String> filePaths = new ArrayList<String>();

	// 文件保存路径
	private static final File DIR = new File(
			Environment.getExternalStorageDirectory(), "smartCar");
	public static final String PICTURE = "picture";
	// singleton
	private static AppContext context = null;

	private Display display;
	public static boolean startedApp = false;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

		init();
	}

	public static AppContext getInstance() {
		return context;
	}

	/**
	 * 初始化
	 */
	private void init() {

		/** 初始极光 */
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);


		//提供以下两种方式进行初始化操作：
//		//第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
//		BmobConfig config =new BmobConfig.Builder(this)
//		//设置appkey
//		.setApplicationId(Api.BMOB_APPID)
//		//请求超时时间（单位为秒）：默认15s
//		.setConnectTimeout(30)
//		//文件分片上传时每片的大小（单位字节），默认512*1024
//		.setUploadBlockSize(1024*1024)
//		//文件的过期时间(单位为秒)：默认1800s
//		.setFileExpiration(5500)
//		.build();
//		Bmob.initialize(config);

		//初始化BmobSDK
		Bmob.initialize(this, Api.BMOB_APPID);

		// 本地图片辅助类初始化

		// 微信 appid appsecret

		// 新浪微博 appkey appsecret

		// QQ和Qzone appid appkey
	}

	/**
	 * 创建app文件夹
	 */
	private static void createDir() {
		if (!DIR.exists()) {
			DIR.mkdirs();
		}
	}

	/**
	 * 获取app文件夹
	 * 
	 * @return
	 */
	public static File getAppDir() {
		if (DIR != null && DIR.exists()) {
		} else {
			createDir();
		}
		return DIR;
	}

	/**
	 * 获取图片缓存文件夹
	 * 
	 * @return
	 */
	public static File getPictureDir() {
		File pictureDir = new File(getAppDir(), PICTURE);
		if (!pictureDir.exists()) {
			pictureDir.mkdirs();
		}
		return pictureDir;
	}

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
//		if (!TextUtils.isEmpty(SPUtil.getUserId(context))
//				&& !TextUtils.isEmpty(SPUtil.getToken(context))) {
//			return true;
//		}
		return false;
	}

	/**
	 * 退出登录清除用户缓存
	 */
	public static void logout() {
		SPUtil.saveToken(context, "");
		SPUtil.saveUserId(context, "");
		SPUtil.savePhone(context, "");
		SPUtil.saveRealName(context, "");
		SPUtil.saveNickName(context, "");
		SPUtil.saveAvatar(context, "");
		SPUtil.saveBrowseCount(context, 0);
		SPUtil.saveDeviceToken(context, "");
		SPUtil.saveUseNo(context, "");


	}

	public String getCachePath() {
		File cacheDir;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			cacheDir = getExternalCacheDir();
		else
			cacheDir = getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		return cacheDir.getAbsolutePath();
	}




}