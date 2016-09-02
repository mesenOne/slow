/**
 * 
 */
package com.indulge.freedom.who.model;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * 用户登录后信息
 * 
 * @author fangxiaotian
 * 
 */
public class LoginUserInfo extends BmobUser implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7139066274439521686L;


	private Boolean sex;
	private Integer age;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}



	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}