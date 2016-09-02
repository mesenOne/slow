package com.indulge.freedom.who.db;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.indulge.freedom.who.model.City;


/**
 * 数据库工具类
 * 
 * @author huangyue
 * 
 */
public class DBService {
	private static final String TAG = "智慧汽车数据库";

	private DBOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;

	public DBService(Context context) {
		mOpenHelper = new DBOpenHelper(context);
		mDatabase = mOpenHelper.getWritableDatabase();
	}

	// TODO 断点下载
	/**
	 * 保存下载apk的断点信息
	 */
	public void saveMark(String downLoadUrl, int threadId, long mark) {
		Log.i(TAG, "保存下载" + threadId + "的断点信息");
		ContentValues values = new ContentValues();
		values.put("downloadUrl", downLoadUrl);
		values.put("threadId", threadId);
		values.put("downloadMark", mark);
		if (has(threadId)) {
			mDatabase.update(DBOpenHelper.DOWNLOAD_APK, values, "threadId = ?",
					new String[] { threadId + "" });
		} else {
			mDatabase.insert(DBOpenHelper.DOWNLOAD_APK, null, values);
		}
	}

	/**
	 * 获取下载apk的断点信息
	 * 
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	public long getMark(int threadId) {
		Log.i(TAG, "获取" + threadId + "下载的断点位置");
		Cursor cursor = mDatabase.query(DBOpenHelper.DOWNLOAD_APK, null,
				" threadId = ? ", new String[] { threadId + "" }, null, null,
				null);
		long mark = 0;
		if (cursor.moveToNext()) {
			mark = cursor.getInt(cursor.getColumnIndex("downloadMark"));
		} else
			mark = 0;
		cursor.close();
		return mark;
	}

	private boolean has(int threadId) {
		Cursor cursor = mDatabase.query(DBOpenHelper.DOWNLOAD_APK, null,
				" threadId = ? ", new String[] { threadId + "" }, null, null,
				null);
		boolean has = cursor.moveToNext();
		cursor.close();
		return has;
	}

	// TODO 搜索历史
	/**
	 * 保存产品搜索历史
	 * 
	 * @param productSearchValue
	 */
	public void saveProductSearchHistory(String productSearchValue) {
		if (!hasProductSearchHistory(productSearchValue)) {
			ContentValues values = new ContentValues();
			values.put("productSearchValue", productSearchValue);
			values.put("productSearchTime", System.currentTimeMillis());
			mDatabase.insert(DBOpenHelper.PRODUCT_SEARCH_HISTORY, null, values);
		} else {
			ContentValues values = new ContentValues();
			values.put("productSearchTime", System.currentTimeMillis());
			mDatabase
					.update(DBOpenHelper.PRODUCT_SEARCH_HISTORY, values,
							"productSearchValue=?",
							new String[] { productSearchValue });
		}
	}

	/**
	 * 获取产品搜索历史
	 * 
	 * @return
	 */
	public ArrayList<String> getProductSearchHistory() {
		ArrayList<String> productSearchHistory = new ArrayList<String>();
		Cursor cursor = mDatabase.query(DBOpenHelper.PRODUCT_SEARCH_HISTORY,
				null, null, null, null, null, "productSearchTime desc");
		while (cursor.moveToNext()) {
			String productSearchValue = cursor.getString(cursor
					.getColumnIndex("productSearchValue"));
			productSearchHistory.add(productSearchValue);
		}
		cursor.close();
		return productSearchHistory;
	}

	/**
	 * 清空搜索历史
	 */
	public void clearSearchHistory() {
		mDatabase.delete(DBOpenHelper.PRODUCT_SEARCH_HISTORY, null, null);
	}

	/**
	 * 判断是否已经缓存了
	 * 
	 * @param productSearch
	 * @return
	 */
	private boolean hasProductSearchHistory(String productSearch) {
		Cursor cursor = mDatabase.query(DBOpenHelper.PRODUCT_SEARCH_HISTORY,
				null, " productSearchValue = ? ",
				new String[] { productSearch }, null, null, null);
		boolean has = cursor.moveToNext();
		cursor.close();
		return has;
	}

	// TODO 城市搜索历史
	/**
	 * 保存城市搜索历史
	 *
	 */
	public void saveCitySearchHistory(City city) {
		if (city != null) {
			if (!hasCitySearchHistory(city)) {
				ContentValues values = new ContentValues();
				values.put("citySearchValue", city.getsCityName());
				values.put("citySearchTime", System.currentTimeMillis());
				mDatabase
						.insert(DBOpenHelper.CITY_SEARCH_HISTORY, null, values);
			} else {
				ContentValues values = new ContentValues();
				values.put("citySearchTime", System.currentTimeMillis());
				mDatabase.update(DBOpenHelper.CITY_SEARCH_HISTORY, values,
						"citySearchValue=?",
						new String[] { city.getsCityName() });
			}
		}
	}

	/**
	 * 获取缓存的城市搜索历史
	 * 
	 * @return
	 */
	public ArrayList<City> getCitySearchHistory() {
		ArrayList<City> citySearchHistory = new ArrayList<City>();
		Cursor cursor = mDatabase.query(DBOpenHelper.CITY_SEARCH_HISTORY, null,
				null, null, null, null, "citySearchTime desc");
		while (cursor.moveToNext()) {
			String cityName = cursor.getString(cursor
					.getColumnIndex("citySearchValue"));
			if (!TextUtils.isEmpty(cityName)) {
				City city = new City();
				city.setsCityName(cityName);
				citySearchHistory.add(city);
			}
		}
		cursor.close();
		return citySearchHistory;
	}

	/**
	 * 清空城市搜索历史
	 */
	public void clearCitySearchHistory() {
		mDatabase.delete(DBOpenHelper.CITY_SEARCH_HISTORY, null, null);
	}

	/**
	 * 判断是否已经缓存该城市了
	 * 
	 * @param city
	 * @return
	 */
	private boolean hasCitySearchHistory(City city) {
		boolean has = false;
		if (city != null) {
			Cursor cursor = mDatabase.query(DBOpenHelper.CITY_SEARCH_HISTORY,
					null, " citySearchValue = ? ",
					new String[] { city.getsCityName() }, null, null, null);
			has = cursor.moveToNext();
			cursor.close();
		}
		return has;
	}
}
