package com.indulge.freedom.who.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.config.Constant;
import com.indulge.freedom.who.ui.activity.publishActivity;
import com.indulge.freedom.who.ui.fragment.FullScreenDlgFragment;
import com.indulge.freedom.who.util.LocalImageHelper;
import com.indulge.freedom.who.util.ScreenUtils;
import com.indulge.freedom.who.util.ToastUtil;
import com.indulge.freedom.who.view.LabelView;
import com.squareup.picasso.Picasso;


/**
 * 
 * 显示拍照或选取照片的适配器（卖车页面）
 * 
 * @author huhuan
 * 
 */
public class ShowPhotoGridViewAdapter extends CommonAdapter<LocalImageHelper.LocalFile> {
	private static final int TYPE_ADD = 0;
	private static final int TYPE_NORMAL = 1;
	private FragmentManager fm;
	private boolean showAddPic = true;
	publishActivity fragment;
	public ShowPhotoGridViewAdapter(Context mContext, List<LocalImageHelper.LocalFile> mList,
			int itemLayout,
									publishActivity fragment) {
		super(mContext, mList, itemLayout);
		this.fm = fm;
		this.fragment = fragment;
	}

	@Override
	public int getCount() {
		return showAddPic?mList.size() + 1:mList.size();
	}
	public void setShowAddPic(boolean b) {
		if (showAddPic == b)
			return;

		showAddPic = b;
		notifyDataSetChanged();
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public int getItemViewType(int position) {
		if (showAddPic) {
			return position == 0 ? TYPE_ADD : TYPE_NORMAL;
		}
		return TYPE_NORMAL;
	}

	@Override
	protected void convert(ViewHolder viewHolder, final int position) {
		ImageView imageView = viewHolder.getView(R.id.img_item_pic);
		Button btnDelete = viewHolder.getView(R.id.btn_item_delete);
		LabelView lbv = viewHolder.getView(R.id.lvb_first);

		if (position < mList.size()) {
			if (position == 0) {
				lbv.setVisibility(View.VISIBLE);
				lbv.setText("封面");
				lbv.setBgColor(mContext.getResources().getColor(
						R.color.color_first_page));
			} else {
				lbv.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(mList.get(position).getOriginalUri())) {
				Picasso.with(mContext)
						.load(mList.get(position).getOriginalUri())
						.config(Bitmap.Config.RGB_565)
						.resize(ScreenUtils.getQuarterWidth(mContext),
								(int) (ScreenUtils.getQuarterWidth(mContext) * Constant.THUMAIL_RATIO))
						.centerCrop().into(imageView);
			}
			btnDelete.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.VISIBLE);
			// 长按设为封面
			imageView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					LocalImageHelper.LocalFile temp = mList.get(position);
					mList.remove(position);
					mList.add(0, temp);
					ToastUtil.show(mContext, "设为封面成功");
					notifyDataSetChanged();
					return false;
				}
			});
			// 点击全屏预览
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					FullScreenDlgFragment.newInstance(mContext, mList,
							position, ShowPhotoGridViewAdapter.this).show(fm,
							FullScreenDlgFragment.TAG_NAME);
				}
			});
			btnDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!showAddPic){
						setShowAddPic(true);
					}
					mList.get(position).deleteSelf();
					mList.remove(position);
					notifyDataSetChanged();
				}
			});

		} else if (position == mList.size()) {
			lbv.setVisibility(View.GONE);
			if (mList.size() >= 18) {
				setShowAddPic(false);
				imageView.setVisibility(View.GONE);
			} else {
				if(!showAddPic){
					setShowAddPic(true);
				}
				Picasso.with(mContext)
						.load(R.drawable.add_photo)
						.config(Bitmap.Config.RGB_565)
						.centerInside()
						.resize(ScreenUtils.getQuarterWidth(mContext),
								(int) (ScreenUtils.getQuarterWidth(mContext) * Constant.THUMAIL_RATIO))
						.into(imageView);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						fragment.showAddPicDialog();
					}
				});
			}
			btnDelete.setVisibility(View.GONE);
		}
	}
}
