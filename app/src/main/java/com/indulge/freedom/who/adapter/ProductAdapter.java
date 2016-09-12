package com.indulge.freedom.who.adapter;

import java.util.List;

import android.content.Context;


import com.indulge.freedom.who.model.Product;


/**
 * 产品列表适配器
 * 
 * @author huangyue
 * 
 */
public class ProductAdapter extends CommonAdapter<Product> {


	public ProductAdapter(Context mContext, List<Product> mList, int itemLayout) {
		super(mContext, mList, itemLayout);
	}


	@Override
	protected void convert(ViewHolder holder, int position) {

	}
	
	
	
	

}
