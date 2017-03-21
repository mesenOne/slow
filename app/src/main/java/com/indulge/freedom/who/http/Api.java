package com.indulge.freedom.who.http;

/**
 * 服务器与接口Api
 * 
 * @author huangyue
 * 
 */
public class Api {

	/************************************************
	 * 服务器地址
	 ************************************************/

	/**
	 *  此为Bmob的APP_ID
	 */
	public static String BMOB_APPID = "6f3e8c20282e69f6a3cbc6dae33c2099";

	/** 三方服务器*/
	public static final String MAIN_API = "http://apicloud.mob.com/";


	/** 当前服务器 */
	public static final String SERVICE = MAIN_API;


	/************************************************
	 * 接口Api
	 ************************************************/

	/** 获取当前版本的服务器 */
	public static final String GET_SERVICE = "Config/GetApiPort";



	/** 获取广告页信息 */
	public static final String GET_AD = "Config/GetInitialPage";




	/** 根据最新时间戳获取二手车、买车未读数量 */
	public static final String GET_PRODUCT_COUNT = "Product/GetProductCount";


	/** 登录 */
	public static final String LOGIN = "user/login?key=14990450fc142&username={username}&password={password}";


}
