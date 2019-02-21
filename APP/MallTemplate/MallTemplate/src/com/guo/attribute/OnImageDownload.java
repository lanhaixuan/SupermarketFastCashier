package com.guo.attribute;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 图片异步下载完成后回调
 * 
 * @author yanbin
 * 
 */
public interface OnImageDownload {
	void onDownloadSucc(Bitmap bitmap, String c_url, ImageView mImageView);
}
