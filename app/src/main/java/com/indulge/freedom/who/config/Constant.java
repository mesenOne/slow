package com.indulge.freedom.who.config;


import com.indulge.freedom.who.ui.fragment.CarPeccancyFragment;
import com.indulge.freedom.who.ui.fragment.CarStagingFragment;
import com.indulge.freedom.who.ui.fragment.MineFragment;
import com.indulge.freedom.who.ui.fragment.SearchFragment;

/**
 * 常量
 * 
 * @author huangyue
 * 
 */
public class Constant {

	public static final String FAIL = "访问服务器失败";
	public static final String NO_NET = "您的网络好像不太给力，请稍后再试";

	public static final String ACTION = "action";// 跳转到UsedCarActivity时的action
	public static final String MINE = MineFragment.class.getSimpleName();// 标识跳MineFragment的action
	public static final String CAR_STAGE = CarStagingFragment.class.getSimpleName();// 标识跳MineFragment的action
	public static final String SLOW_SEARCH = SearchFragment.class.getSimpleName();// 标识跳MineFragment的action
	public static final String CAR_PECCANCY = CarPeccancyFragment.class.getSimpleName();// 标识跳MineFragment的action

	/**
	 * 买车
	 */
	public static final int REQUEST_CALL_SELLER = 0x05;// 请求码
	public static final String PRODUCT_ID = "productId";// 传产品id的action
	public static final String PRODUCT = "product";// 传产品的action
	public static final String ORDER_ID = "orderId";// 传订单详情的action
	public static final String ORDER_PRICE = "orderPrice";// 传订单详情的action

	/**
	 * 卖车界面上传照片Fragment
	 */
	public static final String LOCAL_FOLDER_NAME = "local_folder_name";// 跳转到相册页的文件夹名称
	public static final String DYNAMIC_ID_AND_DATE = "dynamic_id_and_date";// 动态ID和时间戳字符串
	public static final int RESULT_OK = 0x01;// 返回码返回成功
	public static final int REQUEST_OK = 0x00;// 请求码
	public static final int RESULT_CANCEL = 0x02;// 请求码

	/**
	 * 卖车界面基本信息Fragment
	 */
	public static final int CAR_SELL_CHOOSE_BRAND = 0x11;
	public static final String SELL_CAR_CHOOSE_BRAND = "formSellCar";
	public static final String SELL_CAR_CHOOSE_ADD_CAR = "formAddCar";
	public static final int CAR_SELL_CHOOSE_DATA = 0x22;
	public static final int CAR_SELL_CHOOSE_CITY = 0x33;
	public static final int CAR_SELL_CHOOSE_CITY_MEET = 0x34;
	public static final int CAR_SELL_CHOOSE_COLOR = 0x44;
	public static final int CAR_SELL_PRODUCT_OPERATE = 0x18;

	/**
	 * 定期保养
	 */
	public static String[] RegularMaintance = { "无定期保养", "4s定期保养", "非4s定期保养" };
	public static String[] Caruse = { "非运营", "运营", "营转非", "租赁" };

	/**
	 * 已发车源
	 */
	public static final String S_ID = "sId";
	public static final String S_PRODUCTID = "sProductId";

	public static final String SMOBILE = "sMobile";

	/**
	 * 裁剪头像
	 */
	public static final int CLIP_TOU_XIANG_RESULT = 0x17;

	/**
	 * 缩略图宽高比例
	 */
	public static final float THUMAIL_RATIO = 218.0f / 318.0f;

	/**
	 * 卖车详情重新编辑
	 */
	public static final String SELLCAR_REEDIT = "sellcar_edit";

	public static final int REQUEST_CALL_INQUIRE = 0x55;

	public static int adPictureSize;// 网络上的广告图片的大小

	/**
	 * 内部测试号的范围
	 */
	public static final int INNER_LOGIN_PHONE_MIN = 870000000;
	public static final int INNER_LOGIN_PHONE_MAX = 870009999;

	/* 买车页面搜索条件 */
	/**
	 * 变速箱
	 */
	public static final String TRANSMISSION = "sTransmission";
	/**
	 * 最低排放标准
	 */
	public static final String MIN_OUTPUT_STANDARD = "iMinOutputStandard";
	/**
	 * 最高排放标准
	 */
	public static final String MAX_OUTPUT_STANDARD = "iMaxOutputStandard";
	/**
	 * 最低车龄
	 */
	public static final String MIN_CAR_AGE = "iMinCarAge";
	/**
	 * 最高车龄
	 */
	public static final String MAX_CAR_AGE = "iMaxCarAge";
	/**
	 * 最低里程
	 */
	public static final String MIN_MILEAGE = "dMinMileage";
	/**
	 * 最高里程
	 */
	public static final String MAX_MILEAGE = "dMaxMileage";
	/**
	 * 最低车价
	 */
	public static final String MIN_PRICE = "dMinPrice";
	/**
	 * 最高车价
	 */
	public static final String MAX_PRICE = "dMaxPrice";
	/**
	 * 最低排量
	 */
	public static final String MIN_OUTPUT = "dMinOutput";
	/**
	 * 最高排量
	 */
	public static final String MAX_OUTPUT = "dMaxOutput";
	/**
	 * 品牌Id
	 */
	public static final String BRAND = "sBrandId";
	/**
	 * 车系Id
	 */
	public static final String SERIES = "sSeriesId";
	/**
	 * 车款Id
	 */
	public static final String STYLE = "sStyleId";
	/**
	 * 模糊搜索
	 */
	public static final String KEYWORD = "sKeyWord";
	/**
	 * 城市名称
	 */
	public static final String CITY = "sCityName";
	/**
	 * 车型
	 */
	public static final String TYPE = "sType";
	/**
	 * 颜色
	 */
	public static final String COLOR = "sColor";
	/**
	 * 国家
	 */
	public static final String COUNTRY = "sCountry";
	/**
	 * 来源
	 */
	public static final String SOURCE = "sPublishSource";
	/**
	 * 卖车城市选择
	 */
	public static final String SELL_CAR_CHOOSE_CITY = "sellCarChooseCity";
	
	/**
	 * 上传照片已选记录个数
	 */
	public static final String UPLOAD_PHOTO_CHOOSE_COUNT = "uploadPhotoChooseCount";
	
	/**
	 * 违章信息跳转详情
	 */
	public static final String TO_PECCANCY_INFO_DETAIL = "peccancyDetail";

}
