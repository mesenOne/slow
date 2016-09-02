package com.indulge.freedom.who.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.indulge.freedom.who.R;

/**
 * Toast单例
 * 
 * @author huangyue
 * 
 */
public class ToastUtil {
	private volatile static Toast mToast;

	public static void show(Context context, String message) {
		if (!TextUtils.isEmpty(message)) {
			if (mToast == null) {
				mToast = Toast.makeText(context.getApplicationContext(),
						message, Toast.LENGTH_SHORT);
				mToast.show();
			} else {
				mToast.setText(message);
				mToast.show();
			}
		}
	}

	public static void cancel() {
		if (mToast != null) {
			mToast.cancel();
		}
	}



}