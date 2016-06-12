package com.xuan.bigapple.lib.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.xuan.bigapple.lib.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 截屏工具
 * 
 * @author xuan
 */
public abstract class ScreenshotUtils {
	private static final String TAG = "BP.ScreenshotUtils";

	/**
	 * 保存View到图片
	 *
	 * @param view
	 * @param saveFileName
	 * @param config
	 * @return
	 */
	public static Bitmap shotView(View view, String saveFileName, Config config) {
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache();

		// 保存到指定文件
		if (!Validators.isEmpty(saveFileName)) {
			File file = new File(saveFileName);
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				bitmap.compress(config.getFormt(), config.getQuality(), fos);
				fos.flush();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(fos);
			}
		}

		return bitmap;
	}

	/**
	 * View保存成图片
	 * 
	 * @param view
	 * @param saveFileName
	 *            截屏后文件保存路劲
	 * @return
	 */
	public static Bitmap shotView(View view, String saveFileName) {
		return shotView(view, saveFileName, new Config());
	}

	/**
	 * 截屏
	 * 
	 * @param activity
	 * @param saveFileName
	 *            截屏后文件保存路劲
	 * @return
	 */
	public static Bitmap shotScreen(Activity activity, String saveFileName) {
		View decorview = activity.getWindow().getDecorView();
		return shotView(decorview, saveFileName);
	}

	public static class Config{
		private int quality = 70;
		private Bitmap.CompressFormat formt = Bitmap.CompressFormat.PNG;

		public Bitmap.CompressFormat getFormt() {
			return formt;
		}

		public void setFormt(Bitmap.CompressFormat formt) {
			this.formt = formt;
		}

		public int getQuality() {
			return quality;
		}

		public void setQuality(int quality) {
			this.quality = quality;
		}
	}

}
