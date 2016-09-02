package com.indulge.freedom.who.interf;

/**
 * 下载监听器
 * 
 * @author huangyue
 * 
 */
public interface DownloadApkListener {
	void load(int progress);
	
	void pause();

	void fail();
}
