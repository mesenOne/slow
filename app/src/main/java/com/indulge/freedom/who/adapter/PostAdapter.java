package com.indulge.freedom.who.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.indulge.freedom.who.R;
import com.indulge.freedom.who.model.Post;
import com.indulge.freedom.who.util.ScreenUtils;
import com.squareup.picasso.Picasso;


/**
 * 产品列表适配器
 * 
 * @author huangyue
 * 
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
	private final int itemLayout;
	private final Context context;
	private final int mScreenWidth;
	private final int mScreenHeight;
	public ArrayList<Post> datas = new ArrayList<Post>();
	private String imgUrl;
	private ArrayList<String> imageUrl = new ArrayList<String>();

	public PostAdapter(Context context, ArrayList<Post> datas, int itemLayout) {
		this.datas = datas;
		this.context = context;
		this.itemLayout = itemLayout; 
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		mScreenHeight = ScreenUtils.getScreenHeight(context);
	}


	//创建新View，被LayoutManager所调用
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
		return new ViewHolder(view);
	}


	//获取数据的数量
	@Override
	public int getItemCount() {
		return datas.size();
	}


	//将数据与界面进行绑定的操作
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		Post product = datas.get(position);
		viewHolder.txtTitleChins.setText(product.getsTitle());
		if (product.getImages()!=null && product.getImages().size()>0){
			imageUrl.clear();
			viewHolder.carouseView.setVisibility(View.VISIBLE);
			imageUrl.addAll(product.getImages());
			for (int i = 0; i < imageUrl.size(); i++) {
				imgUrl = imageUrl.get(i);
			}
			Picasso.with(context).load(imgUrl)
					.config(Bitmap.Config.RGB_565).into(viewHolder.carouseView);
		}else {
			viewHolder.carouseView.setVisibility(View.GONE);
		}
	}


	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final ImageView carouseView;
		private final TextView txtTitleChins;

		public ViewHolder(View view){
			super(view);
			txtTitleChins = (TextView)view.findViewById(R.id.txt_product_item_title_chinese);
			carouseView = (ImageView)view.findViewById(R.id.img_geogas_item_carouse);
		}
	}
}
