package com.indulge.freedom.who.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 导航图片信息
 * 
 * @author huangyue
 * 
 */
public class Banner implements Parcelable {




	public int getiType() {
		return iType;
	}

	public void setiType(int iType) {
		this.iType = iType;
	}

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
		return "Banner [iType=" + iType + ", sTitle=" + sTitle + ", sImage="
				+ sImage + ", sUrl=" + sUrl + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	private int iType;// 分类：1 首頁 2 二手車 3 買車 4 賣車

	private String sTitle = "";// 标题

	private String sImage = "";// 图片地址

	private String sUrl = "";// 外链地址
	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeInt(iType);
		dest.writeString(sTitle);
		dest.writeString(sImage);
		dest.writeString(sUrl);
	}
	
	public Banner(Parcel source){
		iType = source.readInt();
		sTitle = source.readString();
		sImage = source.readString();
		sUrl = source.readString();
	}
	
	
	
	
	
	public Banner() {
	}
	public static final Creator<Banner> CREATOR = new Creator<Banner>() {

		@Override
		public Banner createFromParcel(Parcel arg0) {
			return new Banner(arg0);
		}

		@Override
		public Banner[] newArray(int arg0) {
			return new Banner[arg0];
		}
	};
	

}