package com.indulge.freedom.who.model;

import java.io.Serializable;

/**
 * 推送的jsonExtra
 * 
 * @author huangyue
 * 
 */
public class JPushJson implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5262525709653493069L;

	private String PushType = "";
	private String Code = "";

	public String getPushType() {
		return PushType;
	}

	public void setPushType(String pushType) {
		PushType = pushType;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	} //s

	@Override
	public String toString() {
		return "JPushJson [PushType=" + PushType + ", Code=" + Code + "]";
	}
}