package com.indulge.freedom.who.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * 导航图片信息
 * 
 * @author huangyue
 * 
 */
public class Banner extends BmobObject{

	private String sTitle = "";// 标题

	private String sImage = "";// 图片地址

	private String sUrl = "";// 外链地址

	public String getsTitle() {
		return sTitle;
	}

	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	public String getsImage() {
		return sImage;
	}

	public void setsImage(String sImage) {
		this.sImage = sImage;
	}

	public String getsUrl() {
		return sUrl;
	}

	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}



	@Override
	public String toString() {
		return "Banner{" +
				"sTitle='" + sTitle + '\'' +
				", sImage='" + sImage + '\'' +
				", sUrl='" + sUrl + '\'' +
				'}';
	}
}