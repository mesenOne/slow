package com.indulge.freedom.who.interf;

import android.net.NetworkInfo;

/**
 * 网络监听器回调
 * 
 * @author huangyue
 * 
 */
public abstract class NetMonitor {

	public void networkInfo(NetworkInfo wifiInfo, NetworkInfo mobieInfo) {

	}

	public abstract void isConnected(boolean isConnected);

	public void isWifiNetConnected(boolean isConnected) {

	}

	public void isMobieNetConnected(boolean isConnected) {

	}
}
