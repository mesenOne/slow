package com.indulge.freedom.who.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.model.JPush;
import com.indulge.freedom.who.model.JPushJson;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "极光推送相关广播";
	public static final int INQUIRE_MESSAGE = 0x11;// 消息的通知id
	public static final String JPUSH = "jPush";

	// 广播的action
	public static final String PUSH_OPEN_ACTION = "com.smartcar.receiver.notification.open";// 点开消息通知栏的广播action
	public static final String INQUIRE_ACTION = "com.smartcar.receiver.inquire";
	public static final String SYSTEM_ACTION = "com.smartcar.receiver.system";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {// 发送自定义消息（消息是没有notifycation的）
			Bundle bundle = intent.getExtras();
			JPush jPush = new JPush();
			String title = bundle.getString(JPushInterface.EXTRA_TITLE);// 消息标题
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);// 消息内容
			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);// json格式的key-value键值对
			jPush.setTitle(title);
			jPush.setMessage(message);
			Gson gson = new Gson();
			JPushJson json = gson.fromJson(jsonExtra, JPushJson.class);
			jPush.setJsonExtra(json);
//			notifyMsg(context, jPush);
			Log.d(TAG, "接收推送的消息：" + jPush.toString());

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {// 发送通知
			Bundle bundle = intent.getExtras();
			JPush jPush = new JPush();
			String title = bundle
					.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);// 通知标题
			String message = bundle.getString(JPushInterface.EXTRA_ALERT);// 通知内容
			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);// json格式的key-value键值对
			jPush.setTitle(title);
			jPush.setMessage(message);
			Gson gson = new Gson();
			JPushJson json = gson.fromJson(jsonExtra, JPushJson.class);
			jPush.setJsonExtra(json);
			Log.d(TAG, "接收推送的通知：" + jPush.toString());
			Intent intent2System = new Intent();
			intent2System.setAction(SYSTEM_ACTION);
			context.sendBroadcast(intent2System);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {// 点开了通知
			Bundle bundle = intent.getExtras();
			JPush jPush = new JPush();
			String title = bundle
					.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);// 通知标题
			String message = bundle.getString(JPushInterface.EXTRA_ALERT);// 通知内容
			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);// json格式的key-value键值对
			jPush.setTitle(title);
			jPush.setMessage(message);
			Gson gson = new Gson();
			JPushJson json = gson.fromJson(jsonExtra, JPushJson.class);
			jPush.setJsonExtra(json);
			Log.d(TAG, "打开通知who：" + jPush.toString());
			String pushType = json.getPushType();
			String codeStr = json.getCode();
//			Intent intentHome = new Intent(context, MainActivity.class);
//			intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intentHome);
			intent2Act(context, pushType, codeStr);

		} else if (PUSH_OPEN_ACTION.equals(intent.getAction())) {// 点开自定义消息的notifycation
			JPush jPush = new JPush();
			JPushJson pushJson = new JPushJson();
			String pushType = "";
			String codeStr = "";
			if (intent.hasExtra(JPUSH)) {
				jPush = (JPush) intent.getSerializableExtra(JPUSH);
				pushJson = jPush.getJsonExtra();
				if (pushJson != null) {
					pushType = pushJson.getPushType();
					codeStr = pushJson.getCode();
				}
			}
			Log.d(TAG, "打开自定义消息：" + jPush.toString());
//			Intent intentHome = new Intent(context, MainActivity.class);
//			intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intentHome);
			intent2Act(context, pushType, codeStr);
		}
	}

	/**
	 * 发送通知
	 */
	@SuppressWarnings("deprecation")
	private void notifyMsg(Context context, JPush jPush) {
		// 发送通知notification
		// 获取服务
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// PendingIntent
		Intent intent = new Intent();
		intent.setClass(context, JPushReceiver.class);
		intent.setAction(PUSH_OPEN_ACTION);
		intent.putExtra(JPUSH, jPush);
		// 类似intent的一种
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		// 采用系统默认的状态栏显示和通知栏显示
        Notification.Builder builder1 = new Notification.Builder(context);
        builder1.setSmallIcon(R.drawable.icon_app_square); //设置图标
        builder1.setTicker(jPush.getTitle());
        builder1.setContentTitle(jPush.getTitle()); //设置标题
        builder1.setContentText(jPush.getMessage()); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(true);//打开程序后图标消失
        builder1.setContentIntent(pendingIntent);
        Notification notification = builder1.build();
		// 发送
		manager.notify(INQUIRE_MESSAGE, notification);

		// 发送广播
		Intent intent2Inquire = new Intent();
		intent2Inquire.setAction(INQUIRE_ACTION);
		context.sendBroadcast(intent2Inquire);
	}

	/**
	 * 点开通知栏的notification的跳转（跳转到MessageActivity）
	 */
	private void intent2Act(Context context, String pushType, String codeStr) {
		switch (pushType) {
		case "100001":	//	标详情页
			// 投资详情
//			Intent intentDetail = new Intent();
//			intentDetail.setClass(context, InvestDetailActivity.class);
//			intentDetail.putExtra("sn", codeStr);
//			intentDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			context.startActivity(intentDetail);
			break;
			
		case "300000":	//	新闻公告
//			Intent intentNotice = new Intent(context,
//					NewsDetailActivity.class);
//			intentNotice.putExtra("code", codeStr);
//			intentNotice.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			context.startActivity(intentNotice);
			break;
			
		case "400000":	//	活动页面
//			String tiger = "||android";
//			String base64 = Base64.encodeToString(
//					tiger.getBytes(), Base64.DEFAULT);
//			String url = "http://" + codeStr
//					+ "?parameters=" + base64;
//			Intent intent = new Intent();
//			intent.setClass(context, HomeBannerWebActivity.class);
//			intent.putExtra("url", url);
//			intent.putExtra("title", "智富360");
//			intent.putExtra("is_share", "0");
//			intent.putExtra("share_url", "");
//			intent.putExtra("share_pic", "");
//			intent.putExtra("share_title", "");
//			intent.putExtra("share_desc", "");
//			context.startActivity(intent);
			break;

		default:
			break;
		}
	}
}
