package com.indulge.freedom.who.config;


import com.indulge.freedom.who.ui.fragment.FeaturedFragment;
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
	public static final String CAR_PECCANCY = FeaturedFragment.class.getSimpleName();// 标识跳MineFragment的action


	public static int adPictureSize;// 网络上的广告图片的大小


	public static String[] mUrlSmall = new String[]{"http://hbimg.b0.upaiyun.com/e09714f5a312d0bd33e40281dbf62dbe9ff48f662854f-bCCmJz_fw658",
			"http://hbimg.b0.upaiyun.com/92f959488dde872d1a7d0ee497bf52f68d513c3a80e30-tts7ZA_fw658",
			"http://hbimg.b0.upaiyun.com/7cd523a2821f1923725c4e964bccc26b5e76058cdfc6a-fYdDkj_fw658",
			"http://hbimg.b0.upaiyun.com/4848f0820c2281e61759918ee369d702c917326a1401c-NgjTuA_fw658",
			"http://bpic.588ku.com/element_origin_min_pic/01/99/55/095760a18681733.jpg",
			"http://bpic.588ku.com/element_origin_min_pic/01/99/89/695760c8169312e.jpg",
			"http://bpic.588ku.com/element_origin_min_pic/16/07/09/1157806c8124f19.jpg"
			, "http://bpic.588ku.com/element_origin_min_pic/00/85/41/4756e8e175ee893.jpg"
			, "http://bpic.588ku.com/element_origin_min_pic/16/08/31/0857c628ffd4008.jpg"};

	public static String[] mUrlsBig = new String[]{"http://hbimg.b0.upaiyun.com/2492f18d0b577063a035d4455dbcb6523370884a5bbae-Ha01it_fw658",
			"http://hbimg.b0.upaiyun.com/38da21dfe65adfacdefb1678b38b7a9de3c04ffb1842f-356DIF_fw658",
			"http://hbimg.b0.upaiyun.com/951ac26a5b6867c0834acf5ccfdda52f31b81576892a-0BnXM9_fw658",
			"http://hbimg.b0.upaiyun.com/ba0c0a6571567858b3c4dd78a4497b22aabec8f01bd5c8-EYVJEk_fw658",
			"http://hbimg.b0.upaiyun.com/88e5f735a3e0f7f717352e7290c7b72f0f9ac4f44aa9a-9I6QVS_fw658",
			"http://img.hb.aicdn.com/332e70655bfc25ff83d8d242fbc4d8041e8e5279852df-6MArhf_fw658",
			"http://img.hb.aicdn.com/0fb0c72ed40e86a6160a3b11e449a72a1647aee02abac-HN1uL8_fw658",
			"http://img.hb.aicdn.com/3200f7876c6d46f9d7efd5be944ea7ab110c20da25482-0XvPWt_fw658"
	};


	/**
	 * 变速箱
	 */
	public static final String TRANSMISSION = "sTransmission";

}
