package com.indulge.freedom.who.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.bmob.v3.helper.NotificationCompat;

import com.google.gson.Gson;
import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.model.JPush;
import com.indulge.freedom.who.model.JPushJson;
import com.indulge.freedom.who.ui.activity.LoginActivity;
import com.indulge.freedom.who.ui.activity.UsedCarActivity;


/**
 * 极光推送消息相关的广播
 * 
 * @author huangyue
 * 
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
//		if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {// 发送自定义消息（消息是没有notifycation的）
//			Bundle bundle = intent.getExtras();
//			JPush jPush = new JPush();
//			String title = bundle.getString(JPushInterface.EXTRA_TITLE);// 消息标题
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);// 消息内容
//			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);// json格式的key-value键值对
//			jPush.setTitle(title);
//			jPush.setMessage(message);
//			Gson gson = new Gson();
//			JPushJson json = gson.fromJson(jsonExtra, JPushJson.class);
//			jPush.setJsonExtra(json);
//			notifyMsg(context, jPush);
//			Log.d(TAG, "接收推送的消息：" + jPush.toString());
//
//		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
//				.getAction())) {// 发送通知
//			Bundle bundle = intent.getExtras();
//			JPush jPush = new JPush();
//			String title = bundle
//					.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);// 通知标题
//			String message = bundle.getString(JPushInterface.EXTRA_ALERT);// 通知内容
//			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);// json格式的key-value键值对
//			jPush.setTitle(title);
//			jPush.setMessage(message);
//			Gson gson = new Gson();
//			JPushJson json = gson.fromJson(jsonExtra, JPushJson.class);
//			jPush.setJsonExtra(json);
//			Log.d(TAG, "接收推送的通知：" + jPush.toString());
//			Intent intent2System = new Intent();
//			intent2System.setAction(SYSTEM_ACTION);
//			context.sendBroadcast(intent2System);
//
//		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
//				.getAction())) {// 点开了通知
//			Bundle bundle = intent.getExtras();
//			JPush jPush = new JPush();
//			String title = bundle
//					.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);// 通知标题
//			String message = bundle.getString(JPushInterface.EXTRA_ALERT);// 通知内容
//			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);// json格式的key-value键值对
//			jPush.setTitle(title);
//			jPush.setMessage(message);
//			Gson gson = new Gson();
//			JPushJson json = gson.fromJson(jsonExtra, JPushJson.class);
//			jPush.setJsonExtra(json);
//			Log.d(TAG, "打开通知：" + jPush.toString());
//			String pushType = json.getPushType();
//			intent2Act(context, pushType);
//
//		} else if (PUSH_OPEN_ACTION.equals(intent.getAction())) {// 点开自定义消息的notifycation
//			JPush jPush = new JPush();
//			JPushJson pushJson = new JPushJson();
//			String pushType = "";
//			if (intent.hasExtra(JPUSH)) {
//				jPush = (JPush) intent.getSerializableExtra(JPUSH);
//				pushJson = jPush.getJsonExtra();
//				if (pushJson != null) {
//					pushType = pushJson.getPushType();
//				}
//			}
//			Log.d(TAG, "打开自定义消息：" + jPush.toString());
//			intent2Act(context, pushType);
//		}
	}

	/**
	 * 发送通知
	 */
	@SuppressWarnings("deprecation")
	private void notifyMsg(Context context, JPush jPush) {
		// 发送通知notification
		if (jPush != null ) {
			// 获取服务
			NotificationManager manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			// PendingIntent
			Intent intent = new Intent();
			intent.setClass(context, JPushReceiver.class);
			intent.setAction(PUSH_OPEN_ACTION);
			intent.putExtra(JPUSH, jPush);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

			// 采用系统默认的状态栏显示和通知栏显示
//			Notification.Builder builder = new Notification.Builder(context);
//			builder.setContentTitle(jPush.getTitle());
//			builder.setContentText(jPush.getMessage());
//			builder.setSmallIcon(R.drawable.icon_app_square);
//			Notification notification = builder.getNotification();
//			manager.notify(R.mipmap.ic_launcher, notification);

//			Notification notification = new Notification(
//					R.drawable.icon_app_square, jPush.getTitle(),
//					System.currentTimeMillis());
//			notification.setLatestEventInfo(context, jPush.getTitle(),
//					jPush.getMessage(), pendingIntent);


			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
//			PendingIntent pi = PendingIntent.getActivity(context, 0,
//					new Intent(context, MainActivity.class), 0);
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
					.setTicker("更新啦")
					.setContentTitle("标题")
					.setContentText("内容")
					.setSmallIcon(R.drawable.icon_app_square);
			Notification notification = builder.build();
			notificationManager.notify(0, notification);

			notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知栏清除
			notification.defaults = Notification.DEFAULT_SOUND;// 设置通知铃声

			// 发送
			manager.notify(INQUIRE_MESSAGE, notification);
		}

		// 发送广播
		Intent intent2Inquire = new Intent();
		intent2Inquire.setAction(INQUIRE_ACTION);
		context.sendBroadcast(intent2Inquire);
	}

	/**
	 * 点开通知栏的notification的跳转（跳转到MessageActivity）
	 */
	private void intent2Act(Context context, String pushType) {
		Intent intent2Act = new Intent();
		intent2Act
				.setClass(
						context,
						UsedCarActivity.exist ? (LoginActivity.class)
								: UsedCarActivity.class);
		intent2Act.putExtra(PUSH_OPEN_ACTION, PUSH_OPEN_ACTION);
		intent2Act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent2Act);
	}
}