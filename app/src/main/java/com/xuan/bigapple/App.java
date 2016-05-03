package com.xuan.bigapple;

import android.app.Application;
import android.os.Environment;

import com.xuan.bigapple.lib.Bigapple;
import com.xuan.bigapple.lib.bitmap.BPBitmapLoader;
import com.xuan.bigapple.lib.db.DBHelper;
import com.xuan.bigapple.lib.utils.log.LogUtils;

/**
 * 程序入口处
 * 
 * @author xuan
 */
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		Bigapple.init(this);

		//指定图片磁盘缓存
		BPBitmapLoader.getInstance().getDefaultBitmapGlobalConfig().setDiskCachePath(Environment.getExternalStorageDirectory().getPath()+"/xuan/bigappleBitmapCacheTest");

		// 设置日志TAG
		LogUtils.TAG = "BigappleDemo";
		DBHelper.init(1, "bigapple", this);
	}

}
