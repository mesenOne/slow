package com.indulge.freedom.who.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.adapter.ShowPhotoGridViewAdapter;
import com.indulge.freedom.who.util.ImageUtils;
import com.indulge.freedom.who.util.LocalImageHelper;
import com.indulge.freedom.who.util.ToastUtil;
import com.indulge.freedom.who.view.photoview.PhotoView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.indulge.freedom.who.ui.activity.publishActivity.listFiles;

/**
 * 全屏预览图片的DialogFragment
 * 
 * @author huhuan
 * 
 */
public class FullScreenDlgFragment extends DialogFragment implements
		View.OnClickListener {


	private int mClickItem;// 对应显示ViewPager子项的位置
	private List<LocalImageHelper.LocalFile> mListImgUrls;
	private ViewPager mViewPager;
	// private Integer[] mImgIds;//本地图片资源ID
	private Dialog mDialog;
	public static final String TAG_NAME = FullScreenDlgFragment.class.getName();
	private Context mContext;

	private int currentItem;
	private TextView tvCount;
	private Button btnDelete;
	private Button btnEdit;
	private Button btnSetFirst;
	private List<PhotoView> listImgs;
	private MyPagerAdapter pageAdapter;

	private ShowPhotoGridViewAdapter showPhotoAdapter;
	
	private static FullScreenDlgFragment fullScrDlgFragment;
	
	private boolean hasEdit=false;

	// 即学即用的工厂方法
	public static FullScreenDlgFragment newInstance(Context context,
													List<LocalImageHelper.LocalFile> mListImgUrls, int clickItem,
													ShowPhotoGridViewAdapter adapter) {
		Bundle args = new Bundle();
		FullScreenDlgFragment fragment = new FullScreenDlgFragment();
		fragment.setArguments(args);
		fragment.mContext = context;
		fragment.mListImgUrls = mListImgUrls;
		fragment.mClickItem = clickItem;
		fragment.showPhotoAdapter = adapter;
		fullScrDlgFragment=fragment;
		return fragment;
	}

	// 由ViewPager来响应点击
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit:
//			listFiles.addAll(mListImgUrls);
//			Intent intent = new Intent(mContext, EditActivity.class);
//			intent.putExtra("currentItem", mViewPager.getCurrentItem());
//			startActivityForResult(intent,
//					ImageUtils.REQUEST_CODE_GETIMAGE_BYTUYA_FROME_FULL_BORSWER);
			break;
		case R.id.btn_delete:
			if (listImgs.size() == 1) {
				this.dismiss();
			}
			ToastUtil.show(mContext, "删除成功");
			listImgs.remove(currentItem);
			mListImgUrls.remove(currentItem);
			pageAdapter.notifyDataSetChanged();
			showPhotoAdapter.notifyDataSetChanged();
			tvCount.setText(currentItem + 1 + "/" + mListImgUrls.size());
			break;

		case R.id.btn_set_first:
			LocalImageHelper.LocalFile temp = mListImgUrls.get(currentItem);
			mListImgUrls.remove(currentItem);
			mListImgUrls.add(0, temp);
			pageAdapter.notifyDataSetChanged();
			showPhotoAdapter.notifyDataSetChanged();
			ToastUtil.show(mContext, "设为封面成功");
			this.dismiss();
			break;
		default:
			this.dismiss();
			break;
		}

	}
	public static FullScreenDlgFragment getInstane(){
		return fullScrDlgFragment;
	}
	
	public void onResult(){
		if(fullScrDlgFragment!=null){
			mListImgUrls.clear();
			showPhotoAdapter.notifyDataSetChanged();
			pageAdapter.notifyDataSetChanged();
			hasEdit=true;
		}
	}
	@Override
	public void onResume() {
		if(fullScrDlgFragment!=null&&hasEdit){
			hasEdit=false;
			dismiss();
		}
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.MATCH_PARENT;
			dialog.getWindow().setLayout(width, height);
		}

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// 设置Dialog样式
		// setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog_fill);
		mDialog = new Dialog(mContext);
		// 去标题栏
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		mDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		return mDialog;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@SuppressLint("InflateParams")
	private void initView() {
		// 将Dialog设置全屏！！！
		// setDlgParams();
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_fragment_review_photos, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_preview_photo);
		tvCount = (TextView) view.findViewById(R.id.tv_current_of_all);
		btnDelete = (Button) view.findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(this);
		btnSetFirst = (Button) view.findViewById(R.id.btn_set_first);
		btnSetFirst.setOnClickListener(this);
		btnEdit = (Button) view.findViewById(R.id.btn_edit);
		btnEdit.setOnClickListener(this);
		mViewPager.setBackgroundColor(0xFF000000);
		initViewPager();
		mDialog.setContentView(view);
	}

	private void initViewPager() {
		if (mListImgUrls != null && mListImgUrls.size() > 0) {
			listImgs = new ArrayList<PhotoView>();
			for (int i = 0; i < mListImgUrls.size(); i++) {
				PhotoView iv = new PhotoView(mContext);
				iv.enable();
				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				iv.setLayoutParams(params);
				iv.setAdjustViewBounds(true);
				iv.setScaleType(ScaleType.FIT_CENTER);
				Picasso.with(mContext)
						.load(mListImgUrls.get(i).getOriginalUri()).config(Bitmap.Config.RGB_565).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).config(Bitmap.Config.RGB_565).into(iv);
				listImgs.add(iv);
				iv.setOnClickListener(this);
			}
			if (listImgs.size() > 0) {
				pageAdapter = new MyPagerAdapter(listImgs);
				mViewPager.setAdapter(pageAdapter);
				mViewPager.setCurrentItem(mClickItem);
				currentItem = mClickItem;
				tvCount.setText(mClickItem + 1 + "/" + mListImgUrls.size());
			}
		}
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentItem = arg0;
				tvCount.setText(currentItem + 1 + "/" + mListImgUrls.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	public class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		private List<PhotoView> mList;

		public MyPagerAdapter(List<PhotoView> mList) {
			this.mList = mList;
		}

		@Override
		public int getCount() {

			if (mList != null && mList.size() > 0) {
				return mList.size();
			} else {
				return 0;
			}
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mList.get(position));
			return mList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
}