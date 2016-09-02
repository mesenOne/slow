package com.indulge.freedom.who.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.indulge.freedom.who.R;


/**
 * 弹出自定义的提示对话框
 * @author huhuan
 *
 */
@SuppressLint("InflateParams")
public abstract class TipsCustomDialogUtils {

	private AlertDialog dialog;

	public TipsCustomDialogUtils(AlertDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * 
	 * @param context  	上下文
	 * @param tittle	标题	
	 * @param content	内容
	 * @param params	显示的按钮选项（最多三项）
	 * @param outSideCancel	触摸外部可取消对话框
	 * @param backPressCancel 按返回键是否取消对话框
	 */
	public void creatCustomDialog(Context context, String tittle,
			CharSequence content, String[] params,boolean outSideCancel,boolean backPressCancel) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_tips_custom_dialog, null);
		TextView tvTittle = (TextView) view.findViewById(R.id.tv_title);
		TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
		tvTittle.setText(tittle);
		tvContent.setText(content);
		LinearLayout llChoose = (LinearLayout) view.findViewById(R.id.ll_chose);
		LayoutParams btnParms = new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1);
		llChoose.removeAllViews();
		if (params.length == 1) {
			Button btnSingle = new Button(context);
			btnSingle.setLayoutParams(btnParms);
			btnSingle.setText(params[0]);
			btnSingle.setTextSize(16);
			btnSingle.setTextColor(context.getResources().getColor(
					R.color.white));
			btnSingle
					.setBackgroundResource(R.drawable.shape_button_choose_single);
			btnSingle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					singleClick();
				}
			});
			llChoose.addView(btnSingle);
		} else if (params.length == 2) {
			Button btnLeft = new Button(context);
			btnLeft.setLayoutParams(btnParms);
			btnLeft.setText(params[0]);
			btnLeft.setTextSize(16);
			btnLeft.setTextColor(context.getResources().getColor(R.color.gray));
			btnLeft.setBackgroundResource(R.drawable.shape_button_unpressed_left);
			btnLeft.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					leftClick();
				}
			});
			llChoose.addView(btnLeft, 0);
			Button btnRight = new Button(context);
			btnRight.setLayoutParams(btnParms);
			btnRight.setText(params[1]);
			btnRight.setTextSize(16);
			btnRight.setTextColor(context.getResources()
					.getColor(R.color.white));
			btnRight.setBackgroundResource(R.drawable.shape_button_pressed_right);
			btnRight.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rightClick();
				}
			});
			llChoose.addView(btnRight, 1);

		} else if (params.length == 3) {
			Button btnLeft = new Button(context);
			btnLeft.setLayoutParams(btnParms);
			btnLeft.setText(params[0]);
			btnLeft.setTextSize(16);
			btnLeft.setTextColor(context.getResources().getColor(R.color.gray));
			btnLeft.setBackgroundResource(R.drawable.shape_button_unpressed_left);
			btnLeft.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					leftClick();
				}
			});
			llChoose.addView(btnLeft, 0);
			Button btnMiddle = new Button(context);
			btnMiddle.setLayoutParams(btnParms);
			btnMiddle.setText(params[1]);
			btnMiddle.setTextSize(16);
			btnMiddle.setTextColor(context.getResources().getColor(
					R.color.white));
			btnMiddle
					.setBackgroundColor(context.getResources().getColor(R.color.orange_second));
			btnMiddle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					leftClick();
				}
			});
			llChoose.addView(btnMiddle, 1);
			Button btnRight = new Button(context);
			btnRight.setLayoutParams(btnParms);
			btnRight.setText(params[2]);
			btnRight.setTextSize(16);
			btnRight.setTextColor(context.getResources()
					.getColor(R.color.white));
			btnRight.setBackgroundResource(R.drawable.shape_button_pressed_right);
			btnRight.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rightClick();
				}
			});
			llChoose.addView(btnRight, 2);

		}
		dialog.show();
		dialog.setCanceledOnTouchOutside(outSideCancel);//
		dialog.setCancelable(backPressCancel);
		Window window = dialog.getWindow();
		window.setLayout((int) (ScreenUtils.getScreenWidth(context)*7.0/8.0), android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setContentView(view);

	}

	public void dismissDialog() {
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.cancel();
			}
			dialog = null;
		}
	}

	public abstract void singleClick();

	public abstract void leftClick();

	public abstract void middleClick();

	public abstract void rightClick();

}
