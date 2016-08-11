package com.xuan.bigapple.lib.http.urlconnect;

import android.util.Log;

import com.xuan.bigapple.lib.http.BPHttpClient;
import com.xuan.bigapple.lib.http.BPRequest;
import com.xuan.bigapple.lib.http.BPResponse;
import com.xuan.bigapple.lib.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 用UrlConnect方式实现HTTP请求
 *
 * Created by xuan on 16/2/24.
 */
public class BPHttpUrlConnectionClient implements BPHttpClient {
    private static final String TAG = "BPHttpUrlConnectionCl..";

    @Override
    public BPResponse getDowload(BPRequest bpRequest) {
        try{
            URL url = new URL(bpRequest.getGetUrl());

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            initRequest(conn, bpRequest);

            return readResponseForFile(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            BPResponse response = new BPResponse();
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
            return response;
        }
    }

    @Override
    public BPResponse postDowload(BPRequest bpRequest) {
        try{
            URL url = new URL(bpRequest.getUrl());

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");// POST模式
            conn.setDoOutput(true);//发送POST请求必须设置如下
            conn.setUseCaches(false);

            initRequest(conn, bpRequest);
            //设置POST参数
            putParamsToOutputStream(conn, bpRequest);

            return readResponseForFile(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            BPResponse response = new BPResponse();
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
            return response;
        }
    }

    @Override
    public BPResponse get(BPRequest bpRequest) {
        try{
            URL url = new URL(bpRequest.getGetUrl());

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            initRequest(conn, bpRequest);

            return readResponseForString(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            BPResponse response = new BPResponse();
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
            return response;
        }
    }

    @Override
    public BPResponse post(BPRequest bpRequest) {
        try{
            URL url = new URL(bpRequest.getUrl());

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");// POST模式
            conn.setDoOutput(true);//发送POST请求必须设置如下
            conn.setUseCaches(false);

            initRequest(conn, bpRequest);
            //设置POST参数
            putParamsToOutputStream(conn, bpRequest);

            return readResponseForString(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            BPResponse response = new BPResponse();
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
            return response;
        }
    }

    @Override
    public BPResponse postJson(BPRequest bpRequest) {
        try{
            URL url = new URL(bpRequest.getUrl());

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");// POST模式
            conn.setDoOutput(true);//发送POST请求必须设置如下

            initRequest(conn, bpRequest);
            //设置POST参数
            putParamsToOutputStreamForJson(conn, bpRequest);

            return readResponseForString(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            BPResponse response = new BPResponse();
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
            return response;
        }
    }

    @Override
    public BPResponse upload(BPRequest bpRequest){
        try{
            URL url = new URL(bpRequest.getUrl());

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            initRequest(conn, bpRequest);
            putBodyEntityToOutputStream(conn, bpRequest);

            return readResponseForString(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            BPResponse response = new BPResponse();
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
            return response;
        }
    }

    /**
     * 请求参数设置
     *
     * @param conn
     * @param bpRequest
     */
    private void initRequest(URLConnection conn, BPRequest bpRequest){
        //头部设置
        for(Map.Entry<String, String> entry : bpRequest.getHeaderMap().entrySet()){
            conn.addRequestProperty(entry.getKey(), entry.getValue());
        }

        //超时设置
        conn.setConnectTimeout(bpRequest.getConnectionTimeout());
        conn.setReadTimeout(bpRequest.getReadTimeout());
    }

    /**
     * 把请求体放到输出流中
     *
     * @param conn
     * @param bpRequest
     */
    private static void putBodyEntityToOutputStream(HttpURLConnection conn, BPRequest bpRequest){
        try {
            BPUrlMultipartEntity bodyEntity = new BPUrlMultipartEntity();
            bodyEntity.writeDataToBody(conn, bpRequest);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 以Json的格式放入请求体
     *
     * @param conn
     * @param bpRequest
     */
    private static void putParamsToOutputStreamForJson(HttpURLConnection conn, BPRequest bpRequest){
        String bodyJson = bpRequest.getBodyJson();
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(conn.getOutputStream());
            //dos.writeBytes(bodyJson);
            dos.write(bodyJson.getBytes());
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
        }finally {
            try{
                dos.flush();
                dos.close();
            }catch (Exception e){
                //Ignore
            }
        }
    }

    /**
     * 把参数当作普通表单放到OutputStream中去
     *
     * @param conn
     * @param bpRequest
     */
    private static void putParamsToOutputStream(HttpURLConnection conn, BPRequest bpRequest){
        String paramStr = bpRequest.getParamsStr();
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(conn.getOutputStream());
            //dos.writeBytes(paramStr);
            dos.write(paramStr.getBytes());
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
        }finally {
            try{
                dos.flush();
                dos.close();
            }catch (Exception e){
                //Ignore
            }
        }
    }

    /**
     * 读取结果为文件
     *
     * @param conn
     * @param bpRequest
     * @return
     */
    private static BPResponse readResponseForFile(HttpURLConnection conn, BPRequest bpRequest){
        boolean hasListener = false;
        if(null != bpRequest.getDownloadListener()){
            //设置了下载监听,需要非压缩的方式下载,这样可以提前计算出文件大小
            hasListener = true;
        }

        BPResponse response = new BPResponse();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        InputStream inStream = null;
        try{
            inStream = conn.getInputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            int curCount = 0;//当前字节量
            int total = -1;//总字节量
            if(hasListener){
                total = conn.getContentLength();
            }

            while( (len = inStream.read(buffer)) !=-1 ){
                curCount += len;
                if (hasListener) {
                    // 加载中回调
                    bpRequest.getDownloadListener().callBack(total, curCount, false);
                }
                outStream.write(buffer, 0, len);
            }
            if (hasListener) {
                // 加载中回调
                bpRequest.getDownloadListener().callBack(total, curCount, true);
            }

            response.setStatusCode(conn.getResponseCode());
            response.setReasonPhrase(conn.getResponseMessage());
            byte[] data = outStream.toByteArray();

            File file = new File(bpRequest.getDownloadFileName());
            FileUtils.writeByteArrayToFile(file, data, false);
            response.setResultFile(file);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
        }finally {
            try{
                outStream.close();
                inStream.close();
            }catch (Exception e){
                //Ignore
            }
        }

        return response;
    }

    /**
     * 读取结果作为字符穿
     *
     * @param conn
     * @param bpRequest
     * @return
     */
    private static BPResponse readResponseForString(HttpURLConnection conn, BPRequest bpRequest) {
        BPResponse response = new BPResponse();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        InputStream inStream = null;
        try{
            inStream = conn.getInputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len = inStream.read(buffer)) !=-1 ){
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();

            response.setStatusCode(conn.getResponseCode());
            response.setReasonPhrase(conn.getResponseMessage());
            response.setResultStr(new String(data, bpRequest.getEncode()));
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            response.setStatusCode(-1);
            response.setReasonPhrase(e.getMessage());
        }finally {
            try{
                outStream.close();
                inStream.close();
            }catch (Exception e){
                //Ignore
            }
        }

        return response;
    }

}