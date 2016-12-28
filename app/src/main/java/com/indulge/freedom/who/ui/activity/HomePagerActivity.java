package com.indulge.freedom.who.ui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;

import com.indulge.freedom.who.service.DownloadAPKService;
import com.indulge.freedom.who.ui.fragment.FeaturedFragment;
import com.indulge.freedom.who.ui.fragment.CarStagingFragment;
import com.indulge.freedom.who.ui.fragment.MineFragment;
import com.indulge.freedom.who.ui.fragment.SearchFragment;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.view.DoubleClickRadioButton;
import com.readystatesoftware.viewbadger.BadgeView;

import butterknife.Bind;


/**
 * 加载三个Fragment
 *
 * @author huhuan
 */
public class HomePagerActivity extends BaseActivity implements
        OnCheckedChangeListener, DoubleClickRadioButton.DoubleClickListener {
    public static boolean exist;// 是否存在
    @Bind(R.id.base_ragroup_bottom)
    RadioGroup rgUsedCar;
    @Bind(R.id.rbt_car_stage)
    DoubleClickRadioButton rbtStage;
    @Bind(R.id.rbt_slow_search)
    DoubleClickRadioButton rbtSearch;
    @Bind(R.id.rbt_car_peccancy)
    DoubleClickRadioButton rbtPeccancy;
    @Bind(R.id.rbt_mine)
    DoubleClickRadioButton rbtMine;
    @Bind(R.id.rbt_slow_publish)
    Button rbtPublish;



//    DoubleClickRadioButton mBuyCarButton;
    private MineFragment mMineFragment;
    private SearchFragment mSearchFragment;
    private CarStagingFragment mCarStagingFragment;
    private FeaturedFragment mFeaturedFragment;

    private BadgeView mBuyCarBadgeView;

    private long mBuyCarCount;
    private Object mChoose;

    private FragmentManager fm;
    private Fragment mCurrentFm;
    private List<Fragment> listFragments;



    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_used_car);
        exist = true;
    }

    @Override
    protected void findViews() {
        rbtPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtStage.setTextSize(10.5f);
                rbtSearch.setTextSize(10.5f);
                rbtPeccancy.setTextSize(10.5f);
                rbtMine.setTextSize(10.5f);
                startActivityForResult(new Intent(context,PublishActivity.class),0x886);
                show("发表");
            }
        });
    }

    @Override
    protected void getData() {
        /**
         * 注册用来接收下载apk服务发来的广播
         */
//		IntentFilter filter = new IntentFilter(
//				DownloadAPKService.DOWNLOAD_ACTION);
//		registerReceiver(mReceiver, filter);

        fm = getSupportFragmentManager();
        rgUsedCar.setOnCheckedChangeListener(this);
        listFragments = new ArrayList<Fragment>();
    }

    @Override
    protected void showContent() {
        Intent intent = getIntent();
        String action = intent.getStringExtra(Constant.ACTION);
        if (!TextUtils.isEmpty(action)) {
            if (action.equals(Constant.CAR_PECCANCY)) {
                showFragment(0);
            } else if (action.equals(Constant.CAR_STAGE)) {
                showFragment(1);
            } else if (action.equals(Constant.SLOW_SEARCH)) {
                showFragment(2);
            }else if (action.equals(Constant.MINE)) {
                showFragment(3);
            }
        } else {
            showFragment(0);
        }
    }

    /**
     * 获取服务器
     */
    private void getService() {

    }

    /**
     * 获取版本信息
     */
    private void getUpdate() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.i("HY", "选择");
        showFragmentChoosed(checkedId);
    }

    /**
     * 选择显示的fragment
     *
     * @param i
     */
    public void showFragment(int i) {
        ((RadioButton) rgUsedCar.getChildAt(i)).setChecked(true);
    }

    /**
     * 显示指定位置的Fragment
     */
    private synchronized void showFragmentChoosed(int checkedId) {
        Log.i("HY", "切换fragment");
        FragmentTransaction ft = fm.beginTransaction();
//		ft.setTransition(FragmentTransaction.TRANSIT_NONE);  // 切换动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);  // 切换动画


        switch (checkedId) {
            case R.id.rbt_car_stage:
                rbtStage.setTextSize(11);
                rbtSearch.setTextSize(10.5f);
                rbtPeccancy.setTextSize(10.5f);
                rbtMine.setTextSize(10.5f);
                if (!listFragments.contains(mCarStagingFragment)) {
                    if (mCarStagingFragment == null) {
                        mCarStagingFragment = new CarStagingFragment();
                    }
                    ft.add(R.id.base_layout_view, mCarStagingFragment,
                            mCarStagingFragment.getClass().getSimpleName());
                    listFragments.add(mCarStagingFragment);
                }
                getNewCount();
                mCurrentFm = mCarStagingFragment;

                break;

            case R.id.rbt_slow_search:
                rbtStage.setTextSize(10.5f);
                rbtSearch.setTextSize(11);
                rbtPeccancy.setTextSize(10.5f);
                rbtMine.setTextSize(10.5f);
                if (!listFragments.contains(mSearchFragment)) {
                    if (mSearchFragment == null) {
                        mSearchFragment = new SearchFragment();
                    }
                    ft.add(R.id.base_layout_view, mSearchFragment,
                            mSearchFragment.getClass().getSimpleName());
                    listFragments.add(mSearchFragment);
                }
                getNewCount();
                mCurrentFm = mSearchFragment;


                break;

            case R.id.rbt_car_peccancy:
                rbtStage.setTextSize(10.5f);
                rbtSearch.setTextSize(10.5f);
                rbtPeccancy.setTextSize(11);
                rbtMine.setTextSize(10.5f);
                if (!listFragments.contains(mFeaturedFragment)) {
                    if (mFeaturedFragment == null) {
                        mFeaturedFragment = new FeaturedFragment();
                    }
                    ft.add(R.id.base_layout_view, mFeaturedFragment,
                            mFeaturedFragment.getClass().getSimpleName());
                    listFragments.add(mFeaturedFragment);
                }
                getNewCount();
                mCurrentFm = mFeaturedFragment;
                break;

            case R.id.rbt_mine:
                rbtStage.setTextSize(10.5f);
                rbtSearch.setTextSize(10.5f);
                rbtPeccancy.setTextSize(10.5f);
                rbtMine.setTextSize(11);
                if (!listFragments.contains(mMineFragment)) {
                    if (mMineFragment == null) {
                        mMineFragment = new MineFragment();
                    }
                    ft.add(R.id.base_layout_view, mMineFragment, mMineFragment
                            .getClass().getSimpleName());
                    listFragments.add(mMineFragment);
                }
                getNewCount();
                mCurrentFm = mMineFragment;
                break;
        }
        for (int i = 0; i < listFragments.size(); i++) {
            ft.hide(listFragments.get(i));
        }
        ft.show(mCurrentFm);
        ft.commit();
    }

    /* 双击点击回调 ，双击回到顶部*/
    @Override
    public void doubleClick(DoubleClickRadioButton view) {
        switch (view.getId()) {
        }
    }

    /**
     * 初始化数字显示控件并绑定
     */
    public void getNewCount() {

    }




    /**
     * 按2次返回键退出
     */
    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            show("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            SPUtil.saveAddPopupWindow(context.getApplicationContext(), true);
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    protected void onDestroy() {
        AppContext.startedApp = false;
        exist = false;
        super.onDestroy();
    }



    /**
     * 安装apk
     */
    protected void installApk() {
        File apkfile = new File(AppContext.getAppDir(), DownloadAPKService.APK);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkfile),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x886:
                showFragment(0);
                show("没有发表成功!");
                break;

            default:
                break;
        }
    }
}