package com.indulge.freedom.who.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.indulge.freedom.who.AppContext;


public class CommonUtils {
    public static final int NO_DATA = 0;
    public static final int NO_NET = 1;
    public static final int EXCEPTION = 2;

    /**
     * 将子View从它父View中移除
     *
     * @param child
     */
    public static void removeSelfFromParent(View child) {
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(child);//将子VIew从父View中移除
            }
        }
    }
    
    
    /**
     * 将assert文件夹下的文件中的内容转换成String @param filename assert文件夹下的文件名 @param ctx 上下文环境 @return
     */
    public static String getStringFromAssert(String filename, Context ctx) {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        try {
            is = ctx.getAssets().open(filename);
            int temp = 0;
            byte[] buffer = new byte[4096];
            while ((temp = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, temp, "UTF-8"));
            }
        } catch (IOException e) {
            Log.e("Common", Arrays.toString(e.getStackTrace()) + e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            	Log.e("Common", Arrays.toString(e.getStackTrace()) + e.getMessage());
            }
        }
        return sb.toString();
    }
    

    /**
     * 获取Resources对象
     *
     * @return
     */
    public static Resources getResources() {
        return AppContext.getInstance().getResources();
    }

    /**
     * 获取字符串数组资源
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    

}
