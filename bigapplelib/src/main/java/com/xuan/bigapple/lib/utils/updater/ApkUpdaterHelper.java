package com.xuan.bigapple.lib.utils.updater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 下载更新APK工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2015-3-6 下午5:17:06 $
 */
public class ApkUpdaterHelper {
	/**
	 * 安装Apk
	 *
	 * @param context
	 * @param fileName
	 *            本地APK地址
	 * @return
	 */
	public static boolean installApk(Context context, String fileName) {
		File apkfile = new File(fileName);
		if (!apkfile.exists()) {
			return false;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
		return true;
	}

}
