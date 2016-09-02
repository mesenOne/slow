package com.indulge.freedom.who.util;

import android.content.Context;

import com.indulge.freedom.who.ui.view.MyProgressDialog;


public class ShowLoadingDialogUtil {

	private static MyProgressDialog processDia;

	/**
	 * 显示加载中对话框
	 * 
	 * @param context
	 */
	public static void showLoadingDialog(Context context, String message,
			boolean isCancelable) {
		if (processDia == null) {
			processDia = new MyProgressDialog(context, message);
			// 点击提示框外面是否取消提示框
			processDia.setCanceledOnTouchOutside(false);
			// 点击返回键是否取消提示框
			processDia.setCancelable(isCancelable);
			processDia.setIndeterminate(true);
			processDia.setMessage(message);
			processDia.show();
		}
	}

	/**
	 * 关闭加载对话框
	 */
	public static void closeLoadingDialog() {
		if (processDia != null) {
			if (processDia.isShowing()) {
				processDia.cancel();
			}
			processDia = null;
		}
	}
}
