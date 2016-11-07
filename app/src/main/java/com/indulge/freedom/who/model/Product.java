package com.indulge.freedom.who.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 产品的信息
 * 
 * @author huangyue
 * 
 */
public class Product implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1736676419787711801L;

	private String sHeadImgUrl;// 头像url

	private String sPublisherName;// 发布人昵称

	private String sBigImgUrl = "";// 大图url

	private String sTitleChinese = "";// 中文标题

	private String sTitleEnglish = "";// 英文标题

	private String sLabel = "";// 标签
	private String sLikePersonNumber = "";// 喜欢人数

	public Product(String sHeadImgUrl, String sPublisherName, String sBigImgUrl, String sBitleChinese, String sTitleEnglish, String sLabel, String sLikePersonNumber) {
		this.sHeadImgUrl = sHeadImgUrl;
		this.sPublisherName = sPublisherName;
		this.sBigImgUrl = sBigImgUrl;
		this.sTitleChinese = sBitleChinese;
		this.sTitleEnglish = sTitleEnglish;
		this.sLabel = sLabel;
		this.sLikePersonNumber = sLikePersonNumber;
	}


	public Product(String sBigImgUrl, String sTitleChinese) {
		this.sBigImgUrl = sBigImgUrl;
		this.sTitleChinese = sTitleChinese;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getsHeadImgUrl() {
		return sHeadImgUrl;
	}

	public void setsHeadImgUrl(String sHeadImgUrl) {
		this.sHeadImgUrl = sHeadImgUrl;
	}

	public String getsPublisherName() {
		return sPublisherName;
	}

	public void setsPublisherName(String sPublisherName) {
		this.sPublisherName = sPublisherName;
	}

	public String getsBigImgUrl() {
		return sBigImgUrl;
	}

	public void setsBigImgUrl(String sBigImgUrl) {
		this.sBigImgUrl = sBigImgUrl;
	}

	public String getsTitleChinese() {
		return sTitleChinese;
	}

	public void setsTitleChinese(String sTitleChinese) {
		this.sTitleChinese = sTitleChinese;
	}

	public String getsTitleEnglish() {
		return sTitleEnglish;
	}

	public void setsTitleEnglish(String sTitleEnglish) {
		this.sTitleEnglish = sTitleEnglish;
	}

	public String getsLabel() {
		return sLabel;
	}

	public void setsLabel(String sLabel) {
		this.sLabel = sLabel;
	}

	public String getsLikePersonNumber() {
		return sLikePersonNumber;
	}

	public void setsLikePersonNumber(String sLikePersonNumber) {
		this.sLikePersonNumber = sLikePersonNumber;
	}
}