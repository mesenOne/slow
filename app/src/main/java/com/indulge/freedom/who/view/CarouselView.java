package com.indulge.freedom.who.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.model.AdSupernatant;
import com.indulge.freedom.who.model.Banner;
import com.indulge.freedom.who.util.ConversionUtil;
import com.indulge.freedom.who.util.ScreenUtils;
import com.squareup.picasso.Picasso;


/**
 * 轮播导航
 * 
 * @author huangyue
 * 
 */
public class CarouselView extends RelativeLayout {

	private final float ASPECT_RATIO = 107.0f / 270;// 导航显示的宽高比
	private final long DURATION = 5000;

	private Context mContext;
	private int mWidth;// 屏幕宽度
	private int mHeight;// 导航的高度
	private Timer mTimer;
	private IItemClickListener mClickListener;
	private AdItemClickListener mAdClickListener;

	private CarouseViewPager mViewPager;// 轮播导航图片viewPager
	private List<View> mShowViews;// 轮播导航图片集合
	private CarouselAdapter mCarouselAdapter;// 轮播导航adapter
	private int mLastItem;
	private int mCurrentItem;

	private boolean isTwo = false;

	private LinearLayout mDots;// 导航点容器

	public CarouselView(Context context) {
		super(context);
		init(context);
	}

	public CarouselView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CarouselView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mWidth = ScreenUtils.getScreenWidth(mContext);
		mHeight = (int) (ASPECT_RATIO * mWidth);
		/* 初始化ViewPager */
		mViewPager = new CarouseViewPager(mContext);
		mShowViews = new ArrayList<View>();
		mCarouselAdapter = new CarouselAdapter(mShowViews);
		mViewPager.setAdapter(mCarouselAdapter);
		mLastItem = 0;

