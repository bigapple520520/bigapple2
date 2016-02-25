package com.xuan.bigapple.lib.http;

/**
 * HTT访问通用接口
 * 
 * @author xuan
 */
public interface BPHttpClient {
	/**
	 * POST请求，Json放到请求体里面
	 * 
	 * @param bpRequest
	 * @return
	 */
	BPResponse postJson(BPRequest bpRequest);

	/**
	 * POST请求，普通参数的方式提交
	 * 
	 * @param bpRequest
	 * @return
	 */
	BPResponse post(BPRequest bpRequest);

	/**
	 * GET请求
	 * 
	 * @param bpRequest
	 * @return
	 */
	BPResponse get(BPRequest bpRequest);

	/**
	 * 下载，用的是GET请求
	 *
	 * @param bpRequest
	 * @return
	 */
	BPResponse getDowload(BPRequest bpRequest);

	/**
	 * 下载，用的是POST请求
	 *
	 * @param bpRequest
	 * @return
	 */
	BPResponse postDowload(BPRequest bpRequest);

	/**
	 * 模拟表单上传文件
	 *
	 * @param bpRequest
	 * @return
	 */
	BPResponse upload(BPRequest bpRequest);

}
