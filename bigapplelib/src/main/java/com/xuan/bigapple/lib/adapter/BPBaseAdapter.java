package com.xuan.bigapple.lib.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xuan.bigapple.lib.bitmap.BPBitmapLoader;
import com.xuan.bigapple.lib.bitmap.BitmapDisplayConfig;

/**
 * 列表适配器的基类,丰富了一些常用的API
 *
 * Created by xuan on 16/5/6.
 */
public abstract class BPBaseAdapter extends BaseAdapter {
    private Context mContext;

    public BPBaseAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected void showImage(ImageView iv, String uri, BitmapDisplayConfig config){
        BPBitmapLoader.getInstance().display(iv, uri, config);
    }

    protected void showImage(ImageView iv, String uri){
        BPBitmapLoader.getInstance().display(iv, uri);
    }

    protected void showImage(ImageView iv, String uri, int loadingBitmapResid, int failBitmapResid){
        BitmapDisplayConfig config = new BitmapDisplayConfig();
        config.setLoadingBitmap(BitmapFactory.decodeResource(mContext.getResources(), loadingBitmapResid));
        config.setLoadFailedBitmap(BitmapFactory.decodeResource(mContext.getResources(), failBitmapResid));
        BPBitmapLoader.getInstance().display(iv, uri, config);
    }

}
