package com.indulge.freedom.who.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
/**
 * 万能适配器基础类
 * @author huhuan
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	public List<T> mList;
	protected final int mItemLayoutId; 
	
	public CommonAdapter(Context mContext, List<T> mList,int itemLayout) {
		this.mContext = mContext;
		this.mList = mList;
		mInflater = LayoutInflater.from(mContext);
		mItemLayoutId = itemLayout;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
		convert(viewHolder, position); 
		return viewHolder.getConvertView();
	}
	
	protected abstract void convert(ViewHolder holder , int position);
	
	protected void loadImage(ImageView imageView, String url) {
		if (!TextUtils.isEmpty(url)) {
			Picasso.with(mContext.getApplicationContext()).load(url)
					.config(Bitmap.Config.RGB_565).into(imageView);
		}
	}
	
}

