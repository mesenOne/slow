package com.indulge.freedom.who.download;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.util.Log;

import com.indulge.freedom.who.db.DBService;
import com.indulge.freedom.who.interf.DownloadApkListener;


/**
 * 下载apk的线程
 * 
 * @author huangyue
 * 
 */
public class DownloadTask implements Runnable {
	private static final String TAG = "下载线程";
	private Integer mThreadId;
	private DBService mDbService;
	private RandomAccessFile mAccessFile;
	private String mDownloadUrl = "";
	private long mMark;
	private long mEnd;
	private DownloadApkListener mApkListener;

	private boolean isPause;

	@SuppressLint("UseSparseArrays")
	public DownloadTask(Integer threadId, RandomAccessFile accessFile,
			String downloadUrl, long mark, long end, DBService dbService,
			DownloadApkListener listener) {
		mThreadId = threadId;
		mDownloadUrl = downloadUrl;
		mAccessFile = accessFile;
		mMark = mark;
		mEnd = end;
		mDbService = dbService;
		mApkListener = listener;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(mDownloadUrl);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setConnectTimeout(10000); // 设置连接超时
			http.setRequestProperty("Range", "bytes=" + mMark + "-" + mEnd);// 设置获取实体数据的范围
			http.setRequestProperty("Connection", "Keep-Alive"); // 设置为持久连接
			http.setReadTimeout(10000);
			http.connect();
			int fileSize = http.getContentLength();
			Log.i(TAG, "mMark=" + mMark + "；传入的结尾=" + mEnd + "；大小："
					+ (mEnd - mMark) + "；网络获取的大小=" + fileSize);
			// 得到输入流
			InputStream inStream = http.getInputStream();
			byte[] buffer = new byte[1024 * 8];
			int offset = 0;
			// 定位到pos位置
			mAccessFile.seek(mMark);
			while ((offset = inStream.read(buffer)) != -1) {
				if (!isPause) {
					// 写入文件
					mAccessFile.write(buffer, 0, offset);
					mMark += offset;
					mDbService.saveMark(mDownloadUrl, mThreadId, mMark);
					int progress = (int) ((float) mMark / (float) mEnd * 100);
					mApkListener.load(progress);
				}else {
					mApkListener.pause();
				}
			}
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			mApkListener.fail();
		}
	}

	public void setPause() {
		isPause = true;
	}
}
