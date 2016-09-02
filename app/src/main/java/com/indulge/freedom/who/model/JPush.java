package com.indulge.freedom.who.model;

import java.io.Serializable;

/**
 * 极光推送
 * 
 * @author huangyue
 * 
 */
public class JPush implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5645528309397244383L;

	private String title = "";// 消息标题
	private String message = "";// 消息内容
	private JPushJson jsonExtra;// 消息json格式的"key-value"键值对

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JPushJson getJsonExtra() {
		return jsonExtra;
	}

	public void setJsonExtra(JPushJson jsonExtra) {
		this.jsonExtra = jsonExtra;
	}

	@Override
	public String toString() {
		return "JPush [title=" + title + ", message=" + message
				+ ", jsonExtra=" + jsonExtra + "]";
	}

}