/**
 * 
 */
package com.indulge.freedom.who.model;

import java.io.Serializable;

/**
 * 个人中心通用Message，返回内容为提示信息
 * 
 * @author fangxiaotian
 * 
 */
public class MessageInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6558694644845856662L;

	private String sTitle = "";// 提示框标题

	private String bStop = "";// 是否停留

	private String isStopTime = "";// 提示框停留时间

	private String sContent = "";// 提示信息内容

	private String sModelName = "";// 模块名称（用英文（，）分隔的多个按钮名称）

	/**
	 * @return the sTitle
	 */
	public String getsTitle() {
		return sTitle;
	}

	/**
	 * @param sTitle
	 *            the sTitle to set
	 */
	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	/**
	 * @return the bStop
	 */
	public String getbStop() {
		return bStop;
	}

	/**
	 * @param bStop
	 *            the bStop to set
	 */
	public void setbStop(String bStop) {
		this.bStop = bStop;
	}

	/**
	 * @return the isStopTime
	 */
	public String getIsStopTime() {
		return isStopTime;
	}

	/**
	 * @param isStopTime
	 *            the isStopTime to set
	 */
	public void setIsStopTime(String isStopTime) {
		this.isStopTime = isStopTime;
	}

	/**
	 * @return the sContent
	 */
	public String getsContent() {
		return sContent;
	}

	/**
	 * @param sContent
	 *            the sContent to set
	 */
	public void setsContent(String sContent) {
		this.sContent = sContent;
	}

	/**
	 * @return the sModelName
	 */
	public String getsModelName() {
		return sModelName;
	}

	/**
	 * @param sModelName
	 *            the sModelName to set
	 */
	public void setsModelName(String sModelName) {
		this.sModelName = sModelName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SmsVerifyCode [sTitle=" + sTitle + ", bStop=" + bStop
				+ ", isStopTime=" + isStopTime + ", sContent=" + sContent
				+ ", sModelName=" + sModelName + "]";
	}
}
