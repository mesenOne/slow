package com.indulge.freedom.who.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;

import com.indulge.freedom.who.AppContext;


/**
 * 打开本地相册选取照片的工具类
 * 
 * @author huhuan
 * 
 */
public class LocalImageHelper {
	private static LocalImageHelper instance;
	final List<LocalFile> checkedItems = new ArrayList<LocalFile>();



	public String getCameraImgPath() {
		return CameraImgPath;
	}

	@SuppressLint("SimpleDateFormat")
	public String setCameraImgPath() {

		// 照片命名
		String CameraImgPath = System.currentTimeMillis() + ".png";
		// 裁剪头像的绝对路径
		return CameraImgPath;
	}

	// 拍照时指定保存图片的路径
	private String CameraImgPath;

	final List<LocalFile> paths = new ArrayList<LocalFile>();

	final Map<String, List<LocalFile>> folders = new HashMap<String, List<LocalFile>>();

	private LocalImageHelper(AppContext context) {
		
	}

	public Map<String, List<LocalFile>> getFolderMap() {
		return folders;
	}

	public static LocalImageHelper getInstance() {
		return instance;
	}

	public static void init(AppContext context) {
		instance = new LocalImageHelper(context);
	}

	public boolean isInited() {
		return paths.size() > 0;
	}

	public List<LocalFile> getCheckedItems() {
		return checkedItems;
	}

	private boolean resultOk;

	public boolean isResultOk() {
		return resultOk;
	}

	public void setResultOk(boolean ok) {
		resultOk = ok;
	}


	public List<LocalFile> getFolder(String folder) {
		return folders.get(folder);
	}

	public void clear() {
		String foloder = AppContext.getAppDir().getPath();
		File savedir = new File(foloder);
		if (savedir.exists()) {
			deleteFile(savedir);
		}
	}

	public void deleteFile(File file) {

		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
		} else {
		}
	}

	public static class LocalFile {
		private String filepath;
		private String absolutePath;

		public LocalFile(String filepath, String absolutePath,
				String originalUri, String thumbnailUri, int orientation) {
			this.filepath = filepath;
			this.absolutePath = absolutePath;
			this.originalUri = originalUri;
			this.thumbnailUri = thumbnailUri;
			this.orientation = orientation;
		}

		public LocalFile() {
		}

		public String getAbsolutePath() {
			return absolutePath;
		}

		public void setAbsolutePath(String absolutePath) {
			this.absolutePath = absolutePath;
		}

		public String getFilepath() {
			return filepath;
		}

		public void setFilepath(String filepath) {
			this.filepath = filepath;
		}

		private String originalUri;// 原图URI
		private String thumbnailUri;// 缩略图URI
		private int orientation;// 图片旋转角度

		public String getThumbnailUri() {
			return thumbnailUri;
		}

		public void setThumbnailUri(String thumbnailUri) {
			this.thumbnailUri = thumbnailUri;
		}

		public void deleteSelf() {
			File file = new File(getAbsolutePath());
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}

		public String getOriginalUri() {
			return originalUri;
		}

		public void setOriginalUri(String originalUri) {
			this.originalUri = originalUri;
		}

		public int getOrientation() {
			return orientation;
		}

		public void setOrientation(int exifOrientation) {
			orientation = exifOrientation;
		}

		@Override
		public String toString() {
			return "LocalFile [filepath=" + filepath + ", absolutePath="
					+ absolutePath + ", originalUri=" + originalUri
					+ ", thumbnailUri=" + thumbnailUri + ", orientation="
					+ orientation + "]";
		}

	}
}
