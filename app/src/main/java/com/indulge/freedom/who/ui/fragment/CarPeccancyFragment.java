package com.indulge.freedom.who.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.adapter.GalleryAdapter;
import com.indulge.freedom.who.adapter.ProductAdapter;
import com.indulge.freedom.who.model.Banner;
import com.indulge.freedom.who.model.Product;
import com.indulge.freedom.who.ui.activity.LoginActivity;
import com.indulge.freedom.who.view.CarouselView;
import com.indulge.freedom.who.view.CenterLockHorizontalScrollview;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.img_header_back)
    ImageView ivBack;
    @Bind(R.id.img_header_text_right)
    ImageView ivRight;
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTittle;
    @Bind(R.id.lv_geogas)
    ListView mListView;

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
            "http://hbimg.b0.upaiyun.com/6668f41026b661d8d3048ec757c5e9799bdbf46718d1a-0sWbxO_fw658"
    };

    private CarouselView mCarouselView;
    private List<Banner> mBannerList = new ArrayList<Banner>();
    private List<Product> mProductsList;
    private ProductAdapter mProductAdapter;
    private CenterLockHorizontalScrollview mHorizontalView;
    private GalleryAdapter mGalleryAdapter;
    private ArrayList<Banner> mGalleryList;
    int currIndex = 4;

    private ImageView mTriangleView;

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
        ivBack.setVisibility(View.GONE);
        tvHeaderTittle.setText("SLOW");
        tvSignUp.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
//        ivRight.setImageResource(R.drawable.ico_search_blue_right);

        AssetManager mgr = getActivity().getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/MagmaWave.ttf");   //zhiheiti.ttf  lingHeiTi.ttf  HeiJian.TTF
        tvHeaderTittle.setTypeface(tf);

        View headerView = View.inflate(context, R.layout.header_geogas_lv, null);
        mCarouselView = (CarouselView) headerView.findViewById(R.id.carouse_geogas_lv_header);
        mCarouselView.setParmsType(1);
        // mCarouselView.setDefaultWidthHeight(mScreenWidth,
        // (int) (mScreenWidth * 7.06f / 25.05f), 0);
        mHorizontalView = (CenterLockHorizontalScrollview) headerView.findViewById(R.id.id_listView_horizontal);
        mTriangleView = (ImageView) headerView.findViewById(R.id.iv_triangle);

        mListView.addHeaderView(headerView);


        mTriangleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currIndex < mGalleryList.size()) {
                    mHorizontalView.setCenter(currIndex);
                    currIndex+=2;
                } else {
                    currIndex = 2;
                    mHorizontalView.setCenter(currIndex);
                }
            }
        });

    }




    private void initData() {

        /**
         * 竖向列表数据
         */
        mProductsList = new ArrayList<Product>();
        mProductAdapter = new ProductAdapter(context, mProductsList,
                R.layout.item_geogas_product_lv);
        mListView.setAdapter(mProductAdapter);


        /**
         * 横向列表数据
         *
         * ArrayAdaper和BaseAdapter的刷新机制不同，
         * ArrayAdaper必须在有数据时再加适配器
         * 不能 notifyDataSetChanged
         */
        mGalleryList = new ArrayList<Banner>();
        mGalleryList.clear();
        for (int i = 0; i < mUrlSmall.length; i++) {
            Banner banner = new Banner();
            banner.setsImage(mUrlSmall[i]);
            mGalleryList.add(banner);
            Log.i("FKH", mGalleryList.toString());
        }
        mGalleryAdapter = new GalleryAdapter(context, R.layout.news_list_item, mGalleryList);
        mHorizontalView.setAdapter(context, mGalleryAdapter);


        /**
         * banner控件数据，和监听
         */
        mBannerList.clear();
        for (int i = 0; i < mUrlsBig.length; i++) {
            Banner banner = new Banner();
            banner.setsImage(mUrlsBig[i]);
            mBannerList.add(banner);
            // 列表数据
            mProductsList.add(new Product());
        }
        mCarouselView.addAllImage(mBannerList);
        mCarouselView.start(new CarouselView.IItemClickListener() {
            @Override
            public void onClick(View v, Banner banner, int position) {

            }
        });


        mProductAdapter.notifyDataSetChanged();
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.tv_message_click_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_message_click_test:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }


}
