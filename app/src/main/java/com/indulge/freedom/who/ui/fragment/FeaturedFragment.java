package com.indulge.freedom.who.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.adapter.GalleryAdapter;
import com.indulge.freedom.who.adapter.PostAdapter;
import com.indulge.freedom.who.model.Banner;
import com.indulge.freedom.who.model.Post;
import com.indulge.freedom.who.view.CarouselView;
import com.indulge.freedom.who.view.CenterLockHorizontalScrollview;

import com.indulge.freedom.who.view.RippleView;
import com.indulge.freedom.who.view.recyclerview_animators.adapters.AlphaInAnimationAdapter;

import com.indulge.freedom.who.view.recyclerview_animators.animators.FadeInAnimator;
import com.indulge.freedom.who.view.xrecyclerview.ProgressStyle;
import com.indulge.freedom.who.view.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * 违章查缴
 *
 * @author fangxiaotian
 */
public class FeaturedFragment extends BaseFragment<XRecyclerView>{


    /**
     * 想法: 逼格英语朋友圈,英文博客
     */

    @Bind(R.id.img_header_back)
    ImageView ivBack;
    @Bind(R.id.img_header_text_right_fm)
    RippleView ivRight;
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTittle;
    @Bind(R.id.lv_geogas)
    XRecyclerView mRecyclerView;
    @Bind(R.id.ll_left_location)
    View leftLocation;

    @Bind(R.id.toolbar)
    View toolbar;

    private String[] mUrlSmall = new String[]{"http://hbimg.b0.upaiyun.com/e09714f5a312d0bd33e40281dbf62dbe9ff48f662854f-bCCmJz_fw658",
            "http://hbimg.b0.upaiyun.com/92f959488dde872d1a7d0ee497bf52f68d513c3a80e30-tts7ZA_fw658",
            "http://hbimg.b0.upaiyun.com/7cd523a2821f1923725c4e964bccc26b5e76058cdfc6a-fYdDkj_fw658",
            "http://hbimg.b0.upaiyun.com/4848f0820c2281e61759918ee369d702c917326a1401c-NgjTuA_fw658",
            "http://bpic.588ku.com/element_origin_min_pic/01/99/55/095760a18681733.jpg",
            "http://bpic.588ku.com/element_origin_min_pic/01/99/89/695760c8169312e.jpg",
            "http://bpic.588ku.com/element_origin_min_pic/16/07/09/1157806c8124f19.jpg"
            , "http://bpic.588ku.com/element_origin_min_pic/00/85/41/4756e8e175ee893.jpg"
            , "http://bpic.588ku.com/element_origin_min_pic/16/08/31/0857c628ffd4008.jpg"};

    private String[] mUrlsBig = new String[]{"http://hbimg.b0.upaiyun.com/2492f18d0b577063a035d4455dbcb6523370884a5bbae-Ha01it_fw658",
            "http://hbimg.b0.upaiyun.com/38da21dfe65adfacdefb1678b38b7a9de3c04ffb1842f-356DIF_fw658",
            "http://hbimg.b0.upaiyun.com/951ac26a5b6867c0834acf5ccfdda52f31b81576892a-0BnXM9_fw658",
            "http://hbimg.b0.upaiyun.com/ba0c0a6571567858b3c4dd78a4497b22aabec8f01bd5c8-EYVJEk_fw658",
            "http://hbimg.b0.upaiyun.com/88e5f735a3e0f7f717352e7290c7b72f0f9ac4f44aa9a-9I6QVS_fw658",
            "http://img.hb.aicdn.com/332e70655bfc25ff83d8d242fbc4d8041e8e5279852df-6MArhf_fw658",
            "http://img.hb.aicdn.com/0fb0c72ed40e86a6160a3b11e449a72a1647aee02abac-HN1uL8_fw658",
            "http://img.hb.aicdn.com/3200f7876c6d46f9d7efd5be944ea7ab110c20da25482-0XvPWt_fw658"
    };

    private CarouselView mCarouselView;
    private List<Banner> mBannerList = new ArrayList<Banner>();
    private ArrayList<Post> mPostList;
    private PostAdapter mPostAdapter;
    private CenterLockHorizontalScrollview mHorizontalView;
    private GalleryAdapter mGalleryAdapter;
    private ArrayList<Banner> mGalleryList;
    int currIndex = 5;

