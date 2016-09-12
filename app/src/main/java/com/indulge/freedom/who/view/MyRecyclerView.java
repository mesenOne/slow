package com.indulge.freedom.who.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.indulge.freedom.who.util.Tools;

public class MyRecyclerView extends RecyclerView  {

	/**
	 * 记录当前第一个View
	 */
	private View mCurrentView;

	private OnItemScrollChangeListener mItemScrollChangeListener;

	public void setOnItemScrollChangeListener(
			OnItemScrollChangeListener mItemScrollChangeListener)
	{
		this.mItemScrollChangeListener = mItemScrollChangeListener;
	}

	public interface OnItemScrollChangeListener
	{
		void onChange(View view, int position);
	}

	public MyRecyclerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);

		mCurrentView = getChildAt(0);

		if (mItemScrollChangeListener != null)
		{
			mItemScrollChangeListener.onChange(mCurrentView,
					getChildPosition(mCurrentView));
		}
	}


	@Override
	public void onScrollStateChanged(int arg0)
	{
	}

	/**
	 * 
	 * 滚动时，判断当前第一个View是否发生变化，发生才回调
	 */
	@Override
	public void onScrolled(int arg0, int arg1)
	{
		View newView = getChildAt(0);

		if (mItemScrollChangeListener != null)
		{
			if (newView != null && newView != mCurrentView)
			{
				mCurrentView = newView ;
				mItemScrollChangeListener.onChange(mCurrentView,
						getChildPosition(mCurrentView));

			}
		}

	}


	static public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
		private final int itemCount;
		int mSpace ;

		/**
		 * @param space 传入的值，其单位视为dp
		 */
		public SpaceItemDecoration(Context context,int space,int itemCount) {
			this.mSpace = Tools.PXtoDP(context,space);
			this.itemCount = itemCount;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//			int itemCount = mAdapter.getItemCount();
			int pos = parent.getChildAdapterPosition(view);
//			Log.d(TAG, "itemCount>>" +itemCount + ";Position>>" + pos);


			outRect.top = mSpace;
			outRect.bottom = mSpace;



			if (pos != (itemCount -1)) {
				outRect.right = mSpace;

				if (pos==0){
					outRect.left = mSpace;
				}

			} else {
				outRect.right = mSpace;
				outRect.left = 0;
			}
		}
	}


}
