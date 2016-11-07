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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.adapter.PostAdapter;
import com.indulge.freedom.who.model.Product;
import com.indulge.freedom.who.view.recyclerview_animators.adapters.AlphaInAnimationAdapter;
import com.indulge.freedom.who.view.recyclerview_animators.animators.FadeInAnimator;
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
    private ArrayList<Product> mProductsList;
    private PostAdapter mProductAdapter;
    private String[] mUrlsBig = new String[]{
            "http://img.hb.aicdn.com/0fb0c72ed40e86a6160a3b11e449a72a1647aee02abac-HN1uL8_fw658",
            "http://img.hb.aicdn.com/3200f7876c6d46f9d7efd5be944ea7ab110c20da25482-0XvPWt_fw658",
            "http://img.hb.aicdn.com/9c3fe5bd67be4e30d9770106325e81e99ed486c848b89-lywyyy_fw658",
            "http://img.hb.aicdn.com/2629a4d93fdb3158edb4b28d603ba44cd08ba5f2b05b-aGfMyk_fw658"
    };

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
//        initData();
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
        /**
         * 竖向列表数据, 以及列表滑动动画
         */
//        mProductsList = new ArrayList<Product>();
//        mProductAdapter = new PostAdapter(context, mProductsList,
//                R.layout.item_geogas_product_lv);
//        for (int i = 0; i < 10; i++) {
//            // 列表数据
//            if (i==2){
//                mProductsList.add(new Product(mUrlsBig[0],"在简单愉悦的环境下工作需要这样做才对"));
//            }else if (i==5){
//                mProductsList.add(new Product(mUrlsBig[1],"上海上班族下班好去处-淮海中路店"));
//            }else if (i==8){
//                mProductsList.add(new Product(mUrlsBig[2],"世纪大道的房价本月300币的降幅"));
//            }else{
//                mProductsList.add(new Product(mUrlsBig[3],"橄榄球上海体育馆明日举办"));
//            }
//        }
//
//        mRecyclerView.setItemAnimator(new FadeInAnimator());
//        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mProductAdapter);
//        alphaAdapter.setFirstOnly(true);
//        alphaAdapter.setDuration(700);
//        alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
//        mRecyclerView.setAdapter(alphaAdapter);
//
//        mProductAdapter.notifyDataSetChanged();
//        mRecyclerView.setRefreshing(true);
    }



    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
//                mProductsList.clear();
//                for (int i = 0; i < 20; i++) {
//                    mProductsList.add(new Product());
//                }
//                mProductAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }
        }, 1000);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mRecyclerView.setNoMore(true);
            }
        }, 1000);
    }
}