package com.indulge.freedom.who.ui.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.http.Api;
import com.indulge.freedom.who.model.LoginUserInfo;
import com.indulge.freedom.who.permission.PermissionListener;
import com.indulge.freedom.who.permission.PermissionManager;
import com.indulge.freedom.who.ui.dialog.SingleBtnDialog;
import com.indulge.freedom.who.util.NetUtil;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.util.Tools;
import com.indulge.freedom.who.view.CircularAnimUtil;
import com.indulge.freedom.who.view.CustomEditText;
import com.indulge.freedom.who.view.animation.FadeInAnimation;
import com.indulge.freedom.who.view.animation.FadeOutAnimation;
import com.indulge.freedom.who.view.circular_progress_button.CircularProgressButton;
import com.indulge.freedom.who.view.panningview.library.PanningView;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.checks.CookieThumperSample;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;


/**
 * 登录Activity
 *
 * @author fangxiaotian
 */

public class LoginActivity extends BaseActivity {


    @Bind(R.id.ll_other_login)  // 三方登陆布局
            View ll_other_login;
    @Bind(R.id.ll_phonelogin_click)  // 登陆整体部分
            View ll_phonelogin_click;
    @Bind(R.id.ll_phone_num)  // 手机号输入布局
            View ll_phone_num;
    @Bind(R.id.ll_password_str)  // 密码输入布局
            View ll_password_str;
    @Bind(R.id.ll_verification_num)  // 验证码输入布局
            View ll_verification_num;
    @Bind(R.id.ll_forget_password)   // 忘记密码布局
            View ll_forget_password;
    @Bind(R.id.rl_sendSMS)   // 发送短信验证码
            View rl_sendSMS;

    @Bind(R.id.tv_sendSms)   // 短信验证码提示
            TextView tv_sendSms;

    @Bind(R.id.tv_second_verification)   // 短信验证码倒计时时间
            TextView tv_second_verification;

    @Bind(R.id.tv_forget_password)  // 忘记密码
            TextView tvForgetPassword;

    @Bind(R.id.et_phone_num)
    CustomEditText edtPhoneNum;
    @Bind(R.id.et_password)
    CustomEditText edtPassword;
    @Bind(R.id.et_login_verification)
    CustomEditText edtVerification;
    @Bind(R.id.bt_login_now)
    CircularProgressButton btnSignupNow;

    @Bind(R.id.ll_login_now)
    View llsignupNow;

    @Bind(R.id.panningView)
    PanningView panningView;
    @Bind(R.id.ll_who)
    View ll_who;

    @Bind(R.id.tv_title_login_w)
    TextView tvTitleW;
    @Bind(R.id.tv_title_login_h)
    TextView tvTitleH;
    @Bind(R.id.tv_title_login_o)
    TextView tvTitleO;
    @Bind(R.id.text_surface_login)
    TextSurface textSurface;

    @Bind(R.id.iv_login_lookpw)
    ImageView ivLookpw;

    @Bind(R.id.iv_phone_is_login)
    ImageView ivIsPhone;
    @Bind(R.id.rl_login_lookpw)
    View rlLookpw;
    private boolean isOne = true;

    PermissionManager helperPermission;
    int REQUEST_CODE_READPHONESTATE = 0x90;


    private String strPhoneNum;
    private String strPassword;
    private boolean isSignLogin;
    private String strVerification;
    private boolean isLookPw = true;
    private String isNextOpertion = "";
    private boolean isClickaSelectPhone = false;
    private boolean isOtherLogin;

