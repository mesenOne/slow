package com.indulge.freedom.who.model;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by fengkehua on 2016/10/24.
 */

public class Post extends BmobObject {

    private String sTitle;
    ArrayList<String> images = new ArrayList<String>();
    private String sContent;
    private BmobRelation likes;
    private BmobPointer author;


    @Override
    public String toString() {
        return "Post{" +
                "sTitle='" + sTitle + '\'' +
                ", images=" + images +
                ", sContent='" + sContent + '\'' +
                ", likes=" + likes +
                ", author=" + author +
                '}';
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Post(String sTitle) {
        this.sTitle = sTitle;
    }

    public Post() {
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }


    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public BmobPointer getAuthor() {
        return author;
    }

    public void setAuthor(BmobPointer author) {
        this.author = author;
    }


}
