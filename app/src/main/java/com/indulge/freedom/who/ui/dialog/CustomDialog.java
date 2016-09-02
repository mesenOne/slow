package com.indulge.freedom.who.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.indulge.freedom.who.R;

/**
 * @author fengkehua
 * 
 *         公共对话框 自定义控件 2016/5/27
 */
public class CustomDialog extends Dialog {
	private Context context;
	private String title;
	private String confirmButtonText;
	private String cacelButtonText;
	private String detail;
	private ClickListenerInterface clickListenerInterface;

	public interface ClickListenerInterface {

		public void doConfirm();

		public void doCancel();
	}

	public CustomDialog(Context context, String title,
			String confirmButtonText, String cacelButtonText, String detail) {
		super(context, R.style.dialog);
		this.context = context;
		this.title = title;
		this.confirmButtonText = confirmButtonText;
		this.cacelButtonText = cacelButtonText;
		this.detail = detail;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		@SuppressLint("InflateParams") View view = inflater.inflate(R.layout.widget_custom_dialog, null);
		setContentView(view);

		TextView tvTitle = (TextView) view.findViewById(R.id.tv_delete_title);
		TextView tvdetail = (TextView) view.findViewById(R.id.delete_detail);
		Button tvConfirm = (Button) view.findViewById(R.id.delete_confirm);
		Button tvCancel = (Button) view.findViewById(R.id.delete_cancel);

		tvdetail.setText(detail); // 设置详解提醒
		tvTitle.setText(title);
		tvConfirm.setText(confirmButtonText);
		tvCancel.setText(cacelButtonText);

		tvConfirm.setOnClickListener(new clickListener());
		tvCancel.setOnClickListener(new clickListener());

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
		// lp.height = (int) (d.heightPixels * 0.3); // 高度设置为屏幕的0.3
		dialogWindow.setAttributes(lp);
	}

	public void setClicklistener(ClickListenerInterface clickListenerInterface) {
		this.clickListenerInterface = clickListenerInterface;
	}

	private class clickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) { 
			// TODO Auto-generated method stub
			if (clickListenerInterface != null) {
				int id = v.getId();
				switch (id) {
				case R.id.delete_confirm:
					clickListenerInterface.doConfirm();
					break;
				case R.id.delete_cancel:
					clickListenerInterface.doCancel();
					break;
				}
			}
		}

	};

}
