package com.indulge.freedom.who.ui.activity;


import android.content.Intent;

import android.widget.Button;

import android.widget.TextView;

import com.indulge.freedom.who.R;

import com.indulge.freedom.who.model.Post;
import com.indulge.freedom.who.view.NoScrollGridView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class PublishActivity extends BaseActivity {

    @Bind(R.id.gridView_show_photo)
    NoScrollGridView gridViewPhoto;

    @Bind(R.id.tv_suggest_tittle)
    TextView tvTittle;

    @Bind(R.id.btn_next_step1)
    Button btnNext;


    // 保存每次选取图片返回的localFile
    public static List<String> filePaths = new ArrayList<String>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_up_load_photo);
    }


    @Override
    protected void findViews() {
        initData();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void showContent() {

    }


    private void initData() {

        // 1,上传本地图片到服务器
        String picPath = "sdcard/dsfa.jpg";
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Logger.i("FKH", "上传文件成功:" + bmobFile.getFileUrl());
                    uploadFromImgUrl(bmobFile.getFileUrl());
                } else {
                    toast("上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    /**
     * 2, 文件上传完成后返回了服务器url，将这个url保存到该帖子的集合表字段中
     * @param fileUrl
     */
    public void uploadFromImgUrl(String fileUrl) {
        Post p = new Post();
        p.setsTitle("我的新成绩");
        //添加String类型的数组
        //p.add("hobbys", "唱歌");                         // 添加单个String

        //　这里可以为批量上传　返回的url集合
        //  String[] fils = {fileUrl,"http://www.shanghai666img.jpg"};
        String[] fils = {fileUrl};

        p.addAll("images", Arrays.asList(fils));    // 添加多个url, images字段在数据库中是集合类型
        p.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Logger.i("保存成功----");
                } else {
                    Logger.i("保存失败：" + e.getMessage());
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }


}
