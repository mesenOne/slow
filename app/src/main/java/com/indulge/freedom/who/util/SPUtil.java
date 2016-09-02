package com.indulge.freedom.who.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 文件缓存
 * 
 * @author huangyue
 * 
 */
public class SPUtil {

	private static final String SMART_CAR_SP = "smartCar_sp";// 缓存文件名

	private static SharedPreferences sp;

	private static final String FIRST_IN = "firstIn";// 首次进入应用
	private static final String AD_POPUP_WINDOW = "ad_popup_window";// 首次进入应用
	
	

	private static final String USER_ID = "userId";// 用户id
	private static final String TOKEN = "token";// token
	private static final String DEVICE_TOKEN = "deviceToken";// 登录设备的服务端唯一标识
	private static final String DEVICE_ID = "deviceId";// 设备id
	private static final String REAL_NAME = "realName";// 用户名
	private static final String ALIAS = "alias";// 推送别名
	private static final String NICK_NAME = "nickName";// 用户昵称
	private static final String PHONE = "phone";// 手机号
	private static final String AVATAR = "avatar";// 头像的url地址
	private static final String BROWSE_COUNT = "browseCount";// 浏览量
	private static final String USER_NO = "sUserNo";// 用户编号
	private static final String LOCAL_CITY = "localCity";// 用户编号
	private static final String LOCAL_CITY_ID = "localCityId";// 用户编号

	/* 缓存二手车首页和买车页面的最新时间戳 */
	private static final String USED_CAR_TIME = "usedCarTime";
	private static final String BUY_CAR_TIME = "buyCarTime";
	private static final String COUPON_NEW_TIME = "newCouponTime";

	private static final String UPDATE_APK_VERSION = "updateApkVersion";// 缓存最新apk的版本
	
	private static final String My_REFUEL_CARD_DEFAULT="myRefuelCardDefault";

	private static SharedPreferences getSP(Context context) {
		if (sp == null)
			sp = context.getSharedPreferences(SMART_CAR_SP,
					Context.MODE_PRIVATE);
		return sp;
	}

	// TODO 记录最新apk版本
	/**
	 * 保存最新apk版本
	 * 
	 * @param context
	 * @param newApkVersion
	 */
	public static void saveUpdateApkVersion(Context context,
			String newApkVersion) {
		saveString(context, UPDATE_APK_VERSION, newApkVersion);
	}

	/**
	 * 获取最新apk版本
	 * 
	 * @param context
	 * @return
	 */
	public static String getUpdateApkVersion(Context context) {
		return getString(context, UPDATE_APK_VERSION, "");
	}

	// TODO 记录是否首次进入app
	/**
	 * 是否首次登录
	 * 
	 * @param context
	 * @param firstIn
	 */
	public static void saveFirstIn(Context context, Boolean firstIn) {
		saveBoolean(context, FIRST_IN, firstIn);
	}

	/**
	 * 是否首次登录
	 * 
	 * @param context
	 * @return
	 */
	public static Boolean getFirstIn(Context context) {
		return getBoolean(context, FIRST_IN, true);
	}
	/**
	 * 首次弹出广告框
	 * 
	 * @param context
	 * @param firstIn
	 */
	public static void saveAddPopupWindow(Context context, Boolean firstIn) {
		saveBoolean(context, AD_POPUP_WINDOW, firstIn);
	}
	
	/**
	 * 是否首次登录
	 * 
	 * @param context
	 * @return
	 */
	public static Boolean getAddPopupWindow(Context context) {
		return getBoolean(context, AD_POPUP_WINDOW, true);
	}

	// TODO 用户id缓存
	/**
	 * 保存用户id
	 * 
	 * @param context
	 * @param userId
	 */
	public static void saveUserId(Context context, String userId) {
		saveString(context, USER_ID, userId);
	}

	/**
	 * 获取用户id
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserId(Context context) {
		return getString(context, USER_ID, "");
	}

	// TODO token缓存
	/**
	 * 保存token
	 * 
	 * @param context
	 * @param token
	 */
	public static void saveToken(Context context, String token) {
		saveString(context, TOKEN, token);
	}

