package com.indulge.freedom.who.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * 监听键盘升起落下的公共控件
 */
public class MySoftLayout extends LinearLayout {

	public MySoftLayout(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public MySoftLayout(Context context) {
		super(context);
	}

	private OnSoftKeyboardListener onSoftKeyboardListener;

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		if (onSoftKeyboardListener != null) {
			final int newSpec = MeasureSpec.getSize(heightMeasureSpec);
			final int oldSpec = getMeasuredHeight();
			// If layout became smaller, that means something forced it to
			// resize. Probably soft keyboard :)
			Log.i("H2","oldSpec--"+oldSpec+"newSpec--"+newSpec);
			if (oldSpec > newSpec) {
				onSoftKeyboardListener.onShown();
			} else {
				onSoftKeyboardListener.onHidden();
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public final void setOnSoftKeyboardListener(
			final OnSoftKeyboardListener listener) {
		this.onSoftKeyboardListener = listener;
	}

	// Simplest possible listener :)
	public interface OnSoftKeyboardListener {
		public void onShown();

		public void onHidden();
	}
}
