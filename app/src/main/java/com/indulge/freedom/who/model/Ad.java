package com.indulge.freedom.who.model;

import java.io.Serializable;

/**
 * 广告信息
 * 
 * @author huangyue
 * 
 */
public class Ad implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5438685885610187631L;

	private boolean StartPageIsDownLoad;// 是否需要加载广告页 false 不需要 true 需要

	private String StartPageName = "";// 启动页名称

	private String AdvertUrl = "";// 广告跳转链接地址

	private String StartPageImgNo4S = "";// 广告图片url地址

	public boolean isStartPageIsDownLoad() {
		return StartPageIsDownLoad;
	}

	public void setStartPageIsDownLoad(boolean startPageIsDownLoad) {
		StartPageIsDownLoad = startPageIsDownLoad;
	}

	public String getStartPageName() {
		return StartPageName;
	}

	public void setStartPageName(String startPageName) {
		StartPageName = startPageName;
	}

	public String getAdvertUrl() {
		return AdvertUrl;
	}

	public void setAdvertUrl(String advertUrl) {
		AdvertUrl = advertUrl;
	}

	public String getStartPageImgNo4S() {
		return StartPageImgNo4S;
	}

	public void setStartPageImgNo4S(String startPageImgNo4S) {
		StartPageImgNo4S = startPageImgNo4S;
	}

	@Override
	public String toString() {
		return "Ad [StartPageIsDownLoad=" + StartPageIsDownLoad
				+ ", StartPageName=" + StartPageName + ", AdvertUrl="
				+ AdvertUrl + ", StartPageImgNo4S=" + StartPageImgNo4S + "]";
	}

}
