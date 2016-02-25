package com.xuan.bigapple.lib.http;

import com.xuan.bigapple.lib.http.urlconnect.BPHttpUrlConnectionClient;

/**
 * 实例工厂类
 *
 * Created by xuan on 16/2/24.
 */
public abstract class BPHttpFactroy {

    /**
     * 用URLConnection实现的
     *
     * @return
     */
    public static BPHttpClient getURLConnectionHttpClient(){
        return new BPHttpUrlConnectionClient();
    }

}
