package com.indulge.freedom.who.ui.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import android.widget.TextView;

import com.indulge.freedom.who.R;

import com.indulge.freedom.who.view.xrecyclerview.ProgressStyle;
import com.indulge.freedom.who.view.xrecyclerview.XRecyclerView;

import butterknife.Bind;


/**
 * 车主分期页(主页)
 *
 * @author fengkehua
 */
public class CarStagingFragment extends BaseFragment implements XRecyclerView.LoadingListener{


    @Bind(R.id.tv_header_title_circle)
    TextView tvHeaderTittle;
    @Bind(R.id.toolbar_circle)
    View toolbar;
    @Bind(R.id.lv_circle)
    XRecyclerView mRecyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected XRecyclerView createScrollable() {
        return mRecyclerView;
    }

    @Override
    protected View getObservableScrollTitleView() {
        return toolbar;
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView();
        initData();
    }


    private void initView() {
        AssetManager mgr = getActivity().getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/MagmaWave.ttf");   //zhiheiti.ttf  lingHeiTi.ttf  HeiJian.TTF
        tvHeaderTittle.setTypeface(tf);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingListener(this);


        // 设置下拉刷新和加载更多动画
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScale);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);   // Pacman 吃豆子
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        View footView = View.inflate(context, R.layout.footlayout, null);
        mRecyclerView.setFootView(footView);
    }


    private void initData() {
    }



    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
    }
}