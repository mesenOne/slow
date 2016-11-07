package com.indulge.freedom.who.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.model.Banner;
import com.indulge.freedom.who.util.ToastUtil;
import com.squareup.picasso.Picasso;

public class GalleryAdapter extends ArrayAdapter<Banner> {
	Context context;
	private ArrayList<Banner> list;
	int layoutId;
	Holder holder;
	public View view;
	public int currPosition = 0;

	public GalleryAdapter(Context context, int textViewResourceId,
						  ArrayList<Banner> list) {
		super(context, R.layout.news_list_item, list);
		this.context = context;
		this.list = list;
		layoutId = textViewResourceId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Banner getItem(int position) {
		return list.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		RelativeLayout layout;

		if (convertView == null) {

			layout = (RelativeLayout) View.inflate(context, layoutId, null);

			holder = new Holder();

			holder.rlItem =  layout.findViewById(R.id.rl_list_item_gallery);
			holder.mImg = (ImageView) layout.findViewById(R.id.id_index_gallery_item_image);
			holder.viewGallery1 =  layout.findViewById(R.id.view_gallery1);
			holder.viewGallery2 =  layout.findViewById(R.id.view_gallery2);
			holder.title = (TextView) layout.findViewById(R.id.txt_gallery_center);

//			AssetManager mgr = context.getAssets();//得到AssetManager
//			Typeface tf = Typeface.createFromAsset(mgr, "fonts/AppMainTXT.ttf");   //zhiheiti.ttf  lingHeiTi.ttf  HeiJian.TTF
//			holder.title.setTypeface(tf);

			layout.setTag(holder);

		} else {
			layout = (RelativeLayout) convertView;
			view = layout;
			holder = (Holder) layout.getTag();
		}


		if (position != (list.size())-1){
			if (position==0){
				holder.viewGallery1.setVisibility(View.VISIBLE);
				holder.viewGallery2.setVisibility(View.GONE);
			}else{
				holder.viewGallery1.setVisibility(View.VISIBLE);
				holder.viewGallery2.setVisibility(View.GONE);
			}
		}else{
			holder.viewGallery1.setVisibility(View.VISIBLE);
			holder.viewGallery2.setVisibility(View.VISIBLE);
		}


		if (position==0){
			holder.title.setText("美食");
		}else if(position==1){
			holder.title.setText("近旅");
		}else if(position==2){
			holder.title.setText("演唱会");
		}else if(position==3){
			holder.title.setText("租房");
		}else if(position==4){
			holder.title.setText("户外烧烤");
		}else if(position==5){
			holder.title.setText("SHOP");
		}else{
			holder.title.setText("SLOW");
		}



		Banner item = list.get(position);
		Log.i("FKH",item.toString());
//		holder.title.setText(newsSource);

		Picasso.with(context.getApplicationContext()).load(item.getsImage())
				.config(Bitmap.Config.RGB_565).into(holder.mImg);

		holder.rlItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtil.show(context,position+"");
			}
		});

		Log.v("Test", "lo frm newsadpater");

		return layout;
	}

	private class Holder {
		public TextView title;
		public ImageView mImg;
		public View viewGallery1;
		public View viewGallery2;
		public View rlItem;
	}
	public int getCurrentPosition(){
		return currPosition;
	}
}
