package com.xuan.bigapple.lib.cache;

import android.content.Context;

import com.xuan.bigapple.lib.cache.core.BPACache;

import java.io.File;

/**
 * 磁盘缓存工具
 * 
 * @author xuan
 */
public class BPDiskCacheManager {
	private static BPACache instance;

	public static void init(String cacheDir, int maxSize, int maxCount) {
		File cacheDirFile = new File(cacheDir);
		if(!cacheDirFile.exists()){
			cacheDirFile.mkdirs();
		}

		instance = BPACache.get(cacheDirFile, maxSize, maxCount);
	}

	public static void init(String cacheDir, int maxSize) {
		init(cacheDir, maxSize, Integer.MAX_VALUE);
	}

	public static void init(String cacheDir) {
		init(cacheDir, 1024 * 1024 * 50, Integer.MAX_VALUE);
	}

	public static void init(Context context, int maxSize, int maxCount) {
		instance = BPACache.get(context, maxSize, maxCount);
	}

	public static void init(Context context, int maxSize) {
		instance = BPACache.get(context, maxSize, Integer.MAX_VALUE);
	}

	public static void init(Context context) {
		instance = BPACache.get(context, 1024 * 1024 * 50, Integer.MAX_VALUE);
	}

	public static BPACache getInstance() {
		if (null == instance) {
			throw new NullPointerException(
					"Call BPACacheManager.init first!");
		}

		return instance;
	}

}
