package com.indulge.freedom.who.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.adapter.ShowPhotoGridViewAdapter;
import com.indulge.freedom.who.config.Constant;
import com.indulge.freedom.who.ui.fragment.FullScreenDlgFragment;
import com.indulge.freedom.who.util.ImageUtils;
import com.indulge.freedom.who.util.LocalImageHelper;
import com.indulge.freedom.who.util.NetUtil;
import com.indulge.freedom.who.util.SPUtil;
import com.indulge.freedom.who.util.TipsCustomDialogUtils;
import com.indulge.freedom.who.view.NoScrollGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

import static java.security.AccessController.getContext;

public class publishActivity extends BaseActivity {

    @Bind(R.id.gridView_show_photo)
    NoScrollGridView gridViewPhoto;

    @Bind(R.id.tv_suggest_tittle)
    TextView tvTittle;

    @Bind(R.id.btn_next_step1)
    Button btnNext;

    // 打开选取图片或拍照的弹框
    private PopupWindow addPicWindow;
    private Button btnTakePhoto;// 打开相机拍照按钮
    private Button btnChoosePhoto;// 从相册选取按钮
    private LinearLayout layoutDismiss;// 点击空白处消失

    private List<LocalImageHelper.LocalFile> filesList;// 图片集合
    private int uploadSuccesCount = 0;
    private ShowPhotoGridViewAdapter photoGridViewAdapter;// 显示照片的适配器

