package com.indulge.freedom.who.util;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
  
/* 
 * 监听输入内容是否超出最大长度，并设置光标位置 
 * */  
public class MaxLengthWatcher implements TextWatcher {  
  
    private int maxLen = 0;  
    private EditText editText = null; 
    private TextView view;
    private Context context;
      
      
    public MaxLengthWatcher(Context context,int maxLen, EditText editText,TextView view) {  
        this.maxLen = maxLen;  
        this.editText = editText; 
        this.view=view;
        this.context=context;
    }  
    public MaxLengthWatcher(Context context,int maxLen, EditText editText) {  
    	this.maxLen = maxLen;  
    	this.editText = editText; 
    	this.context=context;
    }  
  
    public void afterTextChanged(Editable arg0) {  
        // TODO Auto-generated method stub  
          
    }  
  
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,  
            int arg3) {  
        // TODO Auto-generated method stub  
          
    }  
  
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {  
        // TODO Auto-generated method stub  
        Editable editable = editText.getText();  
        int len = editable.length();  
          
        if(len > maxLen)  
        {  
        	ToastUtil.show(context.getApplicationContext(), "最多可输入"+maxLen+"字符");
            int selEndIndex = Selection.getSelectionEnd(editable);  
            String str = editable.toString();  
            //截取新字符串  
            String newStr = str.substring(0,maxLen);  
            editText.setText(newStr);  
            editable = editText.getText();  
              
            //新字符串的长度  
            int newLen = editable.length();  
            //旧光标位置超过字符串长度  
            if(selEndIndex > newLen)  
            {  
                selEndIndex = editable.length();  
            }  
            //设置新光标所在的位置  
            Selection.setSelection(editable, selEndIndex);  
        }  
        if(view!=null){
        	view.setText(editable.toString().replaceAll(" ", "").length() + "/" + maxLen);  
        }
    }  
  
} 