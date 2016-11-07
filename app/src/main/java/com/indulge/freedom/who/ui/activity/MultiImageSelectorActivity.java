package com.indulge.freedom.who.ui.activity;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.indulge.freedom.who.R;
import com.indulge.freedom.who.ui.fragment.MultiImageSelectorFragment;
import com.indulge.freedom.who.util.ImageUtils;
import com.indulge.freedom.who.util.LocalImageHelper;
import com.indulge.freedom.who.util.ShowLoadingDialogUtil;


/**
 * 多图选择 Created by Nereo on 2015/4/7. Updated by nereo on 2016/1/19.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements
		MultiImageSelectorFragment.Callback {

	/** 最大图片选择次数，int类型，默认9 */
	public static final String EXTRA_SELECT_COUNT = "max_select_count";
	/** 图片选择模式，默认多选 */
	public static final String EXTRA_SELECT_MODE = "select_count_mode";
	/** 是否显示相机，默认显示 */
	public static final String EXTRA_SHOW_CAMERA = "show_camera";
	/** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合 */
	public static final String EXTRA_RESULT = "select_result";
	/** 默认选择集 */
	public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

	/** 单选 */
	public static final int MODE_SINGLE = 0;
	/** 多选 */
	public static final int MODE_MULTI = 1;

	private ArrayList<String> resultList = new ArrayList<String>();
	private ArrayList<String> mDefaultList = new ArrayList<String>();
	
	private Button mSubmitButton;
	private int mDefaultCount;
	private int scaleFinish = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_default);

		Intent intent = getIntent();
		mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
		int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
		boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);

		Bundle bundle = new Bundle();
		bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT,
				mDefaultCount);
		bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
		bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
		bundle.putStringArrayList(
				MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST,
				resultList);

		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.image_grid,
						Fragment.instantiate(this,
								MultiImageSelectorFragment.class.getName(),
								bundle)).commit();

		// 返回按钮
		findViewById(R.id.btn_back).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						setResult(RESULT_CANCELED);
						finish();
					}
				});

		// 完成按钮
		mSubmitButton = (Button) findViewById(R.id.commit);
		if (resultList == null || resultList.size() <= 0) {
			mSubmitButton.setText(R.string.action_done);
			mSubmitButton.setEnabled(false);
		} else {
			updateDoneText();
			mSubmitButton.setEnabled(true);
		}
		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (resultList != null && resultList.size() > 0) {
					// 返回已选择的图片数据
					final Intent data = new Intent();
					ShowLoadingDialogUtil.showLoadingDialog(
							MultiImageSelectorActivity.this, "请稍等", false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							resultList.removeAll(mDefaultList);
							for (int i = 0; i < resultList.size(); i++) {
								File file = null;
								Bitmap bitmap = BitmapFactory
										.decodeFile(resultList.get(i));
								String filename = LocalImageHelper
										.getInstance().setCameraImgPath();
								Bitmap resizeBm = ImageUtils.comp(
										getApplicationContext(), bitmap, 200,
										100, ImageUtils
												.getBitmapDegree(resultList
														.get(i)));
								file = ImageUtils.saveBitmap2file(resizeBm,
										filename, 65);
								Uri uri = Uri.fromFile(file);
								LocalImageHelper.LocalFile localFile = new LocalImageHelper.LocalFile();
								localFile.setFilepath(filename);
								localFile.setAbsolutePath(file
										.getAbsolutePath());
								localFile.setOriginalUri(uri.toString());
								localFile.setThumbnailUri(uri.toString());
								publishActivity.listFiles.add(localFile);
								bitmap.recycle();
								scaleFinish++;
							}
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// 初始化完毕后，显示文件夹列表
									if (scaleFinish == resultList.size()) {
										data.putStringArrayListExtra(EXTRA_RESULT, resultList);
										setResult(RESULT_OK, data);
										ShowLoadingDialogUtil.closeLoadingDialog();
										finish();
									}
								}
							});
						}
					}).start();

				}
			}
		});
	}

	private void updateDoneText() {
		mSubmitButton.setText(String.format("%s(%d/%d)",
				getString(R.string.action_done), resultList.size(),
				mDefaultCount));
	}

	@Override
	public void onSingleImageSelected(String path) {
	}

	@Override
	public void onImageSelected(String path, String uri) {
		if (!resultList.contains(path)) {
			resultList.add(path);
		}
		Log.i("H2", resultList.toString()+"--");
		// 有图片之后，改变按钮状态
		if (resultList.size() > 0) {
			updateDoneText();
			Log.i("H2", mSubmitButton.isEnabled()+"--");
			if (!mSubmitButton.isEnabled()) {
				mSubmitButton.setEnabled(true);
			}
		}
	}

	@Override
	public void onImageUnselected(String path, String uri) {
		Log.i("H2", "resultList.contains(path)"+resultList.contains(path));
		if (resultList.contains(path)) {
			resultList.remove(path);
		}
		updateDoneText();
		// 当为选择图片时候的状态
		if (resultList.size() == 0) {
			mSubmitButton.setText(R.string.action_done);
			mSubmitButton.setEnabled(false);
		}
	}

	@Override
	public void onCameraShot(File imageFile) {
	}
}