    // 保存每次选取图片返回的localFile
    public static List<LocalImageHelper.LocalFile> listFiles = new ArrayList<LocalImageHelper.LocalFile>();
    public static List<String> filePaths = new ArrayList<String>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_up_load_photo);
    }


    @Override
    protected void findViews() {
        initData();
        initPopupWindow();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void showContent() {

    }


    private void initPopupWindow() {
        // TODO Auto-generated method stub
        // 布局文件转换为view对象
        View viewDialog = LayoutInflater.from(publishActivity.this).inflate(
                R.layout.dialog_addpic, null);
        btnTakePhoto = (Button) viewDialog.findViewById(R.id.btn_take_photo);
        btnChoosePhoto = (Button) viewDialog
                .findViewById(R.id.btn_choose_photo);
        layoutDismiss = (LinearLayout) viewDialog
                .findViewById(R.id.llayout_bg_gray);
        addPicWindow = new PopupWindow(viewDialog, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        addPicWindow.setBackgroundDrawable(new BitmapDrawable());
        addPicWindow.setOutsideTouchable(true);
        addPicWindow.setAnimationStyle(R.style.anim_menu_bottombar);

    }

    private void initData() {
        // 显示回传照片的集合
        filesList = new ArrayList<LocalImageHelper.LocalFile>();
        if (listFiles.size() > 1) {
            filesList.addAll(listFiles);
            listFiles.clear();
        }
        // 显示的GridView的adapter
        photoGridViewAdapter = new ShowPhotoGridViewAdapter(publishActivity.this,
                filesList, R.layout.item_show_pic_sell_car
                , publishActivity.this);
        gridViewPhoto.setAdapter(photoGridViewAdapter);
    }





    /**
     * 弹出对话框跳出 选取相册或进入拍照
     */
    public void showAddPicDialog() {
        addPicWindow.showAsDropDown(tvTittle, -tvTittle.getWidth(),
                -tvTittle.getHeight());
        // 拍照

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(publishActivity.this, CameraActivity.class);
                intent.putExtra(Constant.UPLOAD_PHOTO_CHOOSE_COUNT,
                        18 - filesList.size());
                startActivityForResult(intent,
                        ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
                addPicWindow.dismiss();
            }
        });
        // 从相册中选取
        btnChoosePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context,
//                        MultiImageSelectorActivity.class);
//                // whether show camera
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
//                        false);
//                // max select image amount
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
//                        18 - filesList.size());
//                // select mode (MultiImageSelectorActivity.MODE_SINGLE OR
//                // MultiImageSelectorActivity.MODE_MULTI)
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
//                        MultiImageSelectorActivity.MODE_MULTI);
//                // default select images (support array list)
//                startActivityForResult(intent,
//                        ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
//                addPicWindow.dismiss();
            }
        });
        // 点击空白处取消弹框
        layoutDismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPicWindow.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                if (resultCode == Activity.RESULT_OK) {
                    showPaintDialog(
                            ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CROP,
                            "byCrop");
                }
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                Log.i(TAG, "Camera resultCode--" + resultCode + " ");
                if (resultCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {

                    showPaintDialog(
                            ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CAMERA,
                            "byCamera");
                }
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_FULL_BORSWER:
                Log.i(TAG, "fullScreen");
                FullScreenDlgFragment.getInstane().onResult();
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CAMERA:
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CROP:
                for (int i = 0; i < listFiles.size(); i++) {
                    filesList.add(listFiles.get(i));
                    tvTittle.setText(context.getApplicationContext().getResources()
                            .getString(R.string.sell_car_suggest_broswer_photo));
                    photoGridViewAdapter.notifyDataSetChanged();
                }
                listFiles.clear();
                break;
            default:
                break;
        }
    }

    /**
     * 显示是否需要涂抹照片的对话框，并分别处理操作
     *
     * @param requestCode
     *            返回码
     * @param chooseType
     *            获取照片的方式
     */
    private void showPaintDialog(final int requestCode, final String chooseType) {

        new TipsCustomDialogUtils(new AlertDialog.Builder(context).create()) {

            @Override
            public void singleClick() {

            }

            @Override
            public void rightClick() {
//                Intent intent = new Intent(publishActivity.this, EditActivity.class);
//                intent.setType(chooseType);
//                startActivityForResult(intent, requestCode);
                dismissDialog();
            }

            @Override
            public void middleClick() {

            }

            @Override
            public void leftClick() {
                switch (requestCode) {
                    case ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CROP:
                        addChoosePhoto();
                        break;
                    case ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CAMERA:
                        addTakePhoto();
                        break;
                    default:
                        break;
                }
                dismissDialog();
            }
        }.creatCustomDialog(
                context,
                context.getResources().getString(R.string.photo_paint),
                context.getResources().getString(R.string.upload_if_need_paint),
                new String[] {
                        context.getResources().getString(
                                R.string.upload_no_need_paint),
                        context.getResources().getString(
                                R.string.upload_need_paint) }, false, false);

    }

    /**
     * gridView添加拍照获取的图片
     */
    private void addTakePhoto() {
        for (int i = 0; i < listFiles.size(); i++) {
            filesList.add(listFiles.get(i));
            tvTittle.setText(context.getApplicationContext().getResources()
                    .getString(R.string.sell_car_suggest_broswer_photo));
            photoGridViewAdapter.notifyDataSetChanged();
        }
        listFiles.clear();
    }

    /**
     * gridView添加选取的照片
     */
    private void addChoosePhoto() {
        int length = listFiles.size();
        for (LocalImageHelper.LocalFile localFile : listFiles) {
            filesList.add(localFile);
        }
        if (length > 0) {
            tvTittle.setText(context.getApplicationContext().getResources()
                    .getString(R.string.sell_car_suggest_broswer_photo));
            photoGridViewAdapter.notifyDataSetChanged();
        }
        listFiles.clear();
    }

    @SuppressLint("SimpleDateFormat")
    private String setPostPath(int i) {
        String data = new SimpleDateFormat("yyyy\\MM\\dd\\").format(new Date());
        StringBuilder sb = new StringBuilder();
        String[] str = data.split("\\\\");
        if (str[1].startsWith("0")) {
            str[1] = str[1].substring(1, 2);
        }
        if (str[2].startsWith("0")) {
            str[2] = str[2].substring(1, 2);
        }
        for (int j = 0; j < str.length; j++) {
            sb.append(str[j] + "\\");
        }
        data = sb.toString();
        final String path = filesList.get(i).getFilepath();
        String postFilePath = "ErShouCar" + "\\ProductRImage\\" + data
                + SPUtil.getDeviceId(context) + path;
        return postFilePath;
    }

    private void upload(final int i) {
//        ProgressRequestBody body = new ProgressRequestBody(filesList.get(i)
//                .getAbsolutePath(), new UploadCallbacks() {
//
//            @Override
//            public void onProgressUpdate(int percentage) {
//            }
//
//            @Override
//            public void onError() {
//            }
//
//            @Override
//            public void onFinish() {
//            }
//        });
//        upload = Http.getService().uploadPictures(setPostPath(i), body);
        if (NetUtil.isConnnected(context)) {
//            upload.enqueue(new Callback<Result<String>>() {
//                @Override
//                public void onResponse(Response<Result<String>> arg0,
//                                       Retrofit arg1) {
//                    if (!isDestroy) {
//                        Result<String> result = arg0.body();
//                        if (result != null) {
//                            Log.i(TAG, "上传照片返回" + result.toString());
//                            if (result.ResultCode == 1) {
//                                uploadSuccesCount++;
//                                if (i == 0) {
//                                    SellMyCarActivity.filePaths
//                                            .add(result.Item);
//                                    for (int j = 1; j < filesList.size(); j++) {
//                                        upload(j);
//                                    }
//                                } else {
//                                    SellMyCarActivity.filePaths
//                                            .add(result.Item);
//                                }
//                                if (uploadSuccesCount == filesList.size()) {
//                                    if (!upLoadCanceled) {
//                                        Log.i(TAG, "上传完了");
//                                        ShowLoadingDialogUtil
//                                                .closeLoadingDialog();
//                                        SellMyCarActivity parentActivity = (SellMyCarActivity) getActivity();
//                                        parentActivity.showFragmentChoosed(1);
//                                        uploadSuccesCount = 0;
//                                    }
//
//                                }
//                            } else {
//                                show("服务器睡觉了,上传中断");
//                                ShowLoadingDialogUtil.closeLoadingDialog();
//                            }
//                        } else {
//                            show("服务器睡觉了,上传中断");
//                            ShowLoadingDialogUtil.closeLoadingDialog();
//                        }
//                    }
                }

//                @Override
//                public void onFailure(Throwable arg0) {
//                    if (arg0.getMessage() != null) {
//                        Log.i(TAG, "上传失败" + arg0.getMessage());
//                    }
//                    show("服务器睡觉了,上传中断");
//                    ShowLoadingDialogUtil.closeLoadingDialog();
//                }
//            });
//        } else {
//            upload.cancel();
//            noWifiView();
//        }
    }

//    public void onResume() {
//        Log.i(TAG, "upLoadCanceled" + upLoadCanceled + uploadSuccesCount + "=="
//                + filesList.size());
//        if (upLoadCanceled) {
//            upLoadCanceled = false;
//            if (uploadSuccesCount == filesList.size() && uploadSuccesCount >= 6) {
//                if (!upLoadCanceled) {
//                    Log.i(TAG, "上传完了");
//                    ShowLoadingDialogUtil.closeLoadingDialog();
//                    uploadSuccesCount = 0;
//                }
//
//            } else if (uploadSuccesCount > filesList.size()) {
//                ShowLoadingDialogUtil.closeLoadingDialog();
//            }
//        }
//        super.onResume();
//        MobclickAgent.onPageStart("SellCarUpLoadPhotoFragment");
//    }
//
//    public void onPause() {
//        upLoadCanceled = true;
//        super.onPause();
//        MobclickAgent.onPageEnd("SellCarUpLoadPhotoFragment");
//    }

}
