package com.xuan.bigapple.demo.http;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuan.bigapple.R;
import com.xuan.bigapple.lib.http.BPHttpUtils;
import com.xuan.bigapple.lib.http.BPResponse;
import com.xuan.bigapple.lib.http.callback.BPHttpDownloadListener;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.ioc.app.BPActivity;
import com.xuan.bigapple.lib.utils.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Http模块测试
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2014-1-2 下午6:52:41 $
 */
public class HttpDemoActivity extends BPActivity {
	@InjectView(R.id.getBtn)
	private Button getBtn;

	@InjectView(R.id.postBtn)
	private Button postBtn;

	@InjectView(R.id.getDownloadBtn)
	private Button getDownloadBtn;

	@InjectView(R.id.postDownloadBtn)
	private Button postDownloadBtn;

	@InjectView(R.id.uploadBtn)
	private Button uploadBtn;

	@InjectView(R.id.textView)
	private TextView textView;

	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_http_main);
		initGetBtn();
		initPostBtn();
		initGetDownload();
		initPostDownload();
		initUpload();
	}

	private void initGetBtn() {
		//get请求
		getBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							HashMap<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("appId", "00000000000000000000000000000001");
							paramMap.put("appSecret", "00000000000000000000000000000001");
							final BPResponse bpResponse = BPHttpUtils.get(
									"http://api.jopool.net/jppush/rest/token.htm?osType=ANDROID", paramMap);
							handler.post(new Runnable() {
								@Override
								public void run() {
									textView.setText(bpResponse.toString());
								}
							});
						} catch (Exception e) {
							ToastUtils.displayTextShort(
									e.getMessage());
						}
					}
				}).start();
			}
		});
	}

	private void initPostBtn() {
		postBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							HashMap<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("appId","00000000000000000000000000000001");
							paramMap.put("appSecret","00000000000000000000000000000001");
							paramMap.put("osType","ANDROID");
							final BPResponse bpResponse = BPHttpUtils.post(
									"http://api.jopool.net/jppush/rest/token.htm", paramMap);
							handler.post(new Runnable() {
								@Override
								public void run() {
									textView.setText(bpResponse.toString());
								}
							});
						} catch (Exception e) {
							ToastUtils.displayTextShort(
									e.getMessage());
						}
					}
				}).start();
			}
		});
	}

	private void initGetDownload(){
		getDownloadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String url = "http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg";
							HashMap<String, String> paramMap = new HashMap<String, String>();
							String savePath = Environment.getExternalStorageDirectory().getPath() + "/bigappledwonload/test.jpg";
							BPHttpDownloadListener downloadListener = new BPHttpDownloadListener() {
								@Override
								public void callBack(final long count, final long current, boolean isFinish) {
									handler.post(new Runnable() {
										@Override
										public void run() {
											textView.setText("下载进度:" + current +"/"+count);
										}
									});
								}
							};
							final BPResponse bpResponse = BPHttpUtils.getDowload(url, paramMap, savePath, downloadListener);
							handler.post(new Runnable() {
								@Override
								public void run() {
									textView.setText("图片下载地址:" + bpResponse.getResultFile().getPath());
								}
							});
						} catch (Exception e) {
							ToastUtils.displayTextShort(
									e.getMessage());
						}
					}
				}).start();

			}
		});
	}

	private void initPostDownload(){
		postDownloadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String url = "http://api.jopool.net:80/jppush/jppush_files/image/20160225/20LM3BuI.png";
							HashMap<String, String> paramMap = new HashMap<String, String>();
							String savePath = Environment.getExternalStorageDirectory().getPath() + "/bigappledwonload/test.jpg";
							BPHttpDownloadListener downloadListener = new BPHttpDownloadListener() {
								@Override
								public void callBack(final long count, final long current, boolean isFinish) {
									handler.post(new Runnable() {
										@Override
										public void run() {
											textView.setText("下载进度:" + current +"/"+count);
										}
									});
								}
							};
							final BPResponse bpResponse = BPHttpUtils.postDowload(url, paramMap, savePath, downloadListener);
							handler.post(new Runnable() {
								@Override
								public void run() {
									textView.setText("图片下载地址:" + bpResponse.getResultFile().getPath());
								}
							});
						} catch (Exception e) {
							ToastUtils.displayTextShort(
									e.getMessage());
						}
					}
				}).start();

			}
		});
	}

	private void initUpload(){
		uploadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fileStr = Environment.getExternalStorageDirectory().getPath() + "/xuan/1.png";
				File file = new File(fileStr);

				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("type", "image");

				Map<String, File> fileMap = new HashMap<String, File>();
				fileMap.put("file", file);

				final BPResponse response = BPHttpUtils.upload("http://api.jopool.net/jppush/files/upload.htm", fileMap, paramMap);

				handler.post(new Runnable() {
					@Override
					public void run() {
						textView.setText("图片访问地址:" + response.getResultStr());
					}
				});
				//BPResponse response = BPHttpUtils.upload("http://xuanner.com/upload.php", fileMap, paramMap);
			}
		});
	}

}
