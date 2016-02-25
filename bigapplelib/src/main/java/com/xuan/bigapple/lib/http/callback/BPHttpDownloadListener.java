package com.xuan.bigapple.lib.http.callback;

/**
 * 下载监听
 * 
 * @author xuan
 */
public interface BPHttpDownloadListener {
	/**
	 * 处理结果时回调
	 * 
	 * @param count
	 *            总量字节
	 * @param current
	 *            当前处理字节
	 * @param isFinish
	 *            是否处理完成
	 */
	public void callBack(long count, long current, boolean isFinish);
}
