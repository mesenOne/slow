package com.indulge.freedom.who.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.ui.activity.LoginActivity;
import com.indulge.freedom.who.view.textsurface.Debug;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.checks.CookieThumperSample;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 违章查缴
 *
 * @author fangxiaotian
 */
public class CarPeccancyFragment extends BaseFragment {


    @Bind(R.id.tv_message_click_test)   // 测试
    TextView tvSignUp;
    @Bind(R.id.text_surface)
    TextSurface textSurface;

    @Bind(R.id.check_debug)
    CheckBox checkDebug;

    @Bind(R.id.btn_refresh)
    Button btn_refresh;



    @Override
    public int getContentViewId() {
        return R.layout.sample_activity;
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        // 适配4.4的Translucent bar
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            // 透明状态栏
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            Window window = getActivity().getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        initView();
        initData();
    }



    private void initView() {
        textSurface.postDelayed(new Runnable() {
            @Override public void run() {
                show();
            }
        }, 1000);


//        btn_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                show();
//            }
//        });
//
        checkDebug.setChecked(Debug.ENABLED);
        checkDebug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Debug.ENABLED = isChecked;
                textSurface.invalidate();
            }
        });

    }



    private void initData() {
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.btn_refresh,R.id.tv_message_click_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_message_click_test:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.btn_refresh:
                show();
                break;
        }
    }


    private void show() {
//        textSurface.reset();
//        CookieThumperSample.play(textSurface, getActivity().getAssets());
    }



}
