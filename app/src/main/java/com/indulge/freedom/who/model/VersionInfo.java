package com.indulge.freedom.who.model;

import java.io.Serializable;

/**
 * 版本信息
 * 
 * @author huangyue
 * 
 */
public class VersionInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8578761684883485851L;

	private String AppVersion = "";// 最新版本

	private String UpTitle = "";// 版本提示信息

	private String UpContext = "";// 更新信息

	private String UpUrl = "";// apk下载地址

	private boolean IsUpnotify;// 是否需要更新

	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}

	public String getUpTitle() {
		return UpTitle;
	}

	public void setUpTitle(String upTitle) {
		UpTitle = upTitle;
	}

	public String getUpContext() {
		return UpContext;
	}

	public void setUpContext(String upContext) {
		UpContext = upContext;
	}

	public String getUpUrl() {
		return UpUrl;
	}

	public void setUpUrl(String upUrl) {
		UpUrl = upUrl;
	}

	public boolean isIsUpnotify() {
		return IsUpnotify;
	}

	public void setIsUpnotify(boolean isUpnotify) {
		IsUpnotify = isUpnotify;
	}

	@Override
	public String toString() {
		return "VersionInfo [AppVersion=" + AppVersion + ", UpTitle=" + UpTitle
				+ ", UpContext=" + UpContext + ", UpUrl=" + UpUrl
				+ ", IsUpnotify=" + IsUpnotify + "]";
	}

}