package com.xuan.bigapple.lib.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.xuan.bigapple.lib.cache.impl.BitmapMemoryCache;
import com.xuan.bigapple.lib.cache.impl.ObjectMemoryCache;

/**
 * 作为一个缓存工厂存在<br>
 * 做了多线程安全的同步。
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-9-17 下午8:46:35 $
 */
public abstract class BPMemCacheManager {
	/**对象内存缓存*/
	private static Cache<String, Object> mObjCache;
	/**图片内存缓存*/
	private static Cache<String, Bitmap> mBitmapCache;

	public static void initObjectCache(int maxCount){
		closeObjCache();
		mObjCache = new ObjectMemoryCache(maxCount);
	}

	public static void initObjectCache(){
		closeObjCache();
		mObjCache = new ObjectMemoryCache(50);
	}

	public static void initBitmapCache(Context context, float maxPercent){
		closeBitmapCache();
		int maxSize = (int) (maxPercent * getMemoryClass(context));
		mBitmapCache = new BitmapMemoryCache(maxSize);
	}

	public static void initBitmapCache(int maxSize){
		closeBitmapCache();
		mBitmapCache = new BitmapMemoryCache(maxSize);
	}

	public static void initBitmapCache(){
		closeBitmapCache();
		mBitmapCache = new BitmapMemoryCache(1024 * 1024 * 20);
	}

	/**
	 * 默认CacheObject对象缓存，可放20单位
	 * 
	 * @return
	 */
	public synchronized Cache<String, Object> getObjCache() {
		if (null == mObjCache) {
			mObjCache = new ObjectMemoryCache(50);
		}

		return mObjCache;
	}

	/**
	 * 默认BitMap对象缓存，可放3M容量
	 * 
	 * @return
	 */
	public synchronized static Cache<String, Bitmap> getBitmapCache() {
		if (null == mBitmapCache) {
			mBitmapCache = new BitmapMemoryCache(1024 * 1024 * 20);
		}

		return mBitmapCache;
	}

	/**
	 * 关闭之后，再get的时候会新初始化缓存
	 */
	public synchronized static void closeObjCache() {
		if (null != mObjCache) {
			mObjCache.removeAll();
			mObjCache = null;
		}
	}

	/**
	 * 关闭之后，再get的时候会新初始化缓存
	 */
	public synchronized static void closeBitmapCache() {
		if (null != mBitmapCache) {
			mBitmapCache.removeAll();
			mBitmapCache = null;
		}
	}

	/**
	 * 同时关掉了对象缓存和bitmap缓存
	 */
	public synchronized static void closeAll() {
		closeObjCache();
		closeBitmapCache();
	}

	private static int getMemoryClass(Context context) {
		return ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
	}

}