    protected static int MSGCODE = 1001;
    public static int mimute = 60; // 记录一分钟

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            // 返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
            if (source.equals(" ")) {
                return "";
            } else {
                return null;
            }
        }
    };

    public MyHandler mHandler = new MyHandler(this);

    /**
     * 解决内存泄漏(将当前activity作为弱引用在handler中使用)
     */
    private static class MyHandler extends Handler {

        private final WeakReference<LoginActivity> mActivity;

        public MyHandler(LoginActivity activity) {
            mActivity = new WeakReference<LoginActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final LoginActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == MSGCODE) {
                    mimute = mimute - 1;
                    if (activity.rl_sendSMS!=null){
                        activity.rl_sendSMS.setClickable(false);
                        activity.rl_sendSMS.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_btn_bg_lift_fillet_blue_sms));
                        activity.tv_second_verification.setText("(" + mimute + ")");
                        activity. tv_sendSms.setText("重新发送");

                        if (mimute == 0) {
                            activity. rl_sendSMS.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_btn_bg_lift_fillet_blue_first));
                            activity. tv_sendSms.setText("重新发送");
                            activity. tv_second_verification.setText("60");
                            activity. rl_sendSMS.setClickable(true);
                            mimute = 60;
                        } else {
                            activity.mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    activity.mHandler.sendEmptyMessage(MSGCODE);
                                }
                            }, 1000);
                        }
                    }
                }else if(msg.what==0x11){
                    activity.testGetCurrentUser(true);  // 登陆成功获取本地用户
                }else if(msg.what==0x22){
                    activity.testGetCurrentUser(false);  // 注册成功获取本地用户
                }else if(msg.what==0x33){
                    activity. show("用户名或密码错误");
                }else if(msg.what==0x44){
                    activity.show("验证码错误");
                    activity.edtVerification.isVisibleDeleteIcon(true);
                }else if(msg.what==0x55){
                    if(activity.edtPhoneNum!=null){
                        activity.edtPhoneNum.setText("");
                        activity.edtPassword.setText("");
                    }
                }
            }
        }
    }






    @SuppressLint("InlinedApi")
    @Override
    public void setContentView() {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
//		int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
//		//获得当前窗体对象
//		Window windows=LoginActivity.this.getWindow();
//		//设置当前窗体为全屏显示
//		windows.setFlags(flag, flag);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            Window window = this.getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }









    @Override
    public void getData() {
        //初始化BmobSDK
        Bmob.initialize(this, Api.BMOB_APPID);

        initPermission();

//        AssetManager mgr=getAssets();//得到AssetManager
//        Typeface tf= Typeface.createFromAsset(mgr, "fonts/lingHeiTi.ttf");   //zhiheiti.ttf  lingHeiTi.ttf  HeiJian.TTF
//        btnSignupNow.setTypeface(tf);

        panningView.startPanning();
        textSurface.postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, 800);

        // 不能输空格+字数限制
        edtPhoneNum.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(11)});
        edtPassword.setFilters(new InputFilter[]{filter});
        edtPhoneNum.addTextChangedListener(new TextWatcher() {
            public CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (s.length() == 1) {
                        if (isOne) {
                            FadeOutAnimation fadeOutAnimation = new FadeOutAnimation(ll_other_login);
                            fadeOutAnimation.setDuration(50);
                            fadeOutAnimation.animate();

                            FadeInAnimation fadeInAnimation = new FadeInAnimation(ll_password_str);
                            fadeInAnimation.setDuration(500);
                            fadeInAnimation.animate();

                            FadeInAnimation fadeInAnimationTXT = new FadeInAnimation(ll_forget_password);
                            fadeInAnimationTXT.setDuration(500);
                            fadeInAnimationTXT.animate();

                            FadeInAnimation fadeInAnimationBtn = new FadeInAnimation(llsignupNow);
                            fadeInAnimationBtn.setDuration(500);
                            fadeInAnimationBtn.animate();



                            isOne = false;
                        }
                    }
                } else {
                    FadeOutAnimation fadeOutAnimation = new FadeOutAnimation(null, ll_password_str, "out_in", ll_other_login, 1100);
                    fadeOutAnimation.setDuration(300);
                    fadeOutAnimation.animate();

                    ll_verification_num.setVisibility(View.GONE);
                    ll_forget_password.setVisibility(View.INVISIBLE);
                    llsignupNow.setVisibility(View.INVISIBLE);

                    isOne = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (temp.length() == 11) {
                    isSignUp(true); // 做校验
                } else {
                    FadeOutAnimation fadeOutAnimation = new FadeOutAnimation(ll_verification_num);
                    fadeOutAnimation.setDuration(500);
                    fadeOutAnimation.animate();
                    edtVerification.setText("");
                    ivIsPhone.setImageResource(R.drawable.phone_3);
                }
            }
        });



        edtPassword.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData(R.string.only_can_input).toCharArray();
                return data;
            }
        });

    }


    private void initPermission() {
        helperPermission = PermissionManager.with(LoginActivity.this)
                //添加权限请求码
                .addRequestCode(REQUEST_CODE_READPHONESTATE)
                //设置权限，可以添加多个权限
                .permissions(Manifest.permission.READ_PHONE_STATE)
                //设置权限监听器
                .setPermissionsListener(new PermissionListener() {

                    @Override
                    public void onGranted() {
                        //当权限被授予时调用
                        Log.i("FKH","访问手机状态许可授予");
                        isSaveDeviceIdOrDeviceToken();  // 保存设备号(用于统计和记录设备使用状态)
                    }

                    @Override
                    public void onDenied() {
                        //用户拒绝该权限时调用
                        show("没有访问手机状态权限,可能会导致某些功能无法正常运行");
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        //当用户拒绝某权限时并点击`不再提醒`的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）
                        final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(LoginActivity.this, "确定", "为了为您提供更好的应用体验,需要运行时所需基础权限");
                        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm() {
                                //必须调用该`setIsPositive(true)`方法
                                helperPermission.setIsPositive(true);
                                helperPermission.request();
                                isSaveDeviceIdOrDeviceToken();
                                singleBtnDialog.dismiss();
                            }
                        });
                        singleBtnDialog.show();
                    }
                })
                //请求权限
                .request();
    }


    // 验证手机号是否可以注册
    private void isSignUp(final boolean isAnamtion) {
        if (NetUtil.isConnnected(this)) {

            if (!Tools.isPhone(edtPhoneNum.getText().toString().trim())) {
                ivIsPhone.setImageResource(R.drawable.no_phone);
                edtPhoneNum.requestFocus();
            } else {
                Bmob.initialize(this, Api.BMOB_APPID);
                ivIsPhone.setImageResource(R.drawable.phone_3);
                BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
                query.addWhereEqualTo("username", edtPhoneNum.getText().toString().trim());
                addSubscription(query.findObjects(new FindListener<BmobUser>() {
                    @Override
                    public void done(List<BmobUser> object, BmobException e) {

                        if (e == null) {
                            if (object.size() > 0) {
                                // 账号已经存在，不能再次注册
                                isSignLogin = false;
                                Log.i("FKH", "查询用户成功：" + object.size());
                            } else {
                                // 没有注册 显示验证码，走注册流程
                                if (isAnamtion){
                                    FadeInAnimation fadeInAnimation = new FadeInAnimation(ll_verification_num);
                                    fadeInAnimation.setDuration(500);
                                    fadeInAnimation.animate();
                                }else{
                                    ll_verification_num.setVisibility(View.VISIBLE);
                                }

                                isSignLogin = true;
                                Log.i("FKH", "查询用户成功：" + object.size());
                            }
                            isNextOpertion = "";
                        } else {
                            Log.i("FKH", "更新用户信息失败:" + e.getMessage());
                            show("请求服务器异常,请检查当前网络是否可用");
                            isNextOpertion = e.getMessage();
                        }
                    }

                }));
            }

        } else {
        }

    }




    /**
     * 立即登录
     */
    private void LoginNow() {
        BmobUser user = new BmobUser();
        user.setUsername(edtPhoneNum.getText().toString().trim());
        user.setPassword(edtPassword.getText().toString().trim());

        //新增加的Observable
        user.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
            @Override
            public void onCompleted() {   // 已完成
                log("----onCompleted----");
            }

            @Override
            public void onError(Throwable e) {
                simulateErrorProgress(btnSignupNow);
                mHandler.sendEmptyMessageDelayed(0x33,1500);
                Log.i("FKH", "登陆失败:" + new BmobException(e).getMessage() + "--code--:" + new BmobException(e).getErrorCode());
            }

            @Override
            public void onNext(BmobUser bmobUser) {
                simulateSuccessProgress(btnSignupNow);
                mHandler.sendEmptyMessageDelayed(0x11,1500);
            }
        });
    }


    /**
     * 获取本地用户
     */
    private void testGetCurrentUser(boolean isTag) {
        //V3.4.5版本新增加getObjectByKey方法获取本地用户对象中某一列的值
        String username = (String) BmobUser.getObjectByKey("username");
        String userphone = (String) BmobUser.getObjectByKey("mobilePhoneNumber");
        Integer age = (Integer) BmobUser.getObjectByKey("age");
        Boolean sex = (Boolean) BmobUser.getObjectByKey("sex");
        String objectId = (String) BmobUser.getObjectByKey("objectId");
        String sessionToken = (String) BmobUser.getObjectByKey("sessionToken");

        if (isTag){
            log("登陆 后获取本地用户缓存字段: username：" + username + ",\nage：" + age + ",\nsex：" + sex +
                    ",\nuserphone:" + userphone + ",\nobjectId:" + objectId + ",\nsessionToken:" + sessionToken);
//            show(username + "登陆成功");
        }else{
            // 注意 这里获取的是缓存值，只有在登陆之后，才会自动缓存用户数据，注册之后不缓存
            log("注册登陆 后获取本地用户缓存字段: username：" + username + ",\nage：" + age + ",\nsex：" + sex +
                    ",\nuserphone:" + userphone + ",\nobjectId:" + objectId + ",\nsessionToken:" + sessionToken);
        }

        // 先将颜色展出铺满，然后启动新的Activity并finish当前Activity.
        Intent intent = new Intent(LoginActivity.this, HomePagerActivity.class);
        CircularAnimUtil.startActivityThenFinish(LoginActivity.this, intent, btnSignupNow, R.color.colorPrimaryMain);
    }


    private void isVerification() {
        //这种方式比较灵活，可以在注册或登录的同时设置保存多个字段值
        final LoginUserInfo user = new LoginUserInfo();
        final String userNum = edtPhoneNum.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        user.setUsername(userNum);
        user.setMobilePhoneNumber(userNum);
        user.setPassword(password);
        //注意：不能用save方法进行注册//
        user.setAge(25);//
        user.setSex(true);  // false为男
        addSubscription(user.signOrLogin(strVerification, new SaveListener<LoginUserInfo>() {

            @Override
            public void done(final LoginUserInfo myUser, BmobException e) {
                if (e == null) {
                    BmobUser bu2 = new BmobUser();
                    bu2.setUsername(userNum);
                    bu2.setPassword(password);
                    bu2.login(new SaveListener<BmobUser>() {

                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e==null){
                                simulateSuccessProgress(btnSignupNow);
                                mHandler.sendEmptyMessageDelayed(0x22,1500);
                            }else{
                                loge(e);
                            }
                        }
                    });

                } else {
                    simulateErrorProgress(btnSignupNow);
                    mHandler.sendEmptyMessageDelayed(0x44,1500);
                    loge(e);
                }
            }

        }));

    }


    @Override
    public void showContent() {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }




    @OnClick({
            R.id.tv_forget_password, R.id.bt_login_now,R.id.et_password,R.id.ll_phonelogin_click, R.id.rl_sendSMS, R.id.rl_login_lookpw, R.id.img_weixin, R.id.img_qq, R.id.img_weibo,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_login_lookpw:
                if (isLookPw) {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivLookpw.setImageResource(R.drawable.pw_look);
                    isLookPw = false;
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivLookpw.setImageResource(R.drawable.pw_nolook);
                    isLookPw = true;
                }
                break;
            case R.id.et_password:
                isSignUp(false);
                break;

            case R.id.rl_sendSMS:
                // 收起软键盘
                InputMethodManager sendSms = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (sendSms != null) {
                    sendSms.hideSoftInputFromWindow(getWindow().getDecorView()
                            .getWindowToken(), 0);
                }


                BmobSMS.requestSMSCode(edtPhoneNum.getText().toString().trim(), "who_注册用", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        if (ex == null) {//验证码发送成功
                            show("验证码已发送，请注意查看");
                            mHandler.sendEmptyMessage(1001);
                            Log.i("FKH", "发送成功,短信id：" + smsId);//用于查询本次短信发送详情
                        } else {
                            show("验证码发送失败:" + ex.getMessage() + "--code--:" + ex.getErrorCode());
                        }
                    }
                });
                break;

            case R.id.ll_phonelogin_click:
                isClickaSelectPhone = true;
                FadeOutAnimation fadeOutAnimation = new FadeOutAnimation(context, ll_phonelogin_click, "out_in", ll_phone_num, 200);
                fadeOutAnimation.setDuration(300);
                fadeOutAnimation.animate();
