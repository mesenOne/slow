package com.indulge.freedom.who.ui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.indulge.freedom.who.AppContext;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;

import com.indulge.freedom.who.model.City;
import com.indulge.freedom.who.model.ProductCount;
import com.indulge.freedom.who.model.VersionInfo;
import com.indulge.freedom.who.receiver.JPushReceiver;
import com.indulge.freedom.who.service.DownloadAPKService;
import com.indulge.freedom.who.ui.dialog.CustomDialog;
import com.indulge.freedom.who.ui.fragment.CarPeccancyFragment;
import com.indulge.freedom.who.ui.fragment.CarStagingFragment;
import com.indulge.freedom.who.ui.fragment.MineFragment;
import com.indulge.freedom.who.util.NetUtil;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.view.DoubleClickRadioButton;
import com.readystatesoftware.viewbadger.BadgeView;

import butterknife.Bind;


/**
 * 二手车界面加载四个Fragment
 *
 * @author huhuan
 */
public class UsedCarActivity extends BaseActivity implements
        OnCheckedChangeListener, DoubleClickRadioButton.DoubleClickListener {
    public static boolean exist;// 是否存在
    private static final int ACTION_MSG = 0x12;
    @Bind(R.id.base_ragroup_bottom)
    RadioGroup rgUsedCar;

//    DoubleClickRadioButton mBuyCarButton;
    private MineFragment mMineFragment;
    private CarStagingFragment mCarStagingFragment;
    private CarPeccancyFragment mCarPeccancyFragment;

    private BadgeView mBuyCarBadgeView;

    private long mBuyCarCount;
    private Object mChoose;

    private FragmentManager fm;
    private Fragment mCurrentFm;
    private List<Fragment> listFragments;

    private LocationListener mLocationListener;// Gps消息监听器
    private LocationManager mLocationManager;
    private String cityName;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_used_car);
        exist = true;
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
            } else if (action.equals(Constant.MINE)) {
                showFragment(2);
            }
        } else {
            showFragment(0);
        }
//        mBuyCarButton.setOnDoubleClickListener(this);// 设置双击事件
//		getService();
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
            case R.id.rbt_car_peccancy:
                if (!listFragments.contains(mCarPeccancyFragment)) {
                    if (mCarPeccancyFragment == null) {
                        mCarPeccancyFragment = new CarPeccancyFragment();
                    }
                    ft.add(R.id.base_layout_view, mCarPeccancyFragment,
                            mCarPeccancyFragment.getClass().getSimpleName());
                    listFragments.add(mCarPeccancyFragment);
                }
                getNewCount();
                mCurrentFm = mCarPeccancyFragment;
                break;

            case R.id.rbt_mine:
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
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ErShouCarTimeStamp", SPUtil.getUsedCarTime(context));
//		params.put("BuyCarTimeStamp", SPUtil.getBuyCarTime(context));
//		Call<Result<ProductCount>> call = Http.getService().getProductCount(
//				Http.getParams(params));
//		call.enqueue(new Callback<Result<ProductCount>>() {
//
//			@Override
//			public void onResponse(Response<Result<ProductCount>> response,
//					Retrofit retrofit) {
//				if (!isDestroy) {
//					if (response != null) {
//						Result<ProductCount> result = response.body();
//						if (result != null) {
//							Log.i("HY", result.toString());
////							ProductCount productCount = result.result;
////							mBuyCarCount = productCount.getBuyCarCount();
//							showNumber();
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable arg0) {
//
//			}
//
//		});
    }

    /**
     * 显示数字控件
     */
    private void showNumber() {
        if (mBuyCarCount != 0) {
            if (mBuyCarCount < 100) {
                mBuyCarBadgeView.setText(mBuyCarCount + "");
            } else {
                mBuyCarBadgeView.setText(99 + "+");
            }
            mBuyCarBadgeView.show();
        } else {
            mBuyCarBadgeView.hide();
        }
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
        SPUtil.saveAddPopupWindow(context.getApplicationContext(), true);
        AppContext.startedApp = false;
        exist = false;
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getIntExtra(DownloadAPKService.STATUS, 0)) {
                    case DownloadAPKService.LOADING:// 正在下载状态
                        DownloadAPKService.sDownloadStatus = DownloadAPKService.LOADING;
                        break;
                    case DownloadAPKService.PAUSE:// 暂停状态
                        DownloadAPKService.sDownloadStatus = DownloadAPKService.PAUSE;
                        break;
                    case DownloadAPKService.COMPLETE:// 完成状态
                        DownloadAPKService.sDownloadStatus = DownloadAPKService.COMPLETE;
                        show("最新版本下载完成");
                        installApk();
                        break;
                    case DownloadAPKService.FAIL:// 异常，重开服务继续下载
                        Log.i("HY", "异常");
                        if (DownloadAPKService.sDownloadStatus != DownloadAPKService.COMPLETE) {
                            DownloadAPKService.sDownloadStatus = DownloadAPKService.PAUSE;
                        }
                        break;
                }
            }
        }
    };

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


}