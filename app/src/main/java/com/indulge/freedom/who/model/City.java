package com.indulge.freedom.who.model;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 城市
 * 
 * @author huangyue
 * 
 */
public class City implements Serializable, Comparable<City> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7405024539624291458L;

	private boolean bHot;// 热门标识（0：热门；1：非热门）
	private long iCityId;// 城市id
	private String sCityName = "";// 城市名称
	private String sFirstLetter = "";// 手写大字母

	public boolean isbHot() {
		return bHot;
	}

	public void setbHot(boolean bHot) {
		this.bHot = bHot;
	}

	public long getiCityId() {
		return iCityId;
	}

	public void setiCityId(long iCityId) {
		this.iCityId = iCityId;
	}

	public String getsCityName() {
		return sCityName;
	}

	public void setsCityName(String sCityName) {
		this.sCityName = sCityName;
	}

	public String getsFirstLetter() {
		return sFirstLetter;
	}

	public void setsFirstLetter(String sFirstLetter) {
		this.sFirstLetter = sFirstLetter;
	}

	@Override
	public String toString() {
		return "City [bHot=" + bHot + ", iCityId=" + iCityId + ", sCityName="
				+ sCityName + ", sFirstLetter=" + sFirstLetter + "]";
	}

	@Override
	public int compareTo(City city) {
		if (this == city) {
			return 0;
		}
		if (TextUtils.isEmpty(this.getsFirstLetter())) {
			return 1;
		}
		if (TextUtils.isEmpty(city.getsFirstLetter())) {
			return -1;
		}
		return this.getsFirstLetter().charAt(0)
				- city.getsFirstLetter().charAt(0);
	}

}