//                String userphone = (String) BmobUser.getObjectByKey("mobilePhoneNumber");
                break;
            case R.id.tv_forget_password:
//			startActivity(new Intent(this, ForgetPwIpPhoneActivity.class));
                break;
            case R.id.img_weixin:// 微信登录

                break;
            case R.id.img_qq:// QQ登录

                break;
            case R.id.img_weibo:// 微博登录

                break;
            case R.id.bt_login_now:
                edtVerification.isVisibleDeleteIcon(false);
                isSignUp(false);
                if (btnSignupNow.getProgress() == 0) {
                    // 收起软键盘
                    InputMethodManager imms = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imms != null) {
                        imms.hideSoftInputFromWindow(getWindow().getDecorView()
                                .getWindowToken(), 0);
                    }

                    strPhoneNum = edtPhoneNum.getText().toString().trim();
                    strPassword = edtPassword.getText().toString().trim();
                    strVerification = edtVerification.getText().toString().trim();
                    if (Tools.checkIsOnLine(context)) {
                        if (strPhoneNum.length() == 0) {
                            toast("请输入手机号");
                            edtPhoneNum.setText("");
                            edtPhoneNum.requestFocus();
                        } else if (!Tools.isPhone(strPhoneNum)) {
                            edtPhoneNum.requestFocus();
                        } else if (strPassword.length() == 0) {
                            toast("请输入密码");
                            edtPassword.setText("");
                            edtPassword.requestFocus();
                        } else if (strPassword.length() < 6) {
                            toast("密码至少需要6位");
                            edtPassword.requestFocus();
                        } else if (!isSignLogin) {
                            if (TextUtils.isEmpty(isNextOpertion)) {
                                LoginNow();   // 登陆
                            } else {
                                show("请求服务器异常,请检查当前网络是否可用");
                            }
                            Log.i("FKH", isSignLogin + "");
                        } else {
                            if (TextUtils.isEmpty(isNextOpertion)) {
                                if (TextUtils.isEmpty(strVerification)) {
                                    toast("请输入验证码");
                                } else {
                                    if (strVerification.length() != 6) {
                                        toast("验证码长度不够");
                                    } else {
                                        isVerification();  // 校验验证码，完了登陆
                                    }
                                }
                            } else {
                                show("请求服务器异常,请检查当前网络是否可用");
                            }
                        }
                    } else {
                        show("请检查网络链接");
                    }

                } else {
                    btnSignupNow.setProgress(0);
                }

                break;
            default:
                break;
        }
    }






    /**
     * 按2次返回键退出
     */
    private long exitTime = 0;
    @Override
    public void onBackPressed() {

        if (isClickaSelectPhone ) {
            isClickaSelectPhone = false;

            ll_password_str.setVisibility(View.INVISIBLE);
            ll_verification_num.setVisibility(View.GONE);
            ll_forget_password.setVisibility(View.INVISIBLE);
            llsignupNow.setVisibility(View.INVISIBLE);
            ll_phone_num.setVisibility(View.INVISIBLE);

            FadeInAnimation fade2InAnimation = new FadeInAnimation(ll_phonelogin_click);
            fade2InAnimation.setDuration(500);
            fade2InAnimation.animate();


            textSurface.postDelayed(new Runnable() {
                @Override
                public void run() {
                    show();
                }
            }, 700);

            if(!TextUtils.isEmpty(edtPhoneNum.getText().toString())){
                FadeInAnimation fadeinAnimation = new FadeInAnimation(ll_other_login);
                fadeinAnimation.setDuration(1000);
                fadeinAnimation.animate();
                mHandler.sendEmptyMessageDelayed(0x55,1300);
            }

        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                show("登陆开启精彩生活!");
                startActivity(new Intent(LoginActivity.this,HomePagerActivity.class));
                exitTime = System.currentTimeMillis();
            } else {
                SPUtil.saveAddPopupWindow(context.getApplicationContext(), true);
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }


    /**
     * 成功的btn动画效果
     * @param button
     */
    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    /**
     * 失败的btn动画效果
     * @param button
     */
    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }


    private void show() {

        if(textSurface!=null){
            textSurface.setVisibility(View.VISIBLE);
            textSurface.reset();
            CookieThumperSample.play(textSurface, this.getAssets());
        }
//		Rotation3DSample.play(textSurface);   //  三级 3d旋转
//		AlignSample.play(textSurface);         //  二级 闪片
//		ColorSample.play(textSurface);         // 二级 变色
//		ScaleTextSample.run(textSurface);       // 一级 字放大动画
//		ShapeRevealLoopSample.play(textSurface);  // 四级
//		ShapeRevealSample.play(textSurface);   // 和上面差不多
//		SlideSample.play(textSurface);        // 四级  炫酷不规则游动显示隐藏
//		SurfaceScaleSample.play(textSurface);  // 三级  一层一层放大，由远及近的效果
//		SurfaceTransSample.play(textSurface);  // 二级 z字形路径
    }


    @Override
    protected void onRestart() {
        textSurface.postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, 9000);
        super.onRestart();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x90:
                helperPermission.onPermissionResult(permissions, grantResults);
                break;
        }
    }


    public void isSaveDeviceIdOrDeviceToken() {

        // 获取device ID
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        String serial = tm.getSimSerialNumber();
        String androidId = android.provider.Settings.Secure.getString(
                getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "";
        }
        if (TextUtils.isEmpty(serial)) {
            serial = "";
        }
        if (TextUtils.isEmpty(androidId)) {
            androidId = "";
        }

        if (TextUtils.isEmpty(SPUtil.getDeviceId(LoginActivity.this))) {
            SPUtil.saveDeviceId(context, deviceId);
        } else {
            if (!deviceId.equals(SPUtil.getDeviceId(LoginActivity.this))) {
                SPUtil.saveDeviceId(context, "");
                SPUtil.saveDeviceId(context, deviceId);
            }
        }

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) deviceId.hashCode() << 32) | serial.hashCode());

        if (TextUtils.isEmpty(SPUtil.getDeviceToken(LoginActivity.this))) {
            SPUtil.saveDeviceToken(context, deviceUuid.toString());
            Log.d("FKH", "deviceUuid=" + SPUtil.getDeviceToken(LoginActivity.this));
        } else {
            if (!deviceId.equals(SPUtil.getDeviceToken(LoginActivity.this))) {
                SPUtil.saveDeviceToken(context, "");
                SPUtil.saveDeviceToken(context, deviceUuid.toString());
            }
        }
    }



    public String getStringData(int id) {
        return getResources().getString(id);
    }


    @Override
    protected void onDestroy() {
        panningView.stopPanning();
        panningView.isPanning();
        super.onDestroy();
    }



    @Override
    public void onPause() {
        super.onPause();
    }
}
