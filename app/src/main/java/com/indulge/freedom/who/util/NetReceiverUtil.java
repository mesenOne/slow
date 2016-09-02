package com.indulge.freedom.who.util;

import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.indulge.freedom.who.interf.NetMonitor;


/**
 * 实时监听网络广播的注册、解绑工具
 * 
 * @author huangyue
 * 
 */
public class NetReceiverUtil {
	private static final String TAG = "监控网络";

	private static Map<String, BroadcastReceiver> mReceiverMap = new HashMap<String, BroadcastReceiver>();

	/**
	 * 设置网络实时监听(需要在Activity的onDestroy方法调用unRegisterNetReceiver方法)
	 * 
	 * @param netMonitor
	 */
	public static void setNetMonitor(Context context,
			final NetMonitor netMonitor) {
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 添加接收网络连接状态改变的Action
		BroadcastReceiver netReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context contxt, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					ConnectivityManager connectivityManager = (ConnectivityManager) contxt
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo wifi = connectivityManager
							.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					NetworkInfo mobie = connectivityManager
							.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
					if (netMonitor != null) {
						netMonitor.networkInfo(wifi, mobie);
						netMonitor.isWifiNetConnected(true);
						netMonitor.isMobieNetConnected(true);
						if (wifi.isConnected() || mobie.isConnected()) {
							netMonitor.isConnected(true);
							if (!wifi.isConnected() && mobie.isConnected()) {
								netMonitor.isMobieNetConnected(true);
								netMonitor.isWifiNetConnected(false);
							} else if (!mobie.isConnected()
									&& wifi.isConnected()) {
								netMonitor.isWifiNetConnected(true);
								netMonitor.isMobieNetConnected(false);
							} else if (mobie.isConnected()
									&& wifi.isConnected()) {
								netMonitor.isWifiNetConnected(true);
								netMonitor.isMobieNetConnected(true);
							}
						} else {
							netMonitor.isConnected(false);
							netMonitor.isWifiNetConnected(false);
							netMonitor.isMobieNetConnected(false);
						}
					}
				}
			}
		};
		String key = context.getClass().getName();
		if (!mReceiverMap.containsKey(key)) {
			mReceiverMap.put(key, netReceiver);
		}
		context.registerReceiver(netReceiver, mFilter);
		Log.i(TAG, key + "页面:成功绑定网络实时监听");
	}

	/**
	 * 解除网络监听器的绑定
	 * 
	 * @param context
	 */
	public static void unRegisterNetReceiver(Context context) {
		String key = context.getClass().getName();
		if (mReceiverMap.containsKey(key)) {
			context.unregisterReceiver(mReceiverMap.get(key));
			Log.i(TAG, key + "页面:成功解除网络实时监听的绑定");
			mReceiverMap.remove(key);
		}
	}

}
