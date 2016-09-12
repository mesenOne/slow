package com.indulge.freedom.who.model;

public class AdSupernatant {

	private String sName; // 名称
	private String sUrl; // 广告链接
	private String sImg; // 广告页图片
	private int iSort; // 排序
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsUrl() {
		return sUrl;
	}
	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}
	public String getsImg() {
		return sImg;
	}
	public void setsImg(String sImg) {
		this.sImg = sImg;
	}
	public int getiSort() {
		return iSort;
	}
	public void setiSort(int iSort) {
		this.iSort = iSort;
	}
	@Override
	public String toString() {
		return "AdSupernatant [sName=" + sName + ", sUrl=" + sUrl + ", sImg="
				+ sImg + ", iSort=" + iSort + "]";
	}

	
	
}
