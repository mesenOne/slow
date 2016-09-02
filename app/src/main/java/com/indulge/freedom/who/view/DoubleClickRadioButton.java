package com.indulge.freedom.who.view;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioButton;

/**
 * 含双击的RadioButton
 *
 * (应用场景:listView滚到底部，只需双击底部RadioButton就可以回到顶部)
 * 
 * @author huangyue
 * 
 */
public class DoubleClickRadioButton extends RadioButton {
	private boolean mSecClick;
	private Timer mTimer;
	private DoubleClickListener mDoubleClickListener;

	public DoubleClickRadioButton(Context context) {
		super(context);
	}

	public DoubleClickRadioButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public DoubleClickRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置双击监听
	 *
	 */
	public void setOnDoubleClickListener(DoubleClickListener doubleClickListener) {
		this.mDoubleClickListener = doubleClickListener;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			mTimer = null;
			if (!mSecClick) {
				mSecClick = true; // 双击准备
				if (mTimer == null) {
					mTimer = new Timer();
				}
				mTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						mSecClick = false;
					}
				}, 500); // 如果0.5秒钟内没有再次按下，则启动定时器取消掉刚才执行的任务
			} else {
				// 双击事件
				if (mDoubleClickListener != null) {
					mDoubleClickListener.doubleClick(this);
				}
				if (mTimer != null) {
					mTimer.cancel();
					mTimer = null;
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 双击监听器
	 * 
	 * @author huangyue
	 * 
	 */
	public interface DoubleClickListener {
		void doubleClick(DoubleClickRadioButton view);
	}
}