    private ImageView mTriangleView;
    private View headerView;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_featured;
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
        ivBack.setVisibility(View.GONE);
        tvHeaderTittle.setText("SLOW");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.sendClickEvent(true);
        leftLocation.setVisibility(View.VISIBLE);
        // 初始标题字体样式
        AssetManager mgr = getActivity().getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/MagmaWave.ttf");   //zhiheiti.ttf  lingHeiTi.ttf  HeiJian.TTF
        tvHeaderTittle.setTypeface(tf);
        // 初始RecyclerView控件和列表适配器
        initRecyclerView();
        // 初始横向控件适配器
        mGalleryList = new ArrayList<Banner>();
        mGalleryAdapter = new GalleryAdapter(context, R.layout.news_list_item, mGalleryList);
        // 下拉刷新和滑动控件监听
        eventAction();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        // 设置下拉刷新和加载更多动画
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScale);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);   // Pacman 吃豆子
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        // 初始头部局
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_geogas_lv, (ViewGroup)getActivity().findViewById(android.R.id.content),false);
        mCarouselView = (CarouselView) headerView.findViewById(R.id.carouse_geogas_lv_header);
        mCarouselView.setParmsType(1);
        mCarouselView.setDefaultWidthHeight(mScreenWidth,
                (int) (mScreenWidth * 10.01f / 25.05f), 1);
        mHorizontalView = (CenterLockHorizontalScrollview) headerView.findViewById(R.id.id_listView_horizontal);
        mTriangleView = (ImageView) headerView.findViewById(R.id.iv_triangle);

        // 添加头 脚布局
        mRecyclerView.addHeaderView(headerView);
        View footView = View.inflate(context, R.layout.footlayout, null);
        mRecyclerView.setFootView(footView);

        // 竖向列表适配器, 以及列表滑动动画
        mPostList = new ArrayList<Post>();
        mPostAdapter = new PostAdapter(context, mPostList,
                R.layout.item_geogas_product_lv);
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mPostAdapter);
        alphaAdapter.setFirstOnly(false);
        alphaAdapter.setDuration(700);
        alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mRecyclerView.setAdapter(alphaAdapter);
//        mRecyclerView.setLoadingMoreEnabled(false);
    }


    private void initData() {
        loadListData(1);
        loadBanner();
        loadGallery();
    }


    /**
     * 横向列表数据
     * ArrayAdaper和BaseAdapter的刷新机制不同，
     * ArrayAdaper必须在有数据时再加适配器
     * 不能 notifyDataSetChanged
     */
    private void loadGallery() {
        mGalleryList.clear();
        for (int i = 0; i < mUrlSmall.length; i++) {
            Banner banner = new Banner();
            banner.setsImage(mUrlSmall[i]);
            mGalleryList.add(banner);
        }
        mHorizontalView.setAdapter(context, mGalleryAdapter);
    }


    /**
     * banner控件数据
     */
    private void loadBanner() {
        mBannerList.clear();

        BmobQuery<Banner> query = new BmobQuery<Banner>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Banner>() {
            @Override
            public void done(List<Banner> bannerInfo,BmobException e) {
                if(e==null){
                    if (bannerInfo!=null && bannerInfo.size()>0){
                        for (int i = 0; i < bannerInfo.size(); i++) {
                            Logger.i(bannerInfo.size()+"---bannerInfo.size()"+bannerInfo.get(i).toString()+"---bannerInfo");
                            mBannerList.add(bannerInfo.get(i));
                        }
                        mCarouselView.addAllImage(mBannerList);
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });

    }

    /**
     * RecyclerList列表数据
     */
    private void loadListData(int page) {
        if (page==1){
            mPostList.clear();
        }
        BmobQuery<Post> query = new BmobQuery<Post>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> postInfo,BmobException e) {
                if(e==null){
                    for (int i = 0; i < postInfo.size(); i++) {
                        Logger.i("成功"+postInfo.size()+postInfo.get(i).toString());
                    }
                    mPostList.addAll(postInfo);
                    mPostAdapter.notifyDataSetChanged();
                    stopRefreshAndLoadMore();
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }


    int n=100; int sum;
    private void eventAction() {
        // banner点击事件
        mCarouselView.start(new CarouselView.IItemClickListener() {
            @Override
            public void onClick(View v, Banner banner, int position) {
                sum=0;
                // 计算1到100相加的结果
                sum=(1+n)*n/2;
                show(sum+"");
            }
        });

        // RecyclerList点击事件
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }
            @Override
            public void onLoadMore() {
            }
        });

        // 横向控件角标事件点击
        mTriangleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currIndex < mGalleryList.size()) {
                    if(currIndex == 2){
                        currIndex = 5;
                        mHorizontalView.setCenter(currIndex);
                    }else{
                        mHorizontalView.setCenter(currIndex);
                    }
                    currIndex+=1;
                } else {
                    currIndex = 2;
                    mHorizontalView.setCenter(currIndex);
                }
            }
        });
    }

//    /**
//     * 点击事件
//     *
//     * @param view
//     */
//    @OnClick({R.id.tv_message_click_test})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_message_click_test:
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//                break;
//        }
//    }



    /**
     * 停止所有加载动画
     */
    public void stopRefreshAndLoadMore() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

}
