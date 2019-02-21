package com.guo.adapter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guo.adapter.ImageAdapter.ViewHolder;
import com.guo.attribute.ImageDownloader;
import com.guo.attribute.OnImageDownload;
import com.guo.malltemplate.R;
import com.guo.method.ZrcListView;

public class MyAdapter extends BaseAdapter {

	public List<Map<String, String>> mList = null;
	private Context context;
	private ZrcListView listView;
	private ImageDownloader mDownloader;

	public MyAdapter(Context context, List<Map<String, String>> mList2,
			ZrcListView listView) {

		this.context = context;
		this.mList = mList2;
		this.listView = listView;
	}

	public int getCount() {
		return mList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 得到一个布局映射器
		ViewHolder holder = null;
		final Map<String, String> map = mList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.simple_list_item1, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView1.setText(map.get("goodsname"));
		holder.tv_nubler.setText(map.get("quantity") + "件");
		holder.tv_jiage.setText("￥" + map.get("goodprice"));

		double dou = Integer.valueOf(map.get("quantity").toString())
				* Double.parseDouble(map.get("goodprice").toString());
		DecimalFormat df = new DecimalFormat("0.00");

		holder.textView2.setText("￥" + df.format(dou));

		String is_end = map.get("is_pay");
		if (is_end.equals("false")) {
			holder.tv_iszhifu.setText("未支付");
		} else {
			holder.tv_iszhifu.setText("已支付");
		}
		String url = map.get("imageurl").toString();

		if (mDownloader == null) {
			mDownloader = new ImageDownloader();
		}
		holder.iv_image.setTag(url);
		holder.iv_image.setId(position);
		// 异步下载图片
		mDownloader.imageDownload(url, holder.iv_image, "/yanbin", context,
				new OnImageDownload() {
					@Override
					public void onDownloadSucc(Bitmap bitmap, String c_url,
							ImageView mimageView) {

						ImageView imageView = (ImageView) mimageView
								.findViewWithTag(c_url);
						if (imageView != null) {
							mimageView.setImageBitmap(bitmap);
							imageView.setTag("");
						}
					}
				}, url);
		return convertView;
	}

	public class ViewHolder {
		TextView textView1;
		TextView tv_nubler;
		TextView tv_jiage;
		TextView tv_iszhifu;
		TextView textView2;
		ImageView iv_image;

		// ImageView iv_iamge;

		ViewHolder(View view) {
			textView1 = (TextView) view.findViewById(R.id.textView1);
			tv_nubler = (TextView) view.findViewById(R.id.tv_nubler);
			tv_jiage = (TextView) view.findViewById(R.id.tv_jiage);
			tv_iszhifu = (TextView) view.findViewById(R.id.tv_iszhifu);
			textView2 = (TextView) view.findViewById(R.id.textView2);
			iv_image = (ImageView) view.findViewById(R.id.iv_image);

		}
	}
}