	/**
	 * 获取token
	 * 
	 * @param context
	 * @return
	 */
	public static String getToken(Context context) {
		return getString(context, TOKEN, "");
	}
	
	// TODO 定位城市缓存
	/**
	 * 保存token
	 * 
	 * @param context
	 */
	public static void saveLocalCity(Context context, String localCity) {
		saveString(context, LOCAL_CITY, localCity);
	}
	
	/**
	 * 获取token
	 * 
	 * @param context
	 * @return
	 */
	public static String getLocalCity(Context context) {
		return getString(context, LOCAL_CITY, "");
	}

	// TODO deviceToken缓存
	/**
	 * 保存deviceToken
	 * 
	 * @param context
	 * @param token
	 */
	public static void saveDeviceToken(Context context, String token) {
		saveString(context, DEVICE_TOKEN, token);
	}

	/**
	 * 获取deviceToken
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceToken(Context context) {
		return getString(context, DEVICE_TOKEN, "");
	}

	// TODO 缓存设备id
	/**
	 * 保存设备id
	 * 
	 * @param context
	 * @param deviceId
	 */
	public static void saveDeviceId(Context context, String deviceId) {
		saveString(context, DEVICE_ID, deviceId);
	}

	/**
	 * 获取设备id
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		return getString(context, DEVICE_ID, "");
	}

	// TODO 用户名缓存
	/**
	 * 保存用户名
	 * 
	 * @param context
	 * @param realName
	 */
	public static void saveRealName(Context context, String realName) {
		saveString(context, REAL_NAME, realName);
	}

	/**
	 * 获取用户名
	 * 
	 * @param context
	 * @return
	 */
	public static String getRealName(Context context) {
		return getString(context, REAL_NAME, "");
	}

	// TODO 用户编号缓存
	/**
	 * 保存用户编号
	 * 
	 * @param context
	 */
	public static void saveUseNo(Context context, String sUserNo) {
		saveString(context, USER_NO, sUserNo);
	}

	/**
	 * 获取用户编号
	 * 
	 * @param context
	 * @return
	 */
	public static String getUseNo(Context context) {
		return getString(context, USER_NO, "");
	}

	// TODO 用户昵称缓存
	/**
	 * 保存用户昵称
	 * 
	 * @param context
	 * @param nickName
	 */
	public static void saveNickName(Context context, String nickName) {
		saveString(context, NICK_NAME, nickName);
	}

	/**
	 * 获取用户昵称
	 * 
	 * @param context
	 * @return
	 */
	public static String getNickName(Context context) {
		return getString(context, NICK_NAME, "");
	}

	// TODO 推送别名缓存
	/**
	 * 保存推送别名
	 * 
	 * @param context
	 * @param alias
	 */
	public static void saveAlias(Context context, String alias) {
		saveString(context, ALIAS, alias);
	}

	/**
	 * 获取推送别名
	 * 
	 * @param context
	 * @return
	 */
	public static String getAlias(Context context) {
		return getString(context, ALIAS, "");
	}

	// TODO 用户手机号缓存
	/**
	 * 保存用户手机号
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void savePhone(Context context, String phoneNumber) {
		saveString(context, PHONE, phoneNumber);
	}

	/**
	 * 获取用户手机号
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhone(Context context) {
		return getString(context, PHONE, "");
	}

	// TODO 用户头像url地址缓存
	/**
	 * 保存用户头像url地址
	 * 
	 * @param context
	 * @param avatar
	 */
	public static void saveAvatar(Context context, String avatar) {
		saveString(context, AVATAR, avatar);
	}

	/**
	 * 获取用户头像url地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getAvatar(Context context) {
		return getString(context, AVATAR, "");
	}

	// TODO 缓存浏览量
	/**
	 * 保存浏览量
	 * 
	 * @param context
	 * @param browseCount
	 */
	public static void saveBrowseCount(Context context, int browseCount) {
		saveInt(context, BROWSE_COUNT, browseCount);
	}

