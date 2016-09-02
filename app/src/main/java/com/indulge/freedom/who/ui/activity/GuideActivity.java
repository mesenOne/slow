package com.indulge.freedom.who.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.indulge.freedom.who.AppManager;
import com.indulge.freedom.who.R;
import com.indulge.freedom.who.adapter.GuideAdapter;
import com.indulge.freedom.who.config.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;


public class GuideActivity extends Activity implements OnClickListener {

	@Bind(R.id.viewPager)
	ViewPager mGuidePager;
	@Bind(R.id.lLayout_guide_dots)
	LinearLayout mDots;
	private PagerAdapter mAdapter;
	private List<View> mViews;
	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		ButterKnife.bind(this);
		getData();
	}

	@SuppressLint("InflateParams")
	private void getData() {
		mViews = new ArrayList<View>();
		mInflater = LayoutInflater.from(this);
		// 第一个引导页
		View guideView1 = mInflater.inflate(R.layout.item_guide, null, false);
		ImageView guideImg1 = (ImageView) guideView1
				.findViewById(R.id.img_guide);
		guideImg1.setImageResource(R.drawable.guide_page1);
		Button skip = (Button) guideView1.findViewById(R.id.btn_guide_skip);
		skip.setVisibility(View.VISIBLE);
		skip.setOnClickListener(this);

		// 第二个引导页
		View guideView2 = mInflater.inflate(R.layout.item_guide, null, false);
		ImageView guideImg2 = (ImageView) guideView2
				.findViewById(R.id.img_guide);
		guideImg2.setImageResource(R.drawable.guide_page2);

		// 第三个引导页
		View guideView3 = mInflater.inflate(R.layout.item_guide, null, false);
		ImageView guideImg3 = (ImageView) guideView3
				.findViewById(R.id.img_guide);
		guideImg3.setImageResource(R.drawable.guide_page3);
		
		Button experience = (Button) guideView3
				.findViewById(R.id.btn_experience);
		experience.setVisibility(View.VISIBLE);
		experience.setOnClickListener(this);
		mViews.add(guideView1);
		mViews.add(guideView2);
		mViews.add(guideView3);
		mAdapter = new GuideAdapter(mViews);
		mGuidePager.setAdapter(mAdapter);
		mGuidePager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int current) {
				for (int i = 0; i < mViews.size(); i++) {
					((ImageView) mDots.getChildAt(i))
							.setImageResource(R.drawable.icon_point);
					if (i == current) {
						((ImageView) mDots.getChildAt(i))
								.setImageResource(R.drawable.icon_current_point);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	public void onClick(View v) {

		String username = (String) BmobUser.getObjectByKey("username");
		if(username!=null){
			Intent intent = new Intent();
			intent.setClass(this, UsedCarActivity.class);
			intent.putExtra(Constant.ACTION, Constant.CAR_PECCANCY);
			startActivity(intent);
		}else{
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
		}


		finish();
	}

	@Override
	protected void onDestroy() {
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
		ButterKnife.unbind(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

	}

}
