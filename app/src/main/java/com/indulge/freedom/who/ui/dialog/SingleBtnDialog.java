package com.indulge.freedom.who.ui.dialog;


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


public class SingleBtnDialog extends Dialog {

    private Context context;
//    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private String deleteDetail;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        void doConfirm();
    }

    public SingleBtnDialog(Context context, String confirmButtonText,
                           String Detail) {
        super(context, R.style.edit_AlertDialog_style);
        this.context = context;
        this.confirmButtonText = confirmButtonText;
        //		this.cacelButtonText = cacelButtonText;
        this.deleteDetail = Detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.widget_coupon_dialog, null);
        setContentView(view);

//        TextView tvTitle = (TextView) view.findViewById(R.id.tv_delete_title);
        TextView tvDeleteDetail = (TextView) view.findViewById(R.id.delete_detail);
        Button tvConfirm = (Button) view.findViewById(R.id.delete_confirm);
        //		Button tvCancel = (Button) view.findViewById(R.id.delete_cancel);

        tvDeleteDetail.setText(deleteDetail);  //设置详解提醒
//        tvTitle.setText(title);
        tvConfirm.setText(confirmButtonText);
        //		tvCancel.setText(cacelButtonText);

        tvConfirm.setOnClickListener(new clickListener());
        //		tvCancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        //		lp.height = (int) (d.heightPixels * 0.3); // 高度设置为屏幕的0.3
        // 点空白不消失对话框
        SingleBtnDialog.this.setCancelable(true);
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.delete_confirm:
                    clickListenerInterface.doConfirm();
                    break;
                //			case R.id.delete_cancel:
                //				clickListenerInterface.doCancel();
                //				break;
            }
        }

    }

}
