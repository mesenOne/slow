package com.indulge.freedom.who.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.indulge.freedom.who.util.Tools;


/**
 * SQLite数据库辅助类
 * 
 * @author huangyue
 * 
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE = "smart_car.db";
	public static final String DOWNLOAD_APK = "apk";// 用来缓存apk多线程信息的表
	public static final String PRODUCT_SEARCH_HISTORY = "product_search_history";// 用来缓存搜索历史缓存
	public static final String CITY_SEARCH_HISTORY = "city_search_history";// 用来缓存城市搜索历史缓存

	public DBOpenHelper(Context context) {
		super(context, DATABASE, null, Tools.getVersionCode(context));
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String downloadApk = "create table if not exists "
				+ DOWNLOAD_APK
				+ "(threadId integer primary key,downloadUrl varchar(100),downloadMark integer)";
		String productSearchHistory = "create table if not exists "
				+ PRODUCT_SEARCH_HISTORY
				+ "(productSearchValue varchar(100),productSearchTime integer)";
		String citySearchHistory = "create table if not exists "
				+ CITY_SEARCH_HISTORY
				+ "(citySearchValue varchar(100),citySearchTime integer)";
		db.execSQL(downloadApk);
		db.execSQL(productSearchHistory);
		db.execSQL(citySearchHistory);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + DOWNLOAD_APK);
		db.execSQL("drop table if exists " + PRODUCT_SEARCH_HISTORY);
		db.execSQL("drop table if exists " + CITY_SEARCH_HISTORY);
		onCreate(db);
	}

}
