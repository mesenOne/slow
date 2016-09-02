/**
 * 
 */
package com.indulge.freedom.who.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;



/**
 * 使用Google的Gson实现对象和json字符串之间的转换
 * 
 * @author fangxiaotian
 *
 */
public class JsonUtil {
	
	private JsonUtil(){}  
    
    /** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) {  
        Gson gson = new Gson();
        return gson.toJson(obj);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type 
     * @return  
     */  
    public static <T> T fromJson(String str, Type type) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, type);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type  
     * @return  
     */  
    public static <T> T fromJson(String str, Class<T> type) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, type);  
    }  
  
}
