package com.xuan.bigapple.lib.http;

import android.util.Log;

import com.xuan.bigapple.lib.http.callback.BPHttpDownloadListener;

import java.io.File;
import java.util.Map;

/**
 * HTTP工具类，后期可以有多种实现方式
 * 
 * @author xuan
 */
public abstract class BPHttpUtils {
	private static final String TAG = "Bigapple.BPHttpUtils";
	public static final boolean DEBUG = false;

	/**
	 * POST请求,json提交
	 *
	 * @param request
	 * @return
	 */
	public static BPResponse postJson(BPRequest request) {
		BPHttpClient client = BPHttpFactroy.getURLConnectionHttpClient();
		printLog(request);
		return client.postJson(request);
	}

	/**
	 * POST请求,json提交
	 *
	 * @param url
	 * @param bodyJson
	 * @return
	 */
	public static BPResponse postJson(String url, String bodyJson) {
		BPRequest request = new BPRequest();
		request.setUrl(url);
		request.putBodyJson(bodyJson);
		return postJson(request);
	}

	/**
	 * POST请求,普通参数方式提交
	 *
	 * @param request
	 * @return
	 */
	public static BPResponse post(BPRequest request) {
		BPHttpClient client = BPHttpFactroy.getURLConnectionHttpClient();
		printLog(request);
		return client.post(request);
	}

	/**
	 * POST请求,普通参数方式提交
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static BPResponse post(String url, Map<String, String> paramsMap) {
		BPRequest request = new BPRequest();
		request.setUrl(url);

		if(null != paramsMap){
			for(Map.Entry<String,String> entry : paramsMap.entrySet()){
				request.putParam(entry.getKey(), entry.getValue());
			}
		}

		return post(request);
	}

	/**
	 * GET请求
	 *
	 * @param request
	 * @return
	 */
	public static BPResponse get(BPRequest request) {
		BPHttpClient client = BPHttpFactroy.getURLConnectionHttpClient();
		printLog(request);
		return client.get(request);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static BPResponse get(String url, Map<String, String> paramsMap) {
		BPRequest request = new BPRequest();
		request.setUrl(url);

		if(null != paramsMap){
			for(Map.Entry<String,String> entry : paramsMap.entrySet()){
				request.putParam(entry.getKey(), entry.getValue());
			}
		}

		return get(request);
	}

	/**
	 * 上传文件
	 *
	 * @param request
	 * @return
	 */
	public static BPResponse upload(BPRequest request){
		BPHttpClient client = BPHttpFactroy.getURLConnectionHttpClient();
		printLog(request);
		return client.upload(request);
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 * @param fileMap
	 * @param paramMap
	 * @return
	 */
	public static BPResponse upload(String url,
			Map<String, File> fileMap, Map<String, String> paramMap) {
		BPRequest request = new BPRequest();
		request.setUrl(url);
		if(null != fileMap){
			for(Map.Entry<String, File> entry : fileMap.entrySet()){
				request.putFile(entry.getKey(), entry.getValue());
			}
		}
		if(null != paramMap){
			for(Map.Entry<String, String> entry : paramMap.entrySet()){
				request.putParam(entry.getKey(), entry.getValue());
			}
		}

		return upload(request);
	}

	/**
	 * GET的方式下载
	 *
	 * @param bpRequest
	 * @return
	 */
	public static BPResponse getDowload(BPRequest bpRequest){
		BPHttpClient client = BPHttpFactroy.getURLConnectionHttpClient();
		printLog(bpRequest);
		return client.getDowload(bpRequest);
	}

	/**
	 * GET的方式下载
	 *
	 * @param url
	 * @param paramsMap
	 * @param saveFileName
	 * @param downloadListener
	 * @return
	 * @throws Exception
	 */
	public static BPResponse getDowload(String url,
			Map<String, String> paramsMap, String saveFileName,
			BPHttpDownloadListener downloadListener) throws Exception {

		BPRequest request = new BPRequest();
		request.setUrl(url);
		request.setDownloadFileName(saveFileName);
		request.setDownloadListener(downloadListener);
		if(null != paramsMap){
			for(Map.Entry<String,String> entry : paramsMap.entrySet()){
				request.putParam(entry.getKey(), entry.getValue());
			}
		}

		printLog(request);
		return getDowload(request);
	}

	/**
	 * POST的方式下载
	 *
	 * @param bpRequest
	 * @return
	 */
	public static BPResponse postDowload(BPRequest bpRequest){
		BPHttpClient client = BPHttpFactroy.getURLConnectionHttpClient();
		printLog(bpRequest);
		return client.postDowload(bpRequest);
	}

	/**
	 * POST的方式下载
	 *
	 * @param url
	 * @param paramsMap
	 * @param saveFileName
	 * @param downloadListener
	 * @return
	 * @throws Exception
	 */
	public static BPResponse postDowload(String url,
										Map<String, String> paramsMap, String saveFileName,
										BPHttpDownloadListener downloadListener) throws Exception {

		BPRequest request = new BPRequest();
		request.setUrl(url);
		request.setDownloadFileName(saveFileName);
		request.setDownloadListener(downloadListener);
		if(null != paramsMap){
			for(Map.Entry<String,String> entry : paramsMap.entrySet()){
				request.putParam(entry.getKey(), entry.getValue());
			}
		}

		printLog(request);
		return postDowload(request);
	}

	private static void printLog(BPRequest request){
		if(DEBUG){
			Log.d(TAG, request.toString());
		}
	}

}