		/* 初始化导航点布局 */
		mDots = new LinearLayout(mContext);
		setParams(0);
	}
	
	public void setParmsType(int type){

		/* 设置轮播图片的参数 */
		LayoutParams pagerParams = new LayoutParams(mWidth,
				mHeight);
		if (mViewPager.getParent() != null && mViewPager != null) {
			removeAllViews();
		}
		addView(mViewPager, pagerParams);

		//广告浮层页小圆点在中间
		if (type == 1) {
			/* 设置导航点的容器参数 */
			LayoutParams dotsParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, mHeight / 10);
			dotsParams.topMargin = (int) ((mHeight / 10f) * 9);
			dotsParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
					RelativeLayout.TRUE);
			addView(mDots, dotsParams);
		}else{
			//banner页小圆点在右边
			/* 设置导航点的容器参数 */
			LayoutParams dotsParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, mHeight / 10);
			dotsParams.topMargin = (int) ((mHeight / 10f) * 9);
			 dotsParams.rightMargin=(int) (mWidth/11.0f);
			dotsParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
			addView(mDots, dotsParams);
		}
	}

	public void setDefaultWidthHeight(int width, int height,int type) {
		mWidth = width;
		mHeight = height;
		setParams(type);
	}

	/**
	 * 初始化图片容器和导航点布局的参数
	 */
	private void setParams(int type) {

		/* 设置轮播图片的参数 */
		LayoutParams pagerParams = new LayoutParams(mWidth,
				mHeight);
		if (mViewPager.getParent() != null && mViewPager != null) {
			removeAllViews();
		}
		addView(mViewPager, pagerParams);

		//广告浮层页小圆点在中间
		if (type == 1) {
			/* 设置导航点的容器参数 */
			LayoutParams dotsParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, mHeight / 10);
			dotsParams.topMargin = (int) ((mHeight / 10f) * 9);
			dotsParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
					RelativeLayout.TRUE);
			addView(mDots, dotsParams);
		}else{
			//banner页小圆点在右边
			/* 设置导航点的容器参数 */
			LayoutParams dotsParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, mHeight / 10);
			dotsParams.topMargin = (int) ((mHeight / 10f) * 9);
			 dotsParams.rightMargin=(int) (mWidth/11.0f);
			dotsParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
			addView(mDots, dotsParams);
		}
	}

	/**
	 * 添加导航图片
	 */
	@SuppressLint("ClickableViewAccessibility")
	public void addAllImage(List<Banner> bannerList) {
		mShowViews.clear();
		mDots.removeAllViews();
		if (bannerList.size() == 2) {
			isTwo = true;
			bannerList.addAll(bannerList);
		}
		for (int i = 0; i < bannerList.size(); i++) {
			LayoutParams imgParams = new LayoutParams(mWidth, mHeight);
			ImageView view = new ImageView(mContext);
			view.setScaleType(ScaleType.CENTER_CROP);
			if (!TextUtils.isEmpty(bannerList.get(i).getsImage())) {
				Picasso.with(mContext).load(bannerList.get(i).getsImage())
						.config(Bitmap.Config.RGB_565).resize(mWidth, mHeight)
						.into(view);
			}
			view.setTag(bannerList.get(i));
			view.setOnClickListener(mItemClickListener);
			view.setLayoutParams(imgParams);
			mShowViews.add(view);
			mCarouselAdapter.notifyDataSetChanged();
			mViewPager.addOnPageChangeListener(mPageChangeListener);
			mViewPager.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP
							|| event.getAction() == MotionEvent.ACTION_CANCEL) {
						startTimer();
					} else {
						stopTimer();
					}
					return false;
				}
			});
			if (isTwo) {
				if (i <= 1) {
					ImageView dot = new ImageView(mContext);
					LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					if (i != 0) {
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_default));
						dotParams.leftMargin = ConversionUtil
								.dp2px(mContext, 8);
					} else {
//						dot.setImageResource(R.drawable.icon_point_fff);
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point));
					}
					mDots.addView(dot, dotParams);
				}
			} else {
				if (bannerList.size() > 1) {
					ImageView dot = new ImageView(mContext);
					LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					if (i != 0) {
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_default));
						dotParams.leftMargin = ConversionUtil
								.dp2px(mContext, 3);
					} else {
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point));
					}
					mDots.addView(dot, dotParams);
				}
			}
		}
		mViewPager.setCurrentItem(mShowViews.size() <= 1 ? 0 : mShowViews
				.size());
	}

	/**
	 * 添加导航图片
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("ClickableViewAccessibility")
	public void addAllAdImage(List<AdSupernatant> bannerList) {
		mShowViews.clear();
		mDots.removeAllViews();
		if (bannerList.size() == 2) {
			isTwo = true;
			bannerList.addAll(bannerList);
		}
		for (int i = 0; i < bannerList.size(); i++) {
			LayoutParams imgParams = new LayoutParams(mWidth, mHeight);

			ImageView view = new ImageView(mContext);
			view.setScaleType(ScaleType.CENTER_CROP);
			if (!TextUtils.isEmpty(bannerList.get(i).getsImg())) {
				Picasso.with(mContext).load(bannerList.get(i).getsImg())
						.config(Bitmap.Config.RGB_565).resize(mWidth, mHeight)
						.into(view);
			}
			view.setTag(bannerList.get(i));
			view.setOnClickListener(mAdItemClickListener);
			view.setLayoutParams(imgParams);
			view.setBackgroundDrawable(getContext().getResources().getDrawable(
					R.drawable.shape_ad_bg));
			// setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_ad_bg_black));
			// Tools.handleImageViewForDestny(mContext,view); // 适配屏幕的imageView
			mShowViews.add(view);
			mCarouselAdapter.notifyDataSetChanged();
			mViewPager.addOnPageChangeListener(mPageChangeListener);
			if (isTwo) {
				if (i <= 1) {
					ImageView dot = new ImageView(mContext);
					LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					if (i != 0) {
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_default));
						dotParams.leftMargin = ConversionUtil
								.dp2px(mContext, 8);
					} else {
//						dot.setImageResource(R.drawable.icon_point_fff);
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point));
					}
					mDots.addView(dot, dotParams);
				}
			} else {
				if (bannerList.size() > 1) {
					ImageView dot = new ImageView(mContext);
					LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					if (i != 0) {
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_default));
						dotParams.leftMargin = ConversionUtil
								.dp2px(mContext, 3);
					} else {
						dot.setImageDrawable(getResources().getDrawable(R.drawable.shape_point));
						//dot.setImageResource(R.drawable.icon_point_fff);
					}
					mDots.addView(dot, dotParams);
				}
			}
		}
		mViewPager.setCurrentItem(mShowViews.size() <= 1 ? 0 : mShowViews
				.size());
	}

	/**
	 * 开始轮播
	 */
	public void start(IItemClickListener clickListener) {
		setIItemClickListener(clickListener);
		startTimer();
	}

	/**
	 * ad开始轮播
	 */
	public void startAd(AdItemClickListener clickListener) {
		setAdItemClickListener(clickListener);
		// startTimer();
	}

	/**
	 * 获取当前图片item值
	 * 
	 * @return
	 */
	public int getmCurrentItem() {
		return mCurrentItem;
	}

	/**
	 * 添加点击图片的监听器
	 * 
	 * @param clickListener
	 */
	public void setIItemClickListener(IItemClickListener clickListener) {
		mClickListener = clickListener;
	}

	/**
	 * 添加点击图片的监听器
	 * 
	 * @param clickListener
	 */
	public void setAdItemClickListener(AdItemClickListener clickListener) {
		mAdClickListener = clickListener;
	}

	/**
	 * 停止滚动
	 */
	public void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	/**
	 * 开始滚动
	 */
	public synchronized void startTimer() {
		if (mShowViews.size() > 1) {
			if (mTimer == null) {
				mTimer = new Timer();
				mTimer.schedule(new TimerTask() {
					public void run() {
						((Activity) mContext).runOnUiThread(new Runnable() {
							public void run() {
								int position = mViewPager.getCurrentItem() + 1;
								if (position == 50 * mShowViews.size() - 1) {
									mViewPager.setCurrentItem(
											mShowViews.size() - 1, false);
								} else {
									mViewPager.setCurrentItem(position);// 设置控件当前项（改变图片）
								}

							}
						});
					}
				}, DURATION, DURATION);
			}
		}
	}

	/**
	 * 如果外层有ScrollView需要设置.
	 * 
	 * @param parentScrollView
	 */
	public void setParentScrollView(ScrollView parentScrollView) {
		this.mViewPager.setParentScrollView(parentScrollView);
	}

	/**
	 * 如果外层有ListView需要设置.
	 * 
	 * @param parentListView
	 */
	public void setParentListView(ListView parentListView) {
		this.mViewPager.setParentListView(parentListView);
	}

	/**
	 * 轮播图片点击事件
	 */
	private OnClickListener mItemClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			if (mClickListener != null) {
				Banner banner = (Banner) view.getTag();
				mClickListener.onClick(view, banner,
						mViewPager.getCurrentItem() % mShowViews.size());
			}
		}
	};

	/**
	 * 浮层广告轮播图片点击事件
	 */
	private OnClickListener mAdItemClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			if (mAdClickListener != null) {
				AdSupernatant banner = (AdSupernatant) view.getTag();
				mAdClickListener.onClick(view, banner,
						mViewPager.getCurrentItem() % mShowViews.size());
			}
		}
	};

	private boolean imageCanclick = true;
	/**
	 * 轮播监听器
	 */
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int currentItem) {
			mCurrentItem = currentItem % mShowViews.size();
			if (isTwo) {
				mCurrentItem = currentItem % 2;
			}
			if (mDots != null) {
				if (mDots.getChildAt(mLastItem) != null) {
					// 取消圆点选中
					((ImageView) mDots.getChildAt(mLastItem))
					.setImageDrawable(getResources().getDrawable(R.drawable.shape_point_default));
				}
				if (mDots.getChildAt(mCurrentItem) != null) {
					// 圆点选中
					((ImageView) mDots.getChildAt(mCurrentItem))
//							.setImageResource(R.drawable.icon_point_fff);
					.setImageDrawable(getResources().getDrawable(R.drawable.shape_point));
				}
			}
			mLastItem = mCurrentItem;
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (positionOffset == 1 || positionOffset == 0) {
				imageCanclick = true;
			} else {
				imageCanclick = false;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// Log.i("HY", "arg0"+arg0);
			// if(arg0==ViewPager.SCROLL_STATE_DRAGGING){
			// isDrag=true;
			// stopTimer();
			// Log.i("HY", "SCROLL_STATE_DRAGGING");
			// }else if(arg0==ViewPager.SCROLL_STATE_IDLE){
			// startTimer();
			// Log.i("HY", "SCROLL_STATE_IDLE");
			// }
		}
	};

	/**
	 * 轮播导航viewPager的Adapter
	 * 
	 * @author huangyue
	 * 
	 */
	class CarouselAdapter extends PagerAdapter {
		private List<View> carouselViews;

		public CarouselAdapter(List<View> carouselViews) {
			this.carouselViews = carouselViews;
		}

		@Override
		public int getCount() {
			if (carouselViews.size() <= 1) {// 一张图片时不用流动
				return carouselViews.size();
			}
			return 50 * carouselViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = carouselViews.get(position % carouselViews.size());
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}

		@Override
		public void finishUpdate(ViewGroup container) {
			if (carouselViews.size() > 1) {
				int position = mViewPager.getCurrentItem();
				if (position == 0) {
					position = carouselViews.size();
					mViewPager.setCurrentItem(position, false);
				} else if (position == 50 * carouselViews.size() - 1) {
					position = carouselViews.size() - 1;
					mViewPager.setCurrentItem(position, false);
				}
			}
		}
	}

	/**
	 * 轮播图片点击监听器
	 * 
	 * @author huangyue
	 * 
	 */
	public interface IItemClickListener {
		void onClick(View v, Banner banner, int position);

	}

	/**
	 * 轮播图片点击监听器
	 * 
	 * @author huangyue
	 * 
	 */
	public interface AdItemClickListener {
		void onClick(View v, AdSupernatant banner, int position);
	}

	private class CarouseViewPager extends ViewPager {
		/** The parent scroll view. */
		private ScrollView parentScrollView;

		/** The parent list view. */
		private ListView parentListView;

		/** The m gesture detector. */
		private GestureDetector mGestureDetector;

		/**
		 * 初始化这个内部的ViewPager.
		 * 
		 * @param context
		 *            the context
		 */
		public CarouseViewPager(Context context) {
			super(context);
			mGestureDetector = new GestureDetector(context,
					new HyScrollDetector());
			setFadingEdgeLength(0);

		}

		/**
		 * 初始化这个内部的ViewPager.
		 * 
		 * @param context
		 *            the context
		 * @param attrs
		 *            the attrs
		 */
		public CarouseViewPager(Context context, AttributeSet attrs) {
			super(context, attrs);
			mGestureDetector = new GestureDetector(context,
					new HyScrollDetector());
			setFadingEdgeLength(0);
		}

		/**
		 * 描述：拦截事件.
		 * 
		 * @param ev
		 *            the ev
		 * @return true, if successful
		 */
		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			boolean ite = super.onInterceptTouchEvent(ev);
			boolean mget = mGestureDetector.onTouchEvent(ev);
			return ite && mget;
			// return
			// super.onInterceptTouchEvent(ev)&&mGestureDetector.onTouchEvent(ev);
		}

		/**
		 * 设置父级的View.
		 * 
		 * @param flag
		 *            父是否滚动开关
		 */
		private void setParentScrollAble(boolean flag) {
			if (parentScrollView != null) {
				parentScrollView.requestDisallowInterceptTouchEvent(!flag);
			}

			if (parentListView != null) {
				parentListView.requestDisallowInterceptTouchEvent(!flag);
			}

		}

		/**
		 * 如果外层有ScrollView需要设置.
		 * 
		 * @param parentScrollView
		 */
		public void setParentScrollView(ScrollView parentScrollView) {
			this.parentScrollView = parentScrollView;
		}

		/**
		 * 如果外层有ListView需要设置.
		 * 
		 * @param parentListView
		 */
		public void setParentListView(ListView parentListView) {
			this.parentListView = parentListView;
		}

		class HyScrollDetector extends SimpleOnGestureListener {

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {

				if (Math.abs(distanceX) >= Math.abs(distanceY)) {
					// 父布局不滑动
					setParentScrollAble(false);
					return true;
				} else {
					setParentScrollAble(true);
				}
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return !imageCanclick;
			}

		}
	}

}