	/**
	 * 
	 * 获取浏览量
	 * @param context
	 * @return
	 */
	public static int getBrowseCount(Context context) {
		return getInt(context, BROWSE_COUNT, 0);
	}
	

	
	/**
	 * 
	 * 获取加油卡默认项
	 * 
	 * @param context
	 * @return
	 */
	public static int getMyDefaultRefuelCard(Context context) {
		return getInt(context,My_REFUEL_CARD_DEFAULT, 0);
	}
	
	/**
	 * 保存定位城市ID
	 * 
	 * @param context
	 */
	public static void saveLocalCityId(Context context, long cityId) {
		saveLong(context, LOCAL_CITY_ID, cityId);
	}
	
	/**
	 * 
	 * 获取定位城市ID
	 * 
	 * @param context
	 * @return
	 */
	public static long getLocalCityId(Context context) {
		return getLong(context,LOCAL_CITY_ID, 0L);
	}

	// TODO 缓存二手车首页的时间戳
	/**
	 * 保存二手车首页的时间戳
	 * 
	 * @param context
	 * @param time
	 */
	public static void saveUsedCarTime(Context context, long time) {
		if (getUsedCarTime(context) < time) {
			saveLong(context, USED_CAR_TIME, time);
		}
	}

	/**
	 * 获取二手车首页的时间戳
	 * 
	 * @param context
	 * @return
	 */
	public static long getUsedCarTime(Context context) {
		return getLong(context, USED_CAR_TIME, (long) 0);
	}

	// TODO 缓存买车页面的时间戳
	/**
	 * 保存买车页面的时间戳
	 * 
	 * @param context
	 * @param time
	 */
	public static void saveBuyCarTime(Context context, long time) {
		if (getBuyCarTime(context) < time) {
			saveLong(context, BUY_CAR_TIME, time);
		}
	}

	/**
	 * 获取买车页面的时间戳
	 * 
	 * @param context
	 * @return
	 */
	public static long getBuyCarTime(Context context) {
		return getLong(context, BUY_CAR_TIME, (long) 0);
	}
	
	// TODO 缓存买车页面的时间戳
	/**
	 * 保存违章页面新的优惠券的时间戳
	 * 
	 * @param context
	 * @param time
	 */
	public static void saveNewCouponTime(Context context, long time) {
		if (getNewCouponTime(context) < time) {
			saveLong(context, COUPON_NEW_TIME, time);
		}
	}
	
	/**
	 * 获取违章页面新的优惠券的时间戳
	 * 
	 * @param context
	 * @return
	 */
	public static long getNewCouponTime(Context context) {
		return getLong(context, COUPON_NEW_TIME, (long) 0);
	}

	// TODO 缓存数据类型
	/*****************************************************************************************************************************************
	 * 缓存数据类型操作
	 *****************************************************************************************************************************************/
	/**
	 * 缓存字符串数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	private static void saveString(Context context, String key, String value) {
		sp = getSP(context);
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 获取字符串的缓存数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	private static String getString(Context context, String key, String defValue) {
		sp = getSP(context);
		return sp.getString(key, defValue);
	}

	/**
	 * 缓存整型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	private static void saveInt(Context context, String key, int value) {
		sp = getSP(context);
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 获取整型的缓存数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	private static int getInt(Context context, String key, int defValue) {
		sp = getSP(context);
		return sp.getInt(key, defValue);
	}

	/**
	 * 缓存长整型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	private static void saveLong(Context context, String key, Long value) {
		sp = getSP(context);
		sp.edit().putLong(key, value).commit();
	}

	/**
	 * 获取长整型的缓存数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	private static Long getLong(Context context, String key, Long defValue) {
		sp = getSP(context);
		return sp.getLong(key, defValue);
	}

	/**
	 * 缓存布尔型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	private static void saveBoolean(Context context, String key, boolean value) {
		sp = getSP(context);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 获取布尔型的缓存数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	private static boolean getBoolean(Context context, String key,
			boolean defValue) {
		sp = getSP(context);
		return sp.getBoolean(key, defValue);
	}